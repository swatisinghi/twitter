package com.twitter.manager;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.bulk.BulkRequestBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Indexer {
	
	public Indexer() {

    }

    public void indexDocument(HashMap<String, XContentBuilder> jsonDocs, String index, String type) {
    	
    	
    	Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("gateway.local.auto_import_dangled", "no").build();

        Client client = nodeBuilder().settings(settings).node().client();

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        System.out.println(jsonDocs.size());
//        for(Map<String, Object> item : jsonDocs) {
        for(String key : jsonDocs.keySet()) {
        	System.out.println(key);
        	System.out.println(index);
        	System.out.println(jsonDocs.get(key).prettyPrint());
//            bulkRequest.add(client.prepareIndex(index, type, item.toString()).setSource(item));
        	bulkRequest.add(client.prepareIndex(index, type, key).setSource(jsonDocs.get(key)));
        }
        bulkRequest.execute().actionGet();


    }
    
    public static void main(String args[]) {
    	
//    	XContentBuilder jsonDoc = null;
//    	try {
//    		jsonDoc = jsonBuilder()
//    				.startObject()
//                    .field("user", "kimchy")
//                    .field("postDate", new Date())
//                    .field("message", "trying out Elastic Search")
//                .endObject();
//    	} catch (IOException e) {
//    		
//    	}
                
    	
//    	System.out.println(jsonDoc.toString());
    	Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("gateway.local.auto_import_dangled", "no").build();

    	
        Client client = nodeBuilder().settings(settings).node().client();
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        Map<String, Object> json = new HashMap<String, Object>();
		json.put("name", "swati");
		json.put("date", new Date());
		json.put("id", 2);
		
    	bulkRequest.add(client.prepareIndex("twitter", "tweet", "2")
    	        .setSource(json));
    	bulkRequest.execute().actionGet();
    }
}
