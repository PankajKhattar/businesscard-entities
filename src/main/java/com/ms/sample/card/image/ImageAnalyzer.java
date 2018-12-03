package com.ms.sample.card.image;

import java.io.File;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.ms.sample.card.JsonUtils;
import com.ms.sample.card.text.ImageText;

public class ImageAnalyzer {

	private String uriBase;
	private String subscriptionKey;

	public ImageAnalyzer(String uriBase, String subscriptionKey) {
		super();
		this.uriBase = uriBase;
		this.subscriptionKey = subscriptionKey;
	}

	public ImageText imageToText(File imageFile) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			URIBuilder uriBuilder = new URIBuilder(uriBase);

			uriBuilder.setParameter("language", "en");
			uriBuilder.setParameter("detectOrientation", "true");

			// Request parameters.
			URI uri = uriBuilder.build();
			HttpPost request = new HttpPost(uri);

			// Request headers.
			//            request.setHeader("Content-Type", "application/json");
			request.setHeader("Content-Type", "application/octet-stream");
			//			request.setHeader("Content-Type", "multipart/form-data");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

			// Request body.
			//            StringEntity requestEntity =
			//                    new StringEntity("{\"url\":\"" + imageToAnalyze + "\"}");
			//            request.setEntity(requestEntity);

			FileEntity fileEntity = new FileEntity(imageFile);
			request.setEntity(fileEntity );

			// Call the REST API method and get the response entity.
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// Format and display the JSON response.
				String jsonString = EntityUtils.toString(entity);
				//				System.out.println(jsonString);
				List<String> texts = JsonUtils.getTextList(jsonString);
				return new ImageText(jsonString, texts);
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
		return null;
	}
}