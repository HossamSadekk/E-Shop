package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.CategoryDetailsItemBinding;
import com.example.ecommerceapp.listeners.ProductListener;
import com.example.ecommerceapp.models.Product;

import java.util.List;

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.CategoryDetailsViewHolder> {
    private ProductListener productListener;
    private List<Product> list;
    private LayoutInflater layoutInflater;
    private CategoryDetailsItemBinding productItemBinding;

    public CategoryDetailsAdapter(ProductListener productListener, List<Product> list) {
        this.productListener = productListener;
        this.list = list;
    }

    @NonNull
    @Override
    public CategoryDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        productItemBinding  = DataBindingUtil.inflate(layoutInflater, R.layout.category_details_item,parent,false);
        return new CategoryDetailsViewHolder(productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDetailsViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CategoryDetailsViewHolder extends RecyclerView.ViewHolder {
        private CategoryDetailsItemBinding categoryDetailsItemBinding;
        public CategoryDetailsViewHolder(@NonNull CategoryDetailsItemBinding categoryDetailsItemBinding) {
            super(categoryDetailsItemBinding.getRoot());
            this.categoryDetailsItemBinding = categoryDetailsItemBinding;
        }
        void bind(Product product)
        {
            categoryDetailsItemBinding.setProduct(product);
            categoryDetailsItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productListener.onProductClicked(product);
                }
            });
        }
    }
}
