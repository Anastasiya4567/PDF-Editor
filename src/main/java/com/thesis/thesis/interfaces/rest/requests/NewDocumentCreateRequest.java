package com.thesis.thesis.interfaces.rest.requests;

public class NewDocumentCreateRequest {

    private String title;

    private Boolean privateAccess;

    public NewDocumentCreateRequest() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrivateAccess(Boolean privateAccess) {
        this.privateAccess = privateAccess;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getPrivateAccess() {
        return privateAccess;
    }
}
