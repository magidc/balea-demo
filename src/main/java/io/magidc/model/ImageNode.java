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

@Entity
public class ImageNode extends Node {
	private String imageFilePath;
	private int widthPx;
	private int heighPx;

	public int getHeighPx() {
		return heighPx;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public int getWidthPx() {
		return widthPx;
	}

	public void setHeighPx(int heighPx) {
		this.heighPx = heighPx;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public void setWidthPx(int widthPx) {
		this.widthPx = widthPx;
	}

}
