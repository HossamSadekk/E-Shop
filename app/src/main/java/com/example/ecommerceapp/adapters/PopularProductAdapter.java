package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.PopularProductItemBinding;
import com.example.ecommerceapp.listeners.ProductListener;
import com.example.ecommerceapp.models.Product;

import java.util.List;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.PopularViewHolder>{
    private ProductListener productListener;
    private List<Product> popularList;
    private LayoutInflater layoutInflater;
    private PopularProductItemBinding popularProductItemBinding;

    public PopularProductAdapter(ProductListener productListener, List<Product> popularList) {
        this.productListener = productListener;
        this.popularList = popularList;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        popularProductItemBinding  = DataBindingUtil.inflate(layoutInflater, R.layout.popular_product_item,parent,false);
        return new PopularViewHolder(popularProductItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        holder.bind(popularList.get(position));
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {
        private PopularProductItemBinding productItemBinding;
        public PopularViewHolder(@NonNull PopularProductItemBinding productItemBinding) {
            super(productItemBinding.getRoot());
            this.productItemBinding = productItemBinding;
        }
        private void bind(Product product)
        {
            productItemBinding.setProduct(product);
            productItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productListener.onProductClicked(product);
                }
            });
        }

    }
}
