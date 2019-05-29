/*
 *
 *  Copyright 2019 magidc.io
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package io.magidc.config;

import java.io.IOException;
import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.magidc.balea.core.container.DataSourceContainerManager;
import com.magidc.balea.core.container.config.DataSourceContainerParameters;
import com.magidc.balea.core.container.config.PortBindingSupplier;
import com.magidc.balea.core.core.config.DataSourceConfigurer;
import com.magidc.balea.core.proxy.factory.RoutingDataSourceFactory;

import io.magidc.model.BaseEntity;
import io.magidc.service.UserService;

@Configuration
public class DataSourceConfig {

	private static final long CACHE_EXPIRING_TIME_MILLIS = 10000L;
	private static final String POSTGRES_DB = "demo";
	private static final String POSTGRES_PASSWORD = "mypass";
	private static final String POSTGRES_USER = "magidc";

	@Autowired
	private UserService userService;

	private DataSourceConfigurer getDataSourceConfigurer() {
		return new DataSourceConfigurer() {
			@Override
			public DataSource createDataSource(String host, int port) {

				PGSimpleDataSource dataSource = new PGSimpleDataSource();
				dataSource.setUrl(String.format("jdbc:postgresql://%s:%d/%s", host, port, POSTGRES_DB));
				dataSource.setUser(POSTGRES_USER);
				dataSource.setPassword(POSTGRES_PASSWORD);
				dataSource.setDatabaseName(POSTGRES_DB);

				updateDataSource(dataSource, this::validateDataSource);
				return dataSource;
			}

			@Override
			public String getDataDirPath(Object dataSourceId, String dataSourceContainerDataDirPath) {
				return userService.findDataDirectory((Long) dataSourceId);
			}

			@Override
			public Object getDataSourceId() {
				return UserIdFilter.getRequestUserId();
			}

			@Override
			public boolean validateDataSource(DataSource dataSource) {
				Connection connection;
				try {
					connection = dataSource.getConnection();
					connection.close();
					return true;
				} catch (Exception e) {
					return false;
				}
			}
		};
	}

	private DataSourceContainerParameters getDataSourceContainerParameters() {
		DataSourceContainerParameters dataSourceContainerParameters = new DataSourceContainerParameters();
		dataSourceContainerParameters.setDataVolumes(new String[] { "/var/lib/postgresql/data" });
		dataSourceContainerParameters.setImageName("postgres:9.4");
		dataSourceContainerParameters.setPort(5432);
		dataSourceContainerParameters.useDockerProxy(getPortBindingSupplier());
		dataSourceContainerParameters.getEnvironmentVariables().put("POSTGRES_USER", POSTGRES_USER);
		dataSourceContainerParameters.getEnvironmentVariables().put("POSTGRES_PASSWORD", POSTGRES_PASSWORD);
		dataSourceContainerParameters.getEnvironmentVariables().put("POSTGRES_DB", POSTGRES_DB);

		return dataSourceContainerParameters;
	}

	private DockerClientConfig getDockerClientConfig() {
		return DefaultDockerClientConfig.createDefaultConfigBuilder()
				.withDockerHost("tcp://0.0.0.0:4243")
				.withRegistryUrl("https://index.docker.io/v1/")
				.build();
	}

	private PortBindingSupplier getPortBindingSupplier() {
		return new PortBindingSupplier() {
			@Override
			public int getAvailablePort(Collection<Integer> dockerPortBindingsInUse) {
				if (dockerPortBindingsInUse.isEmpty())
					return 5432;
				return Collections.max(dockerPortBindingsInUse) + 1;
			}
		};
	}

	@Bean
	@Primary
	public DataSource routingDataSource() throws DockerException, InterruptedException, IOException, ExecutionException, InstantiationException, IllegalAccessException {
		DataSourceConfigurer dataSourceConfigurer = getDataSourceConfigurer();
		DataSourceContainerManager dataSourceContainerManager = new DataSourceContainerManager(getDataSourceContainerParameters(), dataSourceConfigurer, getDockerClientConfig());
		return RoutingDataSourceFactory.createRoutingDataSource(PGSimpleDataSource.class, 1L, dataSourceContainerManager, dataSourceConfigurer, CACHE_EXPIRING_TIME_MILLIS);
	}

	private void updateDataSource(DataSource dataSource, Predicate<DataSource> validateDataSource) {
		try {
			org.hibernate.cfg.Configuration configuration = new LocalSessionFactoryBuilder(dataSource)
					.scanPackages(BaseEntity.class.getPackage().getName());

			configuration.setImplicitNamingStrategy(new SpringImplicitNamingStrategy());
			configuration.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
			configuration.setProperty(AvailableSettings.HBM2DDL_AUTO, "update");
			configuration.setProperty(AvailableSettings.NON_CONTEXTUAL_LOB_CREATION, "true");
			configuration.setProperty(AvailableSettings.SHOW_SQL, "true");

			while (!validateDataSource.test(dataSource))
				Thread.sleep(10);

			configuration.buildSessionFactory().close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
