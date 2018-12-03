package com.ms.sample.card.text;

import java.util.List;

public class ImageText {
	private final String json;
	private final List<String> texts;

	public ImageText(String json, List<String> texts){
		this.json = json;
		this.texts = texts;
	}

	public String getJson() {
		return json;
	}

	public List<String> getTexts() {
		return texts;
	}

	@Override
	public String toString() {
		return this.json+"\n"+this.texts;
	}

}
