package com.romans.visitsmart.dao;

/**
 * Created by Romans on 20/05/14.
 */
public class VisitObjectComment {
    String objectId;
    String text;
    long time;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
