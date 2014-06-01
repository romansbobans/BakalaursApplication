package com.romans.visitsmart.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.romans.visitsmart.dao.VisitObject;
import com.romans.visitsmart.ui.views.VisitObjectListItem;
import com.romans.visitsmart.utils.AppPreferences;
import com.romans.visitsmart.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romans on 05/05/14.
 */
public class VisitObjectListAdapter extends ArrayAdapter<VisitObject> {

    private List<VisitObject> objects;

    public VisitObjectListAdapter(Context context, List<VisitObject> visitObjects) {
        super(context, android.R.layout.simple_list_item_1);
        objects = new ArrayList<>();
        String language = new AppPreferences(context).getString(Prefs.LANGUAGE, "LV");
        for (VisitObject o : visitObjects)
        {
            if (o.hasLangSuport(language))
            {
                objects.add(o);
            }

        }

    }

    public VisitObjectListAdapter(Context context, VisitObject[] visitObjects) {
        super(context, android.R.layout.simple_list_item_1);
        objects = new ArrayList<>();
        String language = new AppPreferences(context).getString(Prefs.LANGUAGE, "LV");
        for (VisitObject o : visitObjects)
        {
            if (o.hasLangSuport(language))
            {
                objects.add(o);
            }

        }

    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public VisitObject getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VisitObjectListItem item;
        if (convertView == null)
        {
            item = new VisitObjectListItem(getContext(), null);
        }
        else
        {
            item = (VisitObjectListItem) convertView;
        }
            item.setVisitObject(getItem(position));
            item.generate();

        return item;
    }
}
