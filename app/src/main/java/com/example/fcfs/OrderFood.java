package com.example.fcfs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderFood extends AppCompatActivity {

    AutoCompleteTextView o1,o2,o3,o4,q1,q2,q3,q4;
    String[] food = {"Panner_dosa","Mushroom_Dosa","Special_Dosa","Ghee_Roast","Samosa","Tea","Coffee","Idly","Softie","Sambhar_rice","Parotta"};
    int[] price={40,40,50,35,7,10,10,20,25,35,25};
    Integer[] quantity = {1,2,3,4,5};
    Button btn;
    String order="",phone="";
    int n;
    FirebaseFirestore fdb;
    FirebaseAuth fauth;
    final int UPI_PAYMENT=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);
        o1=findViewById(R.id.order1);
        o2=findViewById(R.id.order2);
        o3=findViewById(R.id.order3);
        o4=findViewById(R.id.order4);
        btn=findViewById(R.id.placeorder);
        fauth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();

        q1=findViewById(R.id.quan1);
        q2=findViewById(R.id.quan2);
        q3=findViewById(R.id.quan3);
        q4=findViewById(R.id.quan4);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.dropdown,food);

        o1.setAdapter(adapter);
        o2.setAdapter(adapter);
        o3.setAdapter(adapter);
        o4.setAdapter(adapter);

        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(getApplicationContext(),R.layout.dropdown,quantity);

        q1.setAdapter(adapter2);
        q2.setAdapter(adapter2);
        q3.setAdapter(adapter2);
        q4.setAdapter(adapter2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String or1,or2,or3,or4;
                    int qu1,qu2,qu3,qu4;
                    int total=0;
                    or1=o1.getText().toString();
                    or2=o2.getText().toString();
                    or3=o3.getText().toString();
                    or4=o4.getText().toString();


                    if(or1.isEmpty()&&or2.isEmpty()&&or3.isEmpty()&&or4.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Please select food items",Toast.LENGTH_LONG).show();
                    }else{
                        if(!or1.isEmpty()){
//
                            for(int i=0;i<food.length;i++){
                                if(food[i].equals(or1)){

                                    qu1 = Integer.parseInt(q1.getText().toString());
                                    total+=(price[i]*qu1);
                                    order+=or1+" "+qu1+" ";
                                }
                            }
                        }
                        if(!or2.isEmpty()){
//
                            for(int i=0;i<food.length;i++){
                                if(food[i].equals(or2)){

                                    qu2 = Integer.parseInt(q2.getText().toString());
                                    total+=(price[i]*qu2);
                                    order+=or2+" "+qu2+" ";
                                }
                            }
                        }
                        if(!or3.isEmpty()){
//
                            for(int i=0;i<food.length;i++){
                                if(food[i].equals(or3)){

                                    qu3 = Integer.parseInt(q3.getText().toString());
                                    total+=(price[i]*qu3);
                                    order+=or3+" "+qu3+" ";
                                }
                            }
                        }
                        if(!or4.isEmpty()){
//
                            for(int i=0;i<food.length;i++){
                                if(food[i].equals(or4)){
                                    qu4 = Integer.parseInt(q4.getText().toString());
                                    total+=(price[i]*qu4);
                                    order+=or4+" "+qu4+" ";
                                }
                            }
                        }
                        String bill = String.valueOf(total);
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(OrderFood.this);
                        builder.setTitle("Confirm order?");
                        builder.setMessage("Your order costs "+bill+". Do you want to continue and pay the bill?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                payUsingUpi("Jayasheelan R","elangoravi21@oksbi","bill payments",bill);
                                phone=getIntent().getStringExtra("number");
                                n=Integer.parseInt(getIntent().getStringExtra("token"));
                                DocumentReference df = fdb.collection("identity").document("token");
                                HashMap<String,String> toke = new HashMap<>();
                                int t=n+1;
//                                String str = ""+t;
                                toke.put("current",""+t);
//                                Toast.makeText(getApplicationContext(),""+t+"hii" ,Toast.LENGTH_LONG).show(); //1
                                df.set(toke).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"error:"+e,Toast.LENGTH_LONG).show();
                                    }
                                });
                                String str = ""+n;
                                DocumentReference df2 = fdb.collection("orders").document(str);
                                HashMap<String,String> list = new HashMap<>();
                                list.put("order",order);
                                list.put("phone",phone);
