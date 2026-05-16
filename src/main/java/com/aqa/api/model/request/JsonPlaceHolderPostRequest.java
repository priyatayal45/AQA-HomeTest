package com.aqa.api.model.request;

public class JsonPlaceHolderPostRequest {

	    private String title;
	    private String body;
	    private int userId;

	    public JsonPlaceHolderPostRequest(String title, String body, int userId) 
	    {
	        this.title = title;
	        this.body = body;
	        this.userId = userId;
	    }

	    public String getTitle() { return title; }
	    public String getBody() { return body; }
	    public int getUserId() { return userId; }
}
