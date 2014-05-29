package com.romans.visitsmart.ui.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.romans.visitsmart.R;

/**
 * Created by Romans on 25/04/14.
 */
public class DrawerCell extends LinearLayout {

    ImageView icon;
    TextView title;
    public DrawerCell(Context context) {
        super(context);

        View.inflate(context, R.layout.drawer_item, this);

        icon = (ImageView) findViewById(R.id.drawer_image);
        title = (TextView) findViewById(R.id.drawer_text);
    }


    public void setTitle(String titleText)
    {
        title.setText(titleText);
    }

    public ImageView getImageView()
    {
        return icon;
    }
}
