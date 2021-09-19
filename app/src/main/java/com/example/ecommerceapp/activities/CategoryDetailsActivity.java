package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.CategoryDetailsAdapter;
import com.example.ecommerceapp.databinding.ActivityCategoryDetailsBinding;
import com.example.ecommerceapp.listeners.ProductListener;
import com.example.ecommerceapp.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerceapp.activities.RegisterActivity.TAG;

public class CategoryDetailsActivity extends AppCompatActivity implements ProductListener {
    private ActivityCategoryDetailsBinding categoryDetailsBinding;
    private List<Product> productList;
    private CategoryDetailsAdapter categoryDetailsAdapter;
    private FirebaseFirestore firestore;
    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_category_details);
        firestore = FirebaseFirestore.getInstance();
        item = getIntent().getStringExtra("item");
        categoryDetailsBinding.title.setText(item);
        setupRecyclerView();
        getDataOfItem(item);
    }

    private void getDataOfItem(String item) {
        firestore.collection(item)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                categoryDetailsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents."+task.getException().getMessage());
                        }
                    }
                });

    }

    private void setupRecyclerView() {
        categoryDetailsBinding.categoryDetailsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        productList = new ArrayList<>();
        categoryDetailsAdapter = new CategoryDetailsAdapter(this,productList);
        categoryDetailsBinding.categoryDetailsRv.setAdapter(categoryDetailsAdapter);
    }

    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("product",product);
        startActivity(intent);
    }
}