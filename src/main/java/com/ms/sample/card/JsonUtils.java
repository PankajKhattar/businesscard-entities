package com.ms.sample.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ms.sample.card.entity.Entity;

public class JsonUtils {

	public static List<String> getTextList(String jsonString){
		List<String> textList = new ArrayList<String>();

		JSONObject json = new JSONObject(jsonString);
		JSONArray array1 = json.getJSONArray("regions");
		for(int i=0;i<array1.length();i++){
			JSONArray array2 = ((JSONObject)array1.get(i)).getJSONArray("lines");
			for(int j=0;j<array2.length();j++){
				JSONArray array3 = ((JSONObject)array2.get(j)).getJSONArray("words");
				StringBuilder texts = new StringBuilder();
				for(int k=0;k<array3.length();k++){
					String text = ((JSONObject)array3.get(k)).getString("text");
					texts.append(text).append(" ");
				}
				textList.add(texts.toString().trim());
			}
		}
		return textList;
	}

	public static Map<Entity, Set<String>> getEntityList(String jsonString){

		Map<Entity,Set<String>> data = new HashMap<Entity,Set<String>>();
		JSONObject json = new JSONObject(jsonString);
		JSONArray array1 = json.getJSONArray("Documents");
		for(int i=0;i<array1.length();i++){
			@SuppressWarnings("unused")
			String id = ((JSONObject)array1.get(i)).getString("Id");
			JSONArray array2 = ((JSONObject)array1.get(i)).getJSONArray("Entities");
			for(int j=0;j<array2.length();j++){
				JSONObject obj = ((JSONObject)array2.get(j));
				String name = obj .getString("Name");
				String type  = null;
				if(obj.has("Type")){
					type = obj .getString("Type");
				}
				String subType = null;
				if(obj.has("SubType")){
					subType = obj .getString("SubType");
				}

				if(type != null){
					Entity key = new Entity(type,subType);
					Set<String> list = null;
					if(data.containsKey(key)){
						data.get(key).add(name);
					}else{
						list = new HashSet<String>();
						list.add(name);
						data.put(key, list);
					}
				}
			}
		}
		return data;
	}
}
