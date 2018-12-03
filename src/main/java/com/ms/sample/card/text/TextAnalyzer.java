package com.ms.sample.card.text;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.ms.sample.card.JsonUtils;
import com.ms.sample.card.entity.Entity;

public class TextAnalyzer {

	private String uriBase;
	private String subscriptionKey;

	public TextAnalyzer(String uriBase, String subscriptionKey) {
		super();
		this.uriBase = uriBase;
		this.subscriptionKey = subscriptionKey;
	}

	public Map<Entity, Set<String>> textToEntities(List<String> words) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();

		try {
			URIBuilder uriBuilder = new URIBuilder(uriBase);

			uriBuilder.setParameter("language", "en");
			uriBuilder.setParameter("detectOrientation", "true");

			// Request parameters.
			URI uri = uriBuilder.build();
			HttpPost request = new HttpPost(uri);

			// Request headers.
			request.setHeader("Content-Type", "application/json");
			request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

			String jsonRequest = createJsonRequest(words);
			//			System.out.println(jsonRequest);
			// Request body.
			StringEntity requestEntity = new StringEntity(jsonRequest);
			request.setEntity(requestEntity);


			// Call the REST API method and get the response entity.
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			
			if(response.getStatusLine().getStatusCode() != 200){
				System.out.println(response.getStatusLine().getReasonPhrase());
				return null;
			}

			if (entity != null) {
				// Format and display the JSON response.
				String jsonString = EntityUtils.toString(entity);
				//				System.out.println(jsonString);
				return JsonUtils.getEntityList(jsonString);
			}
		} catch (Exception e) {
			// Display error message.
			System.out.println(e.getMessage());
		}
		return null;
	}

	private String createJsonRequest(List<String> texts) {
		StringBuilder sb = new StringBuilder();
		StringBuilder allText = new StringBuilder();
		sb.append("{ \"documents\": [");
		for(int i=0;i<texts.size();i++){
			String text = texts.get(i);
			if(text == null || text.isEmpty()){
				continue;
			}
			text = text.replaceAll("[^\\x00-\\x7F]", "");

			if(i>0){
				sb.append(" , ");
				allText.append(" ");
			}

			sb.append("{\"language\":\"en\",\"id\":\""+(i+1)+"\",\"text\":\""+text +"\"}");
			allText.append(text);
		}
		sb.append(" , ");
		sb.append("{\"language\":\"en\",\"id\":\""+0+"\",\"text\":\""+allText.toString() +"\"}");
		sb.append(" ] }");
		return sb.toString();
	}
}