package com.romans.visitsmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.webkit.WebView;
import android.widget.RatingBar;
import android.widget.TextView;
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
    private TextView workingHrs;
    private TextView groups;

    private final static String GALLERY_FRAGMENT_TAG = "gallery.fragment";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_description_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            String rawVisitObject = getIntent().getExtras().getString(Extras.SELECTED_VISITOBJECT);
            object = new Gson().fromJson(rawVisitObject, VisitObject.class);
        }
        else
        {

            String rawVisitObject = savedInstanceState.getString(Extras.SELECTED_VISITOBJECT);
            object = new Gson().fromJson(rawVisitObject, VisitObject.class);
        }

        try {
            description = object.getObjectDescriptions(this);
        } catch (LanguageNotFoundException e) {
            throw new Error("Language not found, Should never get here");
        }

        descriptionView = (WebView) findViewById(R.id.description_fragment_holder);
        workingHrs = (TextView) findViewById(R.id.description_working_hrs);
        setWorkingHrs();
        groups = (TextView) findViewById(R.id.description_groups);
        setGroups();
        bar = (RatingBar) findViewById(R.id.object_rating_bar);
        bar.setOnRatingBarChangeListener(ratingListener);
        bar.setRating(object.getRating());
        loadDescription();
        getActionBar().setTitle(description.getName());


        Fragment gallery = getSupportFragmentManager().findFragmentByTag(GALLERY_FRAGMENT_TAG);
        if (gallery == null)
        {
            gallery = GalleryFragment.newInstance(object.getImagePairs());
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (object.getImagePairs() != null && object.getImagePairs().length != 0) {
            transaction.replace(R.id.descriptions_gallery_holder, gallery, GALLERY_FRAGMENT_TAG) ;
        }
        else
        {
         findViewById(R.id.descriptions_gallery_holder).setVisibility(View.GONE);
        }
        transaction.commit();
    }

    private void setGroups() {
        if (description.getGroups() != null && description.getGroups().length > 0)
        {
            for(VisitObject.VisitObjectDescription.ClientGroups group : description.getGroups())
            {
                groups.append(group.getGroupName() + "\t\t" + group.getGroupPrice() + "\n");
            }
        }
        else
        {
            findViewById(R.id.description_groups_container).setVisibility(View.GONE);
        }
    }

    private void setWorkingHrs()
    {
        if (description.getWorkingHours() != null && description.getWorkingHours().length > 0)
        {
            for(VisitObject.VisitObjectDescription.Hours hours : description.getWorkingHours())
            {
                workingHrs.append(hours.getDay() + "\t\t" + hours.getHours() + "\n");
            }
        }
        else
        {
            findViewById(R.id.description_working_hrs_container).setVisibility(View.GONE);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Extras.SELECTED_VISITOBJECT, new Gson().toJson(object));
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
        showDefaultNetworkError(response);
        dismissLoadingDialog();

    }

    @Override
    public void onCommentAddedSuccess() {
        Toast.makeText(this, getString(R.string.comment_added_success), 1000).show();
    }

    @Override
    public void onCommentAddedFailed(Response response) {
        showDefaultNetworkError(response);

    }

    @Override
    public void onRatingPostedFailed(Response response) {
        showDefaultNetworkError(response);
    }

    @Override
    public void onRatingPostedSuccess(VisitObject object) {
        object.setRating(object.getRating());
        object.setRatingCount(object.getRatingCount());
        bar.setRating(object.getRating());
    }

    public void goToMapView(View view) {
        Intent intent = new Intent(this, ObjectMapActivity.class);
        intent.putExtra(Extras.SELECTED_VISITOBJECT, new Gson().toJson(object));
        startActivity(intent);

    }
}