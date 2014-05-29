package com.romans.visitsmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.romans.visitsmart.R;
import com.romans.visitsmart.utils.AppPreferences;
import com.romans.visitsmart.utils.Prefs;

/**
 * Created by Romans on 13/05/14.
 */
public class SettingsFragment extends BaseFragment{


    private final OnSettingsChangedListener listener;
    private AppPreferences prefs;

    public SettingsFragment(OnSettingsChangedListener listener) {
        this.listener = listener;
    }

    public static Fragment newInstance(OnSettingsChangedListener listener) {
        return new SettingsFragment(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = new AppPreferences(getActivity());
        RadioGroup group = (RadioGroup) getActivity().findViewById(R.id.langRadios);
        String lang = prefs.getString(Prefs.LANGUAGE, "LV");
        for (int i = 0; i < group.getChildCount(); i++)
        {
            RadioButton child = (RadioButton)group.getChildAt(i);
            if (child.getText().toString().equals(lang))
            {
                child.setChecked(true);
                break;
            }
        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) group.findViewById(checkedId);

                prefs.putString(Prefs.LANGUAGE, button.getText().toString());

                listener.onLanguageChanged();
            }
        });
    }

    public interface OnSettingsChangedListener {
        void onLanguageChanged();
    }
}
