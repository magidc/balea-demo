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
package io.magidc.model;

import java.time.Instant;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity {
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant created;
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Access(AccessType.PROPERTY)
	private Long id;
	@UpdateTimestamp
	private Instant updated;

	public Instant getCreated() {
		return created;
	}

	public Long getId() {
		return id;
	}

	public Instant getUpdated() {
		return updated;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUpdated(Instant updated) {
		this.updated = updated;
	}
}
