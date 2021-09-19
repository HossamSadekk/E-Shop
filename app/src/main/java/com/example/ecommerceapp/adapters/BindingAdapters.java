package com.example.ecommerceapp.adapters;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingAdapters {

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ImageView imageView, String URL)
    {
        if(!URL.isEmpty())
        {
            Glide.with(imageView).load(URL).into(imageView);
        }
    }

}
