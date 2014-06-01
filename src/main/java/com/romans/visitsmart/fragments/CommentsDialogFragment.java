package com.romans.visitsmart.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.romans.visitsmart.R;
import com.romans.visitsmart.adapters.CommentsAdapter;
import com.romans.visitsmart.dao.VisitObjectComment;
import com.romans.visitsmart.networking.BaseRequest;
import com.romans.visitsmart.networking.handler.NetworkHandler;

/**
 * Created by Romans on 20/05/14.
 */
public class CommentsDialogFragment extends DialogFragment {

    private static final String EXTRA_COMMENTS = "extra.comments";
    private static final String EXTRA_OBJECT_ID = "extra.view.id";
    private NetworkHandler listener;
    private String objectId;
    VisitObjectComment[] object;

    public CommentsDialogFragment() {
    }

    public static DialogFragment newInstance(VisitObjectComment[] comments, String objectId, NetworkHandler listener) {
        CommentsDialogFragment fragment = new CommentsDialogFragment();
        Bundle args = new Bundle();
        Gson gson = new Gson();
        args.putString(EXTRA_COMMENTS, gson.toJson(comments));
        args.putString(EXTRA_OBJECT_ID, objectId);
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.comments_fragment, null);

        object = new Gson().fromJson(getArguments().getString(EXTRA_COMMENTS), VisitObjectComment[].class);
        objectId = getArguments().getString(EXTRA_OBJECT_ID);

        ListView commentList = (ListView) view.findViewById(R.id.comment_list);
        commentList.setAdapter(new CommentsAdapter(getActivity(), object));


        builder.setPositiveButton(R.string.close_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        final TextView newCommentText = (TextView) view.findViewById(R.id.comment_add_text);
        view.findViewById(R.id.send_comment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newCommentText.getText().toString();
                if (TextUtils.isEmpty(text))
                {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_empty_comment), 1000).show();
                    return;
                }
                new BaseRequest(listener).postComment(objectId, text);
                dismiss();
            }
        });


        builder.setView(view);
        builder.setTitle(R.string.comments_text);
        Dialog dialog = builder.create();
        dialog.show();
        builder.setInverseBackgroundForced(true);
        return dialog;
    }

}
