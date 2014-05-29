package com.romans.visitsmart.fragments;

import android.support.v4.app.Fragment;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import com.romans.visitsmart.networking.traffic.Response;

/**
 * Created by Romans on 05/05/14.
 */
public class BaseFragment extends Fragment implements NetworkHandler {



    @Override
    public void onCategoriesReceivedSuccess(Category[] categories) {

    }

    @Override
    public void onCategoriesReceivedFailed(Response response) {

    }

    @Override
    public void onObjectsReceivedSuccess(VisitObject[] categories) {

    }

    @Override
    public void onObjectsReceivedFailed(Response response) {

    }

    @Override
    public void onCommentsReceivedSuccess(VisitObjectComment[] comments) {

    }

    @Override
    public void onCommentsReceivedFailed(Response response) {

    }

    @Override
    public void onCommentAddedSuccess() {

    }

    @Override
    public void onCommentAddedFailed(Response response) {

    }

    @Override
    public void onRatingPostedSuccess(float v, int i) {

    }

    @Override
    public void onRatingPostedFailed(Response response) {

    }
}
