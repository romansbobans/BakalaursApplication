package com.romans.visitsmart.networking;

import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Romans on 01/04/14.
 */
public class BaseRequest {

    private static final String CATEGORIES = "raw/categories";
    private static final String OBJECTS = "raw/views";
    private static final String COMMENTS = "comment";
    private static final String UPLOAD_COMMENTS = "upload/comment";

    NetworkHandler handler;
    public BaseRequest(NetworkHandler handler) {
        this.handler = handler;
    }

    public void getAllCategories()
    {
        NetworkRequest.createGetRequest(handler, CATEGORIES, NetworkHandler.ON_GET_CATEGORIES, Category[].class).execute();
    }

    public void getObjects(Category category) {
        String url = OBJECTS + "/" + category.getId();
        NetworkRequest.createGetRequest(handler, url, NetworkHandler.ON_GET_VISIT_OBJECTS, VisitObject[].class).execute();

    }

    public void getComments(String objectId) {

        NetworkRequest.createGetRequest(handler, COMMENTS + "/" + objectId, NetworkHandler.ON_GET_COMMENTS, VisitObjectComment[].class).execute();

    }

    public void postComment(String objectId, String text) {
        VisitObjectComment comment = new VisitObjectComment();
        comment.setText(text);
       // String

        JSONObject object = new JSONObject();
        try {
            object.put("text", text);
            NetworkRequest.createPostRequest(handler, UPLOAD_COMMENTS + "/" + objectId, object.toString(),  NetworkHandler.ON_COMMENT_ADDED, Void.class).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Server calculates time and date

    }

    public void postRating(String id, float rating) {
        NetworkRequest.createPostRequest(handler, UPLOAD_COMMENTS + "/" + id, rating + "",  NetworkHandler.ON_RATING_POSTED, VisitObject.class).execute();
    }
}
