package com.example.ecommerceapp.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.CategoryDetailsActivity;
import com.example.ecommerceapp.activities.DetailsActivity;
import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.PopularProductAdapter;
import com.example.ecommerceapp.adapters.ProductAdapter;
import com.example.ecommerceapp.databinding.FragmentHomeBinding;
import com.example.ecommerceapp.listeners.CategoryListener;
import com.example.ecommerceapp.listeners.ProductListener;
import com.example.ecommerceapp.models.Category;
import com.example.ecommerceapp.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.ecommerceapp.activities.RegisterActivity.TAG;

public class HomeFragment extends Fragment implements ProductListener, CategoryListener {
    private ProgressDialog progressDialog;
    private FragmentHomeBinding homeBinding;
    private List<SlideModel> slideModelList;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private PopularProductAdapter popularProductAdapter;
    private List<Category> categoryList;
    private List<Product> productList;
    private List<Product> popularList;
    private FirebaseFirestore firestore;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        homeBinding.parent.setVisibility(View.GONE);
        firestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());

        // Progress Dialog
        progressDialog.setTitle("Welcome To E-Shop");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        // image slider
        setupImageSlider();

        // category recycler view
        setupCategoryRecyclerView();
        getCategoryList();

        // new products
        setupNewProductsRecyclerView();
        getNewProductsList();

        // popular products
        setupPopularRecyclerView();
        getPopularList();


        return homeBinding.getRoot();
    }

    // Setting The Image Slider
    private void setupImageSlider() {
        slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.banner1,"Discount On Shoes Now!", ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner2,"Discount On Perfumes Now!", ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner3,"70% OFF Hurry Up!", ScaleTypes.CENTER_CROP));
        homeBinding.imageSlider.setImageList(slideModelList);
    }

    // Setting Recycler View
    private void setupCategoryRecyclerView() {
        homeBinding.categoryRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(categoryList,this);
        homeBinding.categoryRv.setAdapter(categoryAdapter);
    }
    private void setupNewProductsRecyclerView() {
        homeBinding.newProductRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(this,productList);
        homeBinding.newProductRv.setAdapter(productAdapter);
    }
    private void setupPopularRecyclerView()
    {
        homeBinding.popularProductRv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        popularList = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(this,popularList);
        homeBinding.popularProductRv.setAdapter(popularProductAdapter);
    }

    // Getting Data From Firebase FireStore
    private void getPopularList() {
        firestore.collection("popular products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                popularList.add(product);
                                productAdapter.notifyDataSetChanged();
                                homeBinding.parent.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents."+task.getException().getMessage());
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Check Your Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void getNewProductsList() {
        firestore.collection("new products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product product = document.toObject(Product.class);
                                productList.add(product);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents."+task.getException().getMessage());
                        }
                    }
                });

    }
    private void getCategoryList() {
        firestore.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Category category = document.toObject(Category.class);
                                categoryList.add(category);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents."+task.getException().getMessage());
                        }
                    }
                });

    }

    // when user click on product
    @Override
    public void onProductClicked(Product product) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("product",product);
        startActivity(intent);

    }

    @Override
    public void onCategoryClicked(String product) {
        Intent intent = new Intent(getActivity(), CategoryDetailsActivity.class);
        intent.putExtra("item",product);
        startActivity(intent);
    }
}