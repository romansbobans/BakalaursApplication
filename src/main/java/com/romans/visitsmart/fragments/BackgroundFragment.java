package com.romans.visitsmart.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.ImageView;
import com.romans.visitsmart.R;

/**
 * Created by Romans on 06/05/14.
 */
public class BackgroundFragment extends BaseFragment {

    ImageView fadeInFadeOut;
    public static Fragment newInstance() {
        return new BackgroundFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.main_default, null);
        fadeInFadeOut = (ImageView) parent.findViewById(R.id.fade_out_imageview);

        int imagesToShow[] = { R.drawable.landscape_1, R.drawable.ventspils_cropped,R.drawable.landscape_2,R.drawable.landscape_3,R.drawable.landscape_4 };

        animate(fadeInFadeOut, imagesToShow, 0,true);
        return parent;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);



    }





    private void animate(final ImageView imageView, final int images[], final int imageIndex, final boolean forever) {


        int fadeInDuration = 1500;
        int timeBetween = 3000;
        int fadeOutDuration = 1500;

        imageView.setVisibility(View.INVISIBLE);
        imageView.setImageResource(images[imageIndex]);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(fadeInDuration);

        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(fadeInDuration + timeBetween);
        fadeOut.setDuration(fadeOutDuration);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        animation.setRepeatCount(1);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                if (images.length - 1 > imageIndex) {
                    animate(imageView, images, imageIndex + 1,forever);
                }
                else {
                    if (forever){
                        animate(imageView, images, 0,forever);
                    }
                }
            }
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
            }
        });
    }
}
