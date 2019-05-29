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
public class TableComponent extends BaseEntity {
	@ManyToOne
	private TableNode tableNode;
	private int index;
	private String title;

	public int getIndex() {
		return index;
	}

	public TableNode getTableNode() {
		return tableNode;
	}

	public String getTitle() {
		return title;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTableNode(TableNode tableNode) {
		this.tableNode = tableNode;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
