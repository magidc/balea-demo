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
package io.magidc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.magidc.model.BaseEntity;
import io.magidc.service.BaseService;
import io.swagger.annotations.ApiOperation;

public abstract class BaseController<T extends BaseEntity> {
	@Autowired
	private BaseService<T> service;

	@ApiOperation(value = "Delete entity")
	@DeleteMapping(path = "/{id}")
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}

	@ApiOperation(value = "Find all entities")
	@GetMapping
	public ResponseEntity<Iterable<T>> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@ApiOperation(value = "Find entity by id")
	@GetMapping(path = "/{id}")
	public ResponseEntity<T> findById(@PathVariable Long id) {
		Optional<T> optional = service.findById(id);
		if (optional.isPresent())
			return new ResponseEntity<>(optional.get(), HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "Save entity")
	@PostMapping
	public void save(@RequestBody T entity) {
		service.save(entity);
	}
}
