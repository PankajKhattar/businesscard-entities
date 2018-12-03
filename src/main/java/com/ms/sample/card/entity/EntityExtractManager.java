package com.ms.sample.card.entity;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ms.sample.card.BusinessCard;
import com.ms.sample.card.image.ImageAnalyzer;
import com.ms.sample.card.text.ImageText;
import com.ms.sample.card.text.TextAnalyzer;

import java.util.Set;

public class EntityExtractManager {

	public static final String uriOCRBase =
			"https://westcentralus.api.cognitive.microsoft.com/vision/v2.0/ocr";
	public static String uriEntitiesBase = "https://westus.api.cognitive.microsoft.com/text/analytics/v2.1-preview/entities";


	private String subscriptionOCRKey;
	private String subscriptionEntitiesKey;

	public EntityExtractManager(String subscriptionOCRKey,String subscriptionEntitiesKey){
		this.subscriptionOCRKey = subscriptionOCRKey;
		this.subscriptionEntitiesKey = subscriptionEntitiesKey;
	}

	public static void main(String[] args) {
		String directory = args[0];
		String ocrKey = args[1];
		String entitiesKey = args[2];

		new EntityExtractManager(ocrKey,entitiesKey).execute(directory);
	}

	private void execute(String directory) {
		Map<File, ImageText> imageRecords = analyze(new File(directory));
		List<BusinessCard> alldata = parseEntities(imageRecords);
		Iterator<BusinessCard> itr = alldata.iterator();
		while(itr.hasNext()){
			System.out.println("--------------------------------------");
			System.out.println(itr.next());
		}

	}

	private List<BusinessCard> parseEntities(Map<File, ImageText> imageRecords) {
		List<BusinessCard> allData = new ArrayList<BusinessCard>();
		TextAnalyzer analyzer = new TextAnalyzer(uriEntitiesBase, subscriptionEntitiesKey);
		Iterator<Entry<File, ImageText>> itr = imageRecords.entrySet().iterator();
		while(itr.hasNext()){
			Entry<File, ImageText> entry = itr.next();
			System.out.println("Getting entities for "+entry.getKey().getName());
			Map<Entity, Set<String>> data = analyzer.textToEntities(entry.getValue().getTexts());
			allData.add(new BusinessCard(entry.getKey(),entry.getValue(),data));
		}		
		return allData;
	}

	public Map<File, ImageText> analyze(File directory){
		if(directory == null){
			return null;
		}

		File[] files = new File[1];
		if(directory.isDirectory()){
			System.out.println("Analyzing all Images in directory "+directory);

			files = directory.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					if(pathname.isFile() && (pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".png") || pathname.getName().endsWith(".jpeg"))){
						return true;
					}
					return false;
				}
			});
		}

		if(directory.isFile()){
			files[0] = directory; 
		}

		Map<File,ImageText> imageRecords = new HashMap<File,ImageText>();
		ImageAnalyzer analyzer = new ImageAnalyzer(uriOCRBase,subscriptionOCRKey);
		for(File file:files){
			System.out.println("Analyzing "+file.getName());
			ImageText imageText = analyzer.imageToText(file);
			imageRecords.put(file, imageText);
		}
		return imageRecords;
	}

}