//                                list.put("cost",bill);
                                df2.set(list).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"error: "+e,Toast.LENGTH_LONG).show();
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"Ordered: bill no: "+n+" wait for text!!",Toast.LENGTH_LONG).show(); //2
                                Intent i = new Intent(getApplicationContext(),User_home.class);
                                startActivity(i);
                                finish();

                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"Order cancelled",Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.show();
                    }

            }

        });


    }

//    private void payUsingUpi(String name, String upiid, String note, String amount) {
//
//        Uri uri = Uri.parse("upi://pay").buildUpon()
//                .appendQueryParameter("pa",upiid)
//                .appendQueryParameter("pn",name)
//                .appendQueryParameter("tn",note)
//                .appendQueryParameter("am",amount)
//                .appendQueryParameter("cu","INR")
//                .build();
//        Intent upi = new Intent(Intent.ACTION_VIEW);
//        upi.setData(uri);
////        startActivity(upi);
//        Intent chooser = Intent.createChooser(upi,"pay with");
//        if(null!=chooser.resolveActivity(getPackageManager())){
//            startActivityForResult(chooser,UPI_PAYMENT,null);
//        }else{
//            Toast.makeText(getApplicationContext(),"install any upi apps to pay",Toast.LENGTH_LONG).show();
//            return;
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case UPI_PAYMENT:
//                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
//                    if (data != null) {
//                        String trxt = data.getStringExtra("response");
//                        Log.d("UPI", "onActivityResult: " + trxt);
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add(trxt);
//                        upiPaymentDataOperation(dataList);
//                    } else {
//                        Log.d("UPI", "onActivityResult: " + "Return data is null");
//                        ArrayList<String> dataList = new ArrayList<>();
//                        dataList.add("nothing");
//                        upiPaymentDataOperation(dataList);
//                    }
//                } else {
//                    Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
//                    ArrayList<String> dataList = new ArrayList<>();
//                    dataList.add("nothing");
//                    upiPaymentDataOperation(dataList);
//                }
//                break;
//        }
//    }
//
//    private void upiPaymentDataOperation(ArrayList<String> data) {
//        if (isConnectionAvailable(OrderFood.this)) {
//            String str = data.get(0);
//            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
//            String paymentCancel = "";
//            if(str == null) str = "discard";
//            String status = "";
//            String approvalRefNo = "";
//            String response[] = str.split("&");
//            for (int i = 0; i < response.length; i++) {
//                String equalStr[] = response[i].split("=");
//                if(equalStr.length >= 2) {
//                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
//                        status = equalStr[1].toLowerCase();
//                    }
//                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
//                        approvalRefNo = equalStr[1];
//                    }
//                }
//                else {
//                    paymentCancel = "Payment cancelled by user.";
//                }
//            }
//
//            if (status.equals("success")) {
//                //Code to handle successful transaction here.
//                Toast.makeText(OrderFood.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
//                Log.d("UPI", "responseStr: "+approvalRefNo);
//                startActivity(new Intent(getApplicationContext(),billFragment.class));
//            }
//            else if("Payment cancelled by user.".equals(paymentCancel)) {
//                Toast.makeText(OrderFood.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Toast.makeText(OrderFood.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(OrderFood.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private boolean isConnectionAvailable(Context context) {
//        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivityManager != null) {
//            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
//            if (netInfo != null && netInfo.isConnected()
//                    && netInfo.isConnectedOrConnecting()
//                    && netInfo.isAvailable()) {
//                return true;
//            }
//        }
//        return false;
//    }
}

