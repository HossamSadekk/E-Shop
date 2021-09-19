package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.NewProductItemBinding;
import com.example.ecommerceapp.listeners.ProductListener;
import com.example.ecommerceapp.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ProductListener productListener;
    private List<Product> productList;
    private LayoutInflater layoutInflater;
    private NewProductItemBinding productItemBinding;

    public ProductAdapter(ProductListener productListener, List<Product> productList) {
        this.productListener = productListener;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        productItemBinding  = DataBindingUtil.inflate(layoutInflater, R.layout.new_product_item,parent,false);
        return new ProductViewHolder(productItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private NewProductItemBinding productItemBinding;
        public ProductViewHolder(@NonNull NewProductItemBinding productItemBinding) {
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
