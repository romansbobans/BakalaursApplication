package com.romans.visitsmart.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.gson.annotations.SerializedName;
import com.romans.visitsmart.R;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.networking.images.ImageWorker;
import com.romans.visitsmart.utils.AppPreferences;
import com.romans.visitsmart.utils.DevLog;
import com.romans.visitsmart.utils.Prefs;

/**
 * Created by Romans on 05/05/14.
 */
public class VisitObjectListItem extends LinearLayout {

    private VisitObject visitObject;
    @SerializedName("objects")
    private VisitObject.VisitObjectDescription description;
    private boolean regenerate;

    private static String language;

    private TextView title;
    private TextView shortDescription;
    private TextView distanceText;
    private ImageView icon;
    private ImageView objectWithPrice;
    private ImageView workingHrsIcon;
    private String defLanguage = getResources().getString(R.string.language_lv);
    private RatingBar rate;
    private TextView ratingBarText;

    public VisitObjectListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.visitobject_list_item, this);

        title = (TextView) findViewById(R.id.visit_object_title);
        shortDescription = (TextView) findViewById(R.id.visit_object_short_description);
        icon = (ImageView) findViewById(R.id.visit_object_icon);
        workingHrsIcon = (ImageView) findViewById(R.id.working_hrs_icon);
        objectWithPrice = (ImageView) findViewById(R.id.object_with_price_icon);
        distanceText = (TextView) findViewById(R.id.distance_text);
        rate = (RatingBar) findViewById(R.id.rating);
        ratingBarText = (TextView) findViewById(R.id.rating_text);

    }

    public void setVisitObject(VisitObject visitObject){
        if (this.visitObject == null || visitObject == this.visitObject)
        {
            regenerate = true;
        }
        else
        {
            regenerate = false;
        }

        this.visitObject = visitObject;
        DevLog.e("VISITOBJ: " + visitObject);

        AppPreferences prefs = new AppPreferences(getContext());
        language = prefs.getString(Prefs.LANGUAGE, defLanguage);
        for (VisitObject.VisitObjectDescription descr : this.visitObject.getObjectDescriptions())
        {
            if (descr.getLang().equalsIgnoreCase(language))
            {
                this.description = descr;
                break;
            }
        }
    }

    public void setDistance(String distance)
    {
        if (distance == null)
        {
            return;
        }
        distanceText.setVisibility(View.VISIBLE);
        distanceText.setText(distance);
        findViewById(R.id.distance_progress).setVisibility(View.GONE);

    }

    public void generate() {
        if (!regenerate)
        {
            return;
        }
        regenerate = false;

        if (description.getWorkingHours() == null || description.getWorkingHours().length == 0)
        {
            workingHrsIcon.setVisibility(View.GONE);
        }
        else
        {
            workingHrsIcon.setVisibility(View.VISIBLE);
        }
        if (description.getGroups() == null || description.getGroups().length == 0)
        {
            objectWithPrice.setVisibility(View.GONE);
        }
        else
        {
            objectWithPrice.setVisibility(View.VISIBLE);
        }
        title.setText(description.getName());
        shortDescription.setText(description.getShortDescription());

        if (visitObject.getTitleImageUrl() != null)
        {
            DevLog.e("LOADING IMAGE");
            new ImageWorker(getContext(), icon).execute(visitObject.getTitleImageUrl());
        }
        else
        {

            DevLog.e("NOT LOADING IMAGE");
        }

        setDistance(visitObject.getDistance());

        String count = visitObject.getRatingCount() + "";
        ratingBarText.setText(count);
        rate.setRating(visitObject.getRating());

    }
}
