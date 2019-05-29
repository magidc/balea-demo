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

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class TableNode extends Node {
	@OneToMany(mappedBy = "tableNode")
	private Collection<TableComponent> columns;
	@OneToMany(mappedBy = "tableNode")
	private Collection<TableComponent> rows;
	@OneToMany(mappedBy = "tableNode")
	private Collection<Cell> cells;

	public Collection<Cell> getCells() {
		return cells;
	}

	public Collection<TableComponent> getColumns() {
		return columns;
	}

	public Collection<TableComponent> getRows() {
		return rows;
	}

	public void setCells(Collection<Cell> cells) {
		this.cells = cells;
	}

	public void setColumns(Collection<TableComponent> columns) {
		this.columns = columns;
	}

	public void setRows(Collection<TableComponent> rows) {
		this.rows = rows;
	}
}
