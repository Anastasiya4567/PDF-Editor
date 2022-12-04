package com.thesis.thesis.application;

public class NewDocumentCreate {

    private String title;

    private Boolean privateAccess;

    public NewDocumentCreate() {
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
