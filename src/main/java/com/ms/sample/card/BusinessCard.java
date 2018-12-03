package com.ms.sample.card;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ms.sample.card.entity.Entity;
import com.ms.sample.card.text.ImageText;

import java.util.Set;

public class BusinessCard {

	private File file;
	private Map<Entity, Set<String>> entities;
	private ImageText imageText;

	public BusinessCard(File file, ImageText imageText, Map<Entity, Set<String>> data) {
		this.file = file;
		this.entities = data;
		this.imageText = imageText;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Image = "+this.file.getName()).append("\n");
		if(entities != null){
			Iterator<Entry<Entity, Set<String>>> itr = entities.entrySet().iterator();
			while(itr.hasNext()){
				Entry<Entity, Set<String>> entry = itr.next();
				sb.append(entry.getKey()+" = "+entry.getValue()).append("\n");
			}
		}
		sb.append("ImageText = "+this.imageText.getTexts()).append("\n");
		return sb.toString();
	}

}
