package com.example.fcfs;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class shopsFragment extends Fragment {

    private ShopsViewModel mViewModel;
    CardView cv;
    View shops;
    FirebaseAuth fauth;
    FirebaseFirestore fdb;
    String str;
    String token;

    public static shopsFragment newInstance() {
        return new shopsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         shops = inflater.inflate(R.layout.shops_fragment, container, false);
         cv = shops.findViewById(R.id.shop1);
        fauth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        DocumentReference df = fdb.collection("users").document(fauth.getCurrentUser().getUid());
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                str = value.getString("phone");
            }
        });
        DocumentReference df2 = fdb.collection("identity").document("token");
        df2.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                token = value.getString("current");
            }
        });
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),""+str+" "+token,Toast.LENGTH_LONG).show();
                Intent i =  new Intent(getActivity(),OrderFood.class);
                i.putExtra("number",str);
                i.putExtra("token",token);
                startActivity(i);
            }
        });
        return shops;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShopsViewModel.class);
        // TODO: Use the ViewModel
    }

}