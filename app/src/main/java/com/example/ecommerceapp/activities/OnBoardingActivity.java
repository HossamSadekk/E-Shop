package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.SliderOnBoardingAdapter;
import com.example.ecommerceapp.databinding.ActivityOnBoardingBinding;

public class OnBoardingActivity extends AppCompatActivity {

    private ActivityOnBoardingBinding onBoardingBinding;
    private SliderOnBoardingAdapter sliderOnBoardingAdapter;
    private Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBoardingBinding = DataBindingUtil.setContentView(this,R.layout.activity_on_boarding);


        setupViewPager();

        // handling start button
        onBoardingBinding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OnBoardingActivity.this,RegisterActivity.class));
            }
        });


    }

    //setupViewPager2 & Indicator
    private void setupViewPager()
    {
        sliderOnBoardingAdapter = new SliderOnBoardingAdapter();
        onBoardingBinding.slider.setAdapter(sliderOnBoardingAdapter);
        setupSliderIndicator();
        onBoardingBinding.slider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicator(position);
            }
        });
    }

    private void setupSliderIndicator()
    {
        ImageView[] indicators = new ImageView[3];
        /*
         LayoutParams are used by views to tell their parents how they want to be laid out.
         LayoutParams class just describes how big the view wants to be for both width and height. For each dimension
        */
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(10,10);
        layoutParams.rightMargin = 5;

        for (int i = 0; i < indicators.length ; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.slider_indicator1));
            indicators[i].setLayoutParams(layoutParams);
            onBoardingBinding.layoutSlideIndicator.addView(indicators[i]);
        }
        setCurrentSliderIndicator(0);
    }

    private void setCurrentSliderIndicator(int position)
    {   // get how much indicators layoutSlideIndicator has.
        int childCount = onBoardingBinding.layoutSlideIndicator.getChildCount();
        for (int i = 0; i < childCount ; i++)
        {
            ImageView imageView = (ImageView) onBoardingBinding.layoutSlideIndicator.getChildAt(i);
            if(position == i)
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.slider_indicator2));
            }
            else
            {
                imageView.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.slider_indicator1));
            }
            if(position==2)
            {
                animation = AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                onBoardingBinding.start.setAnimation(animation);
                onBoardingBinding.start.setVisibility(View.VISIBLE);
            }
            else
            {
                onBoardingBinding.start.setVisibility(View.INVISIBLE);
            }
        }
    }
}