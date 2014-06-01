package com.romans.visitsmart.networking.handler;

import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.networking.traffic.Response;

/**
 * Created by Romans on 01/04/14.
 */
public interface NetworkHandler {

    String ON_GET_CATEGORIES = "onCategoriesReceived";
    String ON_GET_VISIT_OBJECTS = "onObjectsReceived";
    String ON_GET_COMMENTS = "onCommentsReceived";
    String ON_COMMENT_ADDED = "onCommentAdded";
    String ON_RATING_POSTED = "onRatingPosted";



    void onCategoriesReceivedSuccess(Category[] categories);
    void onCategoriesReceivedFailed(Response response);

    void onObjectsReceivedSuccess(VisitObject[] categories);
    void onObjectsReceivedFailed(Response response);

    void onCommentsReceivedSuccess(VisitObjectComment[] comments);
    void onCommentsReceivedFailed(Response response);

    void onCommentAddedSuccess();
    void onCommentAddedFailed(Response response);

    @Deprecated
    void onRatingPostedSuccess(float v, int i);

    void onRatingPostedSuccess(VisitObject object);
    void onRatingPostedFailed(Response response);

}
