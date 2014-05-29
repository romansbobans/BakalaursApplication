package com.romans.visitsmart.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.ui.views.CommentsListItem;

/**
 * Created by Romans on 20/05/14.
 */
public class CommentsAdapter extends ArrayAdapter<VisitObjectComment> {

    public CommentsAdapter(Context ctx, VisitObjectComment[] commentList) {
        super(ctx, android.R.layout.simple_list_item_1, commentList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentsListItem item;
        if (convertView != null)
        {
            item = (CommentsListItem) convertView;
        }
        else
        {
            item = new CommentsListItem(getContext(), null);
        }
        VisitObjectComment comment = getItem(position);
        item.setCommentDateTime("dasd", "asd");
        item.setCommentText(comment.getText());
        return item;
    }
}
