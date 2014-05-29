package com.romans.visitsmart.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import com.romans.visitsmart.networking.traffic.Response;

public class BaseActivity extends FragmentActivity implements NetworkHandler
{

    ProgressDialog dialog;

    private SharedPreferences prefs;


    BaseRequest request;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    void showLoadingDialog()
    {
        dialog = new ProgressDialog(this);
        dialog.show();
    }

    void dismissLoadingDialog()
    {
    //    ProgressDialog dialog = new ProgressDialog(this);
        if (dialog != null)
        {
            dialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCategoriesReceivedSuccess(Category[] categories) {
        assert false : "Cannot call method from this context. Did you forget to override it?";
    }

    @Override
    public void onCategoriesReceivedFailed(Response response) {

        assert false : "Cannot call method from this context. Did you forget to override it?";

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
