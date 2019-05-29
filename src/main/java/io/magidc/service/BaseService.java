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
package io.magidc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import io.magidc.model.BaseEntity;
import io.magidc.repository.BaseRepository;

public abstract class BaseService<T extends BaseEntity> {
	@Autowired
	private BaseRepository<T> repository;

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Iterable<T> findAll() {
		return repository.findAll();
	}

	public Optional<T> findById(Long id) {
		return repository.findById(id);
	}

	public void save(T entity) {
		repository.save(entity);
	}
}
