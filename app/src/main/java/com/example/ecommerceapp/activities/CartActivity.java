package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.MyCartAdapter;
import com.example.ecommerceapp.databinding.ActivityCartBinding;
import com.example.ecommerceapp.models.Cart;
import com.example.ecommerceapp.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding cartBinding;
    private List<Cart> cartList;
    private MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private int totalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartBinding = DataBindingUtil.setContentView(this,R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        totalPrice = 0;

        cartBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //recycler_view
        setupRecyclerView();
        // get orders details
        getOrdersDetails();

        cartBinding.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this,PaymentActivity.class);
                intent.putExtra("amount",totalPrice); // total price
                intent.putExtra("Activity","Cart"); // total price
                Bundle bundle = new Bundle();
                cartList.clear();
                startActivity(intent);
                finish();
            }
        });

    }



    private void getOrdersDetails() {
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection(auth.getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Cart cart = document.toObject(Cart.class);
                    totalPrice = totalPrice + cart.getTotalPrice();
                    cartList.add(cart);
                    cartAdapter.notifyDataSetChanged();
                }
                cartBinding.totalPrice.setText("Your Total Price:"+totalPrice+"EGP");
            }
        });
    }

    private void setupRecyclerView() {
        cartBinding.ordersRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        cartList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(cartList);
        cartBinding.ordersRv.setAdapter(cartAdapter);
    }
}