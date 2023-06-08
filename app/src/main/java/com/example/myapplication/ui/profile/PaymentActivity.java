package com.example.myapplication.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    TextView subTotal, shipping, total;
    Button checkOutButton;
    int subTotalBill, shippingInt, totalInt;
    String subTotalBillString, shippingString, totalBillString;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        subTotalBill = getIntent().getIntExtra("totalPrice",0);
        subTotal = findViewById(R.id.sub_total);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        checkOutButton = findViewById(R.id.pay_btn);

        subTotalBillString = Integer.toString(subTotalBill);
        subTotalBillString = subTotalBillString+"$";
        subTotal.setText(String.valueOf(subTotalBillString));

        shippingString = shipping.getText().toString();
        String[] parts = shippingString.split("\\$"); // Split the string at the dollar sign
        String numericValue = parts[0];

        shippingInt = Integer.parseInt(numericValue);

        totalInt = subTotalBill + shippingInt;

        totalBillString= String.valueOf(totalInt);
        totalBillString+="$";
        total.setText(totalBillString);

        setupListeners();

    }
    public void setupListeners() {
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod();
            }
        });
    }
    private void paymentMethod(){
        // on below line we are getting
        // amount that is entered by user.
        String billString = total.getText().toString();
        String[] parts = billString.split("\\$"); // Split the string at the dollar sign
        String value = parts[0];

        // rounding off the amount.
        int amount = Math.round(Float.parseFloat(value) * 100);

        // initialize Razorpay account.
        Checkout checkout = new Checkout();

        // set your id as below
        checkout.setKeyID("rzp_test_B12LeGXrVrGMzg");

        // set image
        checkout.setImage(R.drawable.image);

        // initialize json object
        JSONObject object = new JSONObject();
        try {
            // to put name
            object.put("name", "Meal planning App");

            // put description
            object.put("description", "Test payment");

            // to set theme color
            object.put("theme.color", "#25383C");

            // put the currency
            object.put("currency", "USD");

            // put amount
            object.put("amount", amount);

            // put mobile number
            object.put("prefill.contact", "9876543210");

            // put email
            object.put("prefill.email", "benzarnna@gmail.com");

            // open razorpay to checkout activity
            checkout.open(PaymentActivity.this, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Cancel", Toast.LENGTH_SHORT).show();
    }
}
