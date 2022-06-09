package com.example.fcfs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class signup extends AppCompatActivity {
    Button btn;
    TextInputEditText email,username,pass,confpass,phone;
    FirebaseAuth fauth;
    FirebaseFirestore fdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btn=findViewById(R.id.signup);
        email=findViewById(R.id.email);
        username=findViewById(R.id.username);
        pass=findViewById(R.id.pass);
        confpass=findViewById(R.id.conf_pass);
        phone=findViewById(R.id.phone);
        fauth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uemail = email.getText().toString().trim();
                String upass = pass.getText().toString().trim();
                String uname = username.getText().toString().trim();
                String ucpass = confpass.getText().toString().trim();
                String phon = phone.getText().toString().trim();
                if(!uemail.endsWith("student.tce.edu")){
                    Toast.makeText(getApplicationContext(),"You are not from Tce",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!upass.equals(ucpass)){
                    confpass.setError("Passwords  don't match");
                    return;
                }
                if(upass.length() < 6){
                    pass.setError("password is too weak");
                    return;
                }
                if(phon.length() != 10){
                    phone.setError("invalid phone number");
                    return;
                }

                fauth.createUserWithEmailAndPassword(uemail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(),User_home.class);
                            HashMap<String,String> users = new HashMap<>();
                            users.put("name",uname);
                            users.put("email",uemail);
                            users.put("phone",phon);
                            users.put("orders","0");
                            users.put("cost","0");
                            DocumentReference df = fdb.collection("users").document(fauth.getCurrentUser().getUid());
                            df.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"created",Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"error: "+e,Toast.LENGTH_LONG).show();
                                }
                            });
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"error! : "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}