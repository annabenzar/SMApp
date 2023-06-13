package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OneProductActivity extends AppCompatActivity {

    public static Context context;
    private ImageView imageProduct,minusButton,plusButton;
    private TextView productDetails, nameProduct,descriptionProduct,priceProduct,actualPriceProduct,quantityText;
    private Button addToCartButton;
    String imgUrl, productName, productPrice;
    private int quantity = 1, extractedPrice;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_product);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Bundle bundle = getIntent().getExtras();
        initializeViews();
        setValues(bundle);
        setupListeners();
    }

    @SuppressLint("WrongViewCast")
    public void initializeViews(){
        productDetails=findViewById(R.id.details_product);
        imageProduct=findViewById(R.id.image_product);
        nameProduct=findViewById(R.id.name_product);
        descriptionProduct=findViewById(R.id.description_product);
        priceProduct=findViewById(R.id.price_text_product);
        actualPriceProduct=findViewById(R.id.price_product);
        minusButton=findViewById(R.id.minus_button);
        quantityText=findViewById(R.id.quantity_text);
        plusButton=findViewById(R.id.plus_button);
        addToCartButton=findViewById(R.id.add_to_cart_button);
    }

    public void setValues(Bundle bundle){
        imgUrl = bundle.getString("url");
        productName = bundle.getString("name");
        productPrice = bundle.getString("price");
        context = getApplicationContext();

        Glide.with(context).load(imgUrl).into(imageProduct);
        nameProduct.setText(productName);
        actualPriceProduct.setText(productPrice);
    }

    public void setupListeners() {
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    quantityText.setText(String.valueOf(quantity));
                }
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                quantityText.setText(String.valueOf(quantity));
            }
        });
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }
    private void addToCart() {
        final int[] cnt = {0};
        String saveCurrentTime, saveCurrentDate;
        Calendar calendarForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM yyyy");
        saveCurrentDate = currentDate.format(calendarForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarForDate.getTime());
        String totalPrice = getTotalPrice();

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("productName", nameProduct.getText().toString());
        cartMap.put("productTotalPrice", totalPrice);
        cartMap.put("productTotalQuantity", quantityText.getText().toString());
        cartMap.put("productURL", imgUrl);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                String productName = doc.getString("productName");
                                String givenProductName = nameProduct.getText().toString();
                                if (productName.equals(givenProductName)) {
                                    String documentUid = doc.getId();
                                    String productTotalPrice = doc.getString("productTotalPrice");
                                    String productTotalQuantity = doc.getString("productTotalQuantity");
                                    cnt[0] += 1;
                                    editExistingProduct(cartMap, documentUid, productTotalPrice, productTotalQuantity);
                                    break;
                                }
                            }
                            if (cnt[0] == 0) {
                                addNewProductToCart(cartMap);
                            }
                        }
                    }
                });
    }
    public void editExistingProduct(HashMap<String, Object> cartMap,String documentID, String productTotalPrice, String productTotalQuantity){
        int priceFromDocInt, quantityFromDocInt;
        priceFromDocInt = Integer.parseInt(productTotalPrice);
        quantityFromDocInt = Integer.parseInt(productTotalQuantity);
        String totalPriceFromProduct = getTotalPrice();
        String quantityFromProduct = quantityText.getText().toString();
        int priceFromProductInt, quantityfromProductInt;
        priceFromProductInt = Integer.parseInt(totalPriceFromProduct);
        quantityfromProductInt = Integer.parseInt(quantityFromProduct);
        int totalPrice, totalQuantity;
        totalPrice = priceFromDocInt + priceFromProductInt;
        totalQuantity = quantityFromDocInt + quantityfromProductInt;
        String totalPriceString, totalQuantityString;
        totalPriceString = Integer.toString(totalPrice);
        totalQuantityString = Integer.toString(totalQuantity);
        cartMap.put("productTotalPrice",totalPriceString);
        cartMap.put("productTotalQuantity",totalQuantityString);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").document(documentID).set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Added existing product to your cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    public void addNewProductToCart(HashMap<String, Object> cartMap) {
        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(getApplicationContext(), "Added new product to your cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    private int extractPrice() {
        String[] priceParts = productPrice.split("\\$");
        if (priceParts.length > 0) {
            String priceValue = priceParts[0];
            int priceValueInteger = Integer.parseInt(priceValue);
            return priceValueInteger;
        }
        return 0;
    }
    private String getTotalPrice(){
        extractedPrice = extractPrice();
        String quantityString = quantityText.getText().toString();
        int quantityInteger = Integer.parseInt(quantityString);
        int totalPrice  = extractedPrice * quantityInteger;
        String totalPriceString = Integer.toString(totalPrice);
        return totalPriceString;
    }

}
