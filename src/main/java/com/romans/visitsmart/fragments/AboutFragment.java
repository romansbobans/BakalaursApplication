package com.romans.visitsmart.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.romans.visitsmart.R;

/**
 * Created by Romans on 13/05/14.
 */
public class AboutFragment extends BaseFragment {

    public static final String ABOUT_US_URL = "www.stubstubstub.covhfgdm";
    public static Fragment newInstance() {
        return new AboutFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final WebView webView = (WebView) view.findViewById(R.id.about_us_webview);
        getActivity().setTitle(R.string.about_us);
        webView.loadUrl("file:///android_asset/about.html");
/*
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/about.html");
                DevLog.e("LOADED URL");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (getActivity() == null) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                if (url.startsWith("mailto:")) {
                    startMailIntent(url);
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            private void startMailIntent(String url) {
                MailTo mailTo = MailTo.parse(url);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mailTo.getTo()});
                getActivity().startActivity(intent);
            }

        });

        webView.loadUrl(ABOUT_US_URL);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_us, null);
    }
}
