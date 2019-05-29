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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Node extends BaseEntity {
	@ManyToOne
	private MindMap mindMap;
	@OneToMany(mappedBy = "parentNode")
	private Collection<Node> childNodes;
	@ManyToOne
	private Node parentNode;

	public Collection<Node> getChildNodes() {
		return childNodes;
	}

	public MindMap getMindMap() {
		return mindMap;
	}

	public Node getParentNode() {
		return parentNode;
	}

	public void setChildNodes(Collection<Node> childNodes) {
		this.childNodes = childNodes;
	}

	public void setMindMap(MindMap mindMap) {
		this.mindMap = mindMap;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}
}
