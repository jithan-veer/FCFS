package com.example.fcfs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class deliveryUpdate extends AppCompatActivity {

    RecyclerView rec;
    ArrayList<OrderModel> arp;
    myAdapter map;
    FirebaseFirestore fdb;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_update);

        pg = new ProgressDialog(this);
        pg.setCancelable(false);
        pg.setMessage("Fetching data.....");
        pg.show();

        rec= findViewById(R.id.recview);
        rec.setLayoutManager(new LinearLayoutManager(this));
        rec.setHasFixedSize(true);
        fdb = FirebaseFirestore.getInstance();
        Query q = fdb.collection("orders").orderBy("order");
        arp = new ArrayList<OrderModel>();
        map = new myAdapter(getApplicationContext(),arp);

        rec.setAdapter(map);
        
        startdb();


    }

    private void startdb() {

        fdb.collection("orders").orderBy("order", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null){
                            if(pg.isShowing())
                                pg.dismiss();
                            Toast.makeText(getApplicationContext(),""+error,Toast.LENGTH_LONG).show();
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                arp.add(dc.getDocument().toObject(OrderModel.class));
                            }
                            map.notifyDataSetChanged();
                            if(pg.isShowing())
                                pg.dismiss();
                        }
                    }
                });
    }
}