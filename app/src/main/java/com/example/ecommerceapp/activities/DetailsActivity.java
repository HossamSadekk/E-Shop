package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.ActivityDetailsBinding;
import com.example.ecommerceapp.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding detailsBinding;
    private Product product;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    int totalQuantity = 1;
    int totalPrice = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        product = (Product) getIntent().getSerializableExtra("product");
        totalQuantity = Integer.parseInt(detailsBinding.numberOfItem.getText().toString());
        totalPrice = product.getPrice();
        detailsBinding.setProduct(product);

        // handling when click on back button
        detailsBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // buy now button
        detailsBinding.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, PaymentActivity.class);

                intent.putExtra("amount", totalPrice);
                intent.putExtra("quantity",totalQuantity);
                intent.putExtra("productName",product.getName());
                intent.putExtra("Activity","Buy_now");
                startActivity(intent);
            }
        });

        // to cart button
        detailsBinding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        detailsBinding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity < 10)
                {
                    totalQuantity++;
                    totalPrice=totalPrice+product.getPrice();
                    detailsBinding.numberOfItem.setText(String.valueOf(totalQuantity));
                    detailsBinding.price.setText(totalPrice+"EGP");
                }
            }
        });
        detailsBinding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1)
                {
                    totalQuantity--;
                    totalPrice=totalPrice-product.getPrice();
                    detailsBinding.numberOfItem.setText(String.valueOf(totalQuantity));
                    detailsBinding.price.setText(totalPrice+"EGP");
                }
            }
        });

    }

    private void addToCart() {
        String saveCurrentTime;
        String saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MM,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",product.getName());
        cartMap.put("productPrice",product.getPrice());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",totalQuantity);
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection(auth.getCurrentUser().getEmail()).add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(DetailsActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}