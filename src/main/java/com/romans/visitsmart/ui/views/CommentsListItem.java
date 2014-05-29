package com.romans.visitsmart.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.romans.visitsmart.R;

/**
 * Created by Romans on 20/05/14.
 */
public class CommentsListItem extends LinearLayout {

    TextView dateTime;
    TextView commentText;

    public CommentsListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.comment_cell_item, this);
        dateTime = (TextView) findViewById(R.id.comment_date);
        commentText = (TextView) findViewById(R.id.comment_text);
    }

    public void setCommentDateTime(String date, String time)
    {
        dateTime.setText(date + "  " + time);
    }

    public void setCommentText(String text)
    {
       commentText.setText(text);
    }


}
