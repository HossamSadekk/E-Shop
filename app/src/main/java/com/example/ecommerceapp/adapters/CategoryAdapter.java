package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.CategoryItemBinding;
import com.example.ecommerceapp.listeners.CategoryListener;
import com.example.ecommerceapp.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryviewHolder> {
    private CategoryItemBinding categoryItemBinding;
    private LayoutInflater layoutInflater;
    private List<Category> categoryList;
    private CategoryListener categoryListener;

    public CategoryAdapter(List<Category> categoryList, CategoryListener categoryListener) {
        this.categoryList = categoryList;
        this.categoryListener = categoryListener;
    }

    @NonNull
    @Override
    public CategoryviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        categoryItemBinding  = DataBindingUtil.inflate(layoutInflater, R.layout.category_item,parent,false);
        return new CategoryviewHolder(categoryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryviewHolder holder, int position) {
        holder.bind(categoryList.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryviewHolder extends RecyclerView.ViewHolder {
        private CategoryItemBinding categoryItemBinding;
        public CategoryviewHolder(@NonNull CategoryItemBinding categoryItemBinding) {
            super(categoryItemBinding.getRoot());
            this.categoryItemBinding = categoryItemBinding;
        }
        void bind(Category category)
        {
            categoryItemBinding.setCategory(category);
            categoryItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoryListener.onCategoryClicked(category.getName());
                }
            });
        }
    }
}
