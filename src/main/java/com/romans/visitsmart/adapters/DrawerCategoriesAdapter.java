package com.romans.visitsmart.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import com.romans.visitsmart.dao.Category;
import com.romans.visitsmart.exceptions.LanguageNotFoundException;
import com.romans.visitsmart.networking.images.ImageLoader;
import com.romans.visitsmart.networking.images.ImageWorker;
import com.romans.visitsmart.ui.views.DrawerCell;
import com.romans.visitsmart.utils.AppPreferences;
import com.romans.visitsmart.utils.Prefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romans on 24/04/14.
 */
public class DrawerCategoriesAdapter extends ArrayAdapter<Category> implements Filterable {


    String language;
    ImageLoader loader;
    List<Category> categories;

    public DrawerCategoriesAdapter(Context ctx, List<Category> categories) {
        super(ctx, android.R.layout.simple_list_item_1);
        this.categories = new ArrayList<>();
        loader = new ImageLoader(ctx);
        language = new AppPreferences(ctx).getString(Prefs.LANGUAGE, "LV");
        for (Category cat : categories)
        {
            if (cat.hasLangSupport(language))
            {
                this.categories.add(cat);
            }
        }
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerCell v;
        if (convertView == null)
        {
            v = new DrawerCell(getContext());
        }
        else
        {
            v = (DrawerCell) convertView;
        }
        Category category = getItem(position);

        try {
            v.setTitle(category.getObjectDescription(language).getName());
        } catch (LanguageNotFoundException e) {
            e.printStackTrace();

            v.setTitle(category.getObjectDescription()[0].getName());
            return v;
        }
        v.setVisibility(View.VISIBLE);

        new ImageWorker(getContext(), v.getImageView()).execute(category.getThumb_url());

        return v;
    }

    public long getItemId(int position) {
        return position;
    }
/*

    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                newValues = (List<Category>) results.values;

                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Category> FilteredArrayNames = new ArrayList<Category>();


                for (Category cat : categories)
                {
                    if (cat.hasLangSupport(constraint.toString()))
                    {
                        FilteredArrayNames.add(cat);
                    }
                }
                results.values = FilteredArrayNames;
                results.count = FilteredArrayNames.size();

                return results;
            }
        };

        return filter;
    }
*/


}
