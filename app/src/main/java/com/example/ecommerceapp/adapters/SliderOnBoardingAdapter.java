package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;

import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.SlidingLayoutBinding;

public class SliderOnBoardingAdapter extends RecyclerView.Adapter<SliderOnBoardingAdapter.SliderViewHolder> {
    private SlidingLayoutBinding slidingLayoutBinding;
    private LayoutInflater layoutInflater;


    int imageArray [] = {R.drawable.onboardscreen1,R.drawable.onboardscreen2,R.drawable.onboardscreen3};
    int headingArray [] = {R.string.first_slide,R.string.second_slide,R.string.third_slide};
    int descArray [] = {R.string.desc1,R.string.desc2,R.string.desc3};
    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        slidingLayoutBinding  = DataBindingUtil.inflate(layoutInflater,R.layout.sliding_layout,parent,false);
        return new SliderViewHolder(slidingLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.slidingLayoutBinding.heading.setText(headingArray[position]);
        holder.slidingLayoutBinding.desc.setText(descArray[position]);
        holder.slidingLayoutBinding.sliderIv.setImageResource(imageArray[position]);
    }

    @Override
    public int getItemCount() {
        return headingArray.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        private SlidingLayoutBinding slidingLayoutBinding;
        public SliderViewHolder(@NonNull SlidingLayoutBinding slidingLayoutBinding) {
            super(slidingLayoutBinding.getRoot());
            this.slidingLayoutBinding = slidingLayoutBinding;
        }
    }
}
