package com.example.fcfs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Arrays;

public class delivery extends AppCompatActivity {
    TextInputEditText b;
    Button search,deliver;
    FirebaseFirestore fdb;
    FirebaseAuth fauth;
    String orders,phone1;
    TextView tv[] = new TextView[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        b = findViewById(R.id.bill1);
        search = findViewById(R.id.search);
        deliver = findViewById(R.id.deliver);
        fauth=FirebaseAuth.getInstance();
        tv[0]=findViewById(R.id.o1);
        tv[1]=findViewById(R.id.q1);
        tv[2]=findViewById(R.id.o2);
        tv[3]=findViewById(R.id.q2);
        tv[4]=findViewById(R.id.o3);
        tv[5]=findViewById(R.id.q3);
        tv[6]=findViewById(R.id.o4);
        tv[7]=findViewById(R.id.q4);
        fdb=FirebaseFirestore.getInstance();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = b.getText().toString().trim();
                DocumentReference df = fdb.collection("orders").document(str);
                df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                             orders = value.getString("order");
                             phone1 = value.getString("phone");
//                        Toast.makeText(getApplicationContext(),""+orders,Toast.LENGTH_LONG).show();
                        String[] x = orders.split(" ");
                        for(int i=0;i<x.length;i++){
                            tv[i].setText(x[i]);
                        }

                    }
                });
//


            }
        });

        deliver.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),phone1,Toast.LENGTH_LONG).show();
                sendSMS();
            }
        });
    }

    private void sendSMS() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
//                    String phn = e1.getText().toString();
//                    String msg = e2.getText().toString();
                    SmsManager smsManager = SmsManager.getDefault(); smsManager.sendTextMessage(phone1, null, "Your order is ready....do collect it", null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show(); }
                else
                {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }

        }
    }
}