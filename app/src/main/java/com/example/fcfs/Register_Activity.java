package com.example.fcfs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {
    Button btn;
    TextView tv1;
    TextInputEditText user,pass;
    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        btn = findViewById(R.id.login);
        user=findViewById(R.id.uname);
        pass=findViewById(R.id.upass);
        tv1= findViewById(R.id.newuser);
        fauth= FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String password = pass.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    user.setError("both the fields required");
                    return;
                }

                //authentication

                if(username.equals("admin") && password.equals("12345600")){
                    Intent i = new Intent(getApplicationContext(),admin_home.class);
                    startActivity(i);
                }

                else{
                    fauth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent i = new Intent(getApplicationContext(),User_home.class);
                                startActivity(i);
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),"Invalid credentials",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),signup.class);
                startActivity(i);
            }
        });
    }
}