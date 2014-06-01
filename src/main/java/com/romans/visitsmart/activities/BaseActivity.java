package com.romans.visitsmart.activities;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.handler.NetworkHandler;
import com.romans.visitsmart.networking.traffic.Response;

public class BaseActivity extends FragmentActivity implements NetworkHandler
{

    ProgressDialog dialog;
    Gson gson = new Gson();
    private SharedPreferences prefs;


    BaseRequest request;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    void showDefaultNetworkError(Response response)
    {
        switch (response.getCode())
        {
            case NO_NETWORK:
                Toast.makeText(this, getString(R.string.no_ntework_message), 1000).show();
                break;
            default:
                Toast.makeText(this, getString(R.string.unknown_error), 1000).show();

        }
    }

    void showLoadingDialog()
    {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
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
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Override
    public void onObjectsReceivedFailed(Response response) {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Override
    public void onCommentsReceivedSuccess(VisitObjectComment[] comments) {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Override
    public void onCommentsReceivedFailed(Response response) {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Override
    public void onCommentAddedSuccess() {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Override
    public void onCommentAddedFailed(Response response) {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Deprecated
    @Override
    public void onRatingPostedSuccess(float v, int i) {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

    @Override
    public void onRatingPostedSuccess(VisitObject object) {
        assert false : "Cannot call method from this context. Did you forget to override it?";
    }

    @Override
    public void onRatingPostedFailed(Response response) {
        assert false : "Cannot call method from this context. Did you forget to override it?";

    }

}
