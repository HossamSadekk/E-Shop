package com.example.ecommerceapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.CartItemBinding;
import com.example.ecommerceapp.models.Cart;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {

    private LayoutInflater layoutInflater;
    private CartItemBinding cartItemBinding;
    private List<Cart> cartList;

    public MyCartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(parent.getContext());
        cartItemBinding  = DataBindingUtil.inflate(layoutInflater, R.layout.cart_item,parent,false);
        return new CartViewHolder(cartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.cartItemBinding.currentDateTxt.setText(cartList.get(position).getCurrentDate());
        holder.cartItemBinding.currentTimeTxt.setText(cartList.get(position).getCurrentTime());
        holder.cartItemBinding.productNameTxt.setText(cartList.get(position).getProductName());
        holder.cartItemBinding.totalPriceTxt.setText(cartList.get(position).getTotalPrice()+"EGP");
        holder.cartItemBinding.productPriceTxt.setText(cartList.get(position).getProductPrice()+"");
        holder.cartItemBinding.totalQuantityTxt.setText(cartList.get(position).getTotalQuantity()+"");
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private CartItemBinding cartItemBinding;
        public CartViewHolder(@NonNull CartItemBinding cartItemBinding) {
            super(cartItemBinding.getRoot());
            this.cartItemBinding = cartItemBinding;
        }
    }
}
