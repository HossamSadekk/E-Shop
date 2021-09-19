package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.databinding.ActivityPaymentBinding;
import com.example.ecommerceapp.models.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private ActivityPaymentBinding paymentBinding;
    private FirebaseFirestore firestore;
    private int amount;
    private int shipping;
    private int discount;
    private int total;
    private int totalQuantity;
    private String productName;
    private String client_name;
    private ArrayList<Cart> cartList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paymentBinding = DataBindingUtil.setContentView(this,R.layout.activity_payment);
        firestore = FirebaseFirestore.getInstance();
        //getting total amount of money
        amount = getIntent().getIntExtra("amount",0);

        //getting total quantity
        totalQuantity = getIntent().getIntExtra("quantity",0);

        // getting product name
        productName = getIntent().getStringExtra("productName");

        //getting user name
         firestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                client_name = task.getResult().get("userName").toString();
            }
        });
        cartList = new ArrayList<>();
        shipping = Integer.parseInt(paymentBinding.shipping.getText().toString());
        discount = Integer.parseInt(paymentBinding.discount.getText().toString());
        if(amount!=0)
        {
            paymentBinding.subTotal.setText(amount+"EGP");
        }
        total = amount + shipping + discount;
        paymentBinding.total.setText(total+"EGP");
        paymentBinding.checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("Activity").equals("Buy_now")){
                    checkOut();
                }
                else if(getIntent().getStringExtra("Activity").equals("Cart"))
                {
                    checkOutCart();
                }
            }
        });

    }

    private void deleteCartContents()
    {

        firestore.collection("AddToCart").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<String> ids = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                firestore.collection("AddToCart").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                        .document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        }
                    }
                });
    }

                    private void checkOutCart() {
                        Map<String, Object> payment = new HashMap<>();
                        payment.put("clientID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        payment.put("client_Name", client_name);
                        payment.put("subtotal", amount + "EGP");
                        payment.put("shipping", shipping + "EGP");
                        payment.put("discount", discount + "EGP");
                        payment.put("total", total + "EGP");
                        firestore.collection("AddToCart").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .collection(FirebaseAuth.getInstance().getCurrentUser().getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Cart cart = document.toObject(Cart.class);
                                    cartList.add(cart);

                                }

                                payment.put("cart", cartList);
                                firestore.collection("Orders")
                                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("information").add(payment)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(PaymentActivity.this, "Your Order Is Shipping!", Toast.LENGTH_SHORT).show();
                                            deleteCartContents();
                                        } else {
                                            Toast.makeText(PaymentActivity.this, "shit" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        });
                        cartList.clear();
                    }

                    private void checkOut() {
                        String saveCurrentTime;
                        String saveCurrentDate;
                        Calendar calForDate = Calendar.getInstance();
                        SimpleDateFormat currentDate = new SimpleDateFormat("dd,MM,yyyy");
                        saveCurrentDate = currentDate.format(calForDate.getTime());

                        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                        saveCurrentTime = currentTime.format(calForDate.getTime());

                        Map<String, String> payment = new HashMap<>();
                        payment.put("subtotal", amount + "EGP");
                        payment.put("shipping", shipping + "EGP");
                        payment.put("discount", discount + "EGP");
                        payment.put("total", total + "EGP");
                        payment.put("totalQuantity", String.valueOf(totalQuantity));
                        payment.put("productName", productName);
                        payment.put("currentTime", saveCurrentTime);
                        payment.put("currentDate", saveCurrentDate);
                        payment.put("clientID", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        payment.put("client_Name", client_name);
                        firestore.collection("Orders")
                                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .collection("information").add(payment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PaymentActivity.this, "Your Order Is Shipping!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(PaymentActivity.this, MainActivity.class));
                                }
                            }
                        });
                    }
                }


