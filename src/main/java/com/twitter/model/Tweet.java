package com.twitter.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;

import org.elasticsearch.common.xcontent.XContentBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Tweet {
	
	private long id;
	private String text;
	private Date date;
	private String userName;

	public Tweet(long id, String text, Date date, String userName) {
		this.id = id;
		this.text = text;
		this.date = date;
		this.userName = userName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public XContentBuilder tweetJson() {
        XContentBuilder jsonDoc = null;
        try {
            jsonDoc = jsonBuilder()
                    .startObject()
                    .field("genre", this.text)
                    .field("artist", this.date)
                    .field("name", this.userName)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonDoc;
    }

	
	public Map<String, Object> json() {
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("name", this.userName);
		json.put("date", this.date);
		json.put("text", this.text);
		json.put("id", this.id);
        return json;
    }
}
