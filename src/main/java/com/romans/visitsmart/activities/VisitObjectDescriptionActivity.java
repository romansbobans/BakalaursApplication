package com.romans.visitsmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.exceptions.LanguageNotFoundException;
import com.romans.visitsmart.fragments.CommentsDialogFragment;
import com.romans.visitsmart.fragments.GalleryFragment;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.traffic.Response;
import com.romans.visitsmart.utils.Extras;

/**
 * Created by Romans on 05/05/14.
 */
public class VisitObjectDescriptionActivity extends BaseActivity {

    VisitObject object;
    VisitObject.VisitObjectDescription description;

    WebView descriptionView;
    RatingBar bar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_description_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String rawVisitObject = getIntent().getExtras().getString(Extras.SELECTED_VISITOBJECT);
            object = new Gson().fromJson(rawVisitObject, VisitObject.class);
        }

        try {
            description = object.getObjectDescriptions(this);
        } catch (LanguageNotFoundException e) {
            throw new Error("Language not found, Should never get here");
        }

        descriptionView = (WebView) findViewById(R.id.description_fragment_holder);
        bar = (RatingBar) findViewById(R.id.object_rating_bar);
        bar.setOnRatingBarChangeListener(ratingListener);
        bar.setRating(object.getRating());
        loadDescription();
        getActionBar().setTitle(description.getName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
     //   if (object.getImagePairs() != null && object.getImagePairs().length != 0) {
            transaction.replace(R.id.descriptions_gallery_holder, GalleryFragment.newInstance(object));
       // }
        transaction.commit();
    }

    RatingBar.OnRatingBarChangeListener ratingListener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            if (fromUser)
            new BaseRequest(VisitObjectDescriptionActivity.this).postRating(object.getId(), rating);
        }
    };

    private void loadDescription() {
        String text = "<body style=\"text-align:justify;color:gray;text-indent:50px;\">" +
                description.getDescription() +
                "</body>";
        descriptionView.loadData(text, "text/html; charset=UTF-8", null);
    }

    public void openCommentsSection(View view) {
        showLoadingDialog();
        new BaseRequest(this).getComments(object.getId());
    }

    @Override
    public void onCommentsReceivedSuccess(VisitObjectComment[] comments) {
        dismissLoadingDialog();
        DialogFragment fragment = CommentsDialogFragment.newInstance(comments, object.getId(), this);
        fragment.show(getSupportFragmentManager(), "Something");
    }

    @Override
    public void onCommentsReceivedFailed(Response response) {
        super.onCommentsReceivedFailed(response);
    }

    @Override
    public void onCommentAddedSuccess() {
        Toast.makeText(this, getString(R.string.comment_added_success), 1000).show();
    }

    @Override
    public void onCommentAddedFailed(Response response) {
        super.onCommentAddedFailed(response);
    }

    @Override
    public void onRatingPostedFailed(Response response) {
        super.onRatingPostedFailed(response);
    }

    @Override
    public void onRatingPostedSuccess(float v, int i) {
        object.setRating(v);
        object.setRatingCount(i);
        bar.setRating(v);
    }

    public void goToMapView(View view) {
        Intent intent = new Intent(this, ObjectMapActivity.class);
        intent.putExtra(Extras.SELECTED_VISITOBJECT, new Gson().toJson(object));
        startActivity(intent);

    }
}