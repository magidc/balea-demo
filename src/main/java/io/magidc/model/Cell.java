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

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Cell extends BaseEntity {
	@ManyToOne
	private TableComponent row;
	@ManyToOne
	private TableComponent columns;
	@ManyToOne
	private TableNode tableNode;
	private boolean hidden;
	private String value;

	public TableComponent getColumns() {
		return columns;
	}

	public TableComponent getRow() {
		return row;
	}

	public TableNode getTableNode() {
		return tableNode;
	}

	public String getValue() {
		return value;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setColumns(TableComponent columns) {
		this.columns = columns;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public void setRow(TableComponent row) {
		this.row = row;
	}

	public void setTableNode(TableNode tableNode) {
		this.tableNode = tableNode;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
