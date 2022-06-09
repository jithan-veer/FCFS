package com.example.fcfs;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    Button btn;
    View profiles;
    FirebaseAuth fauth;
    TextInputEditText name,mail,phone;
    TextView under1,orders,amount;
    FirebaseFirestore fdb;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profiles = inflater.inflate(R.layout.profile_fragment, container, false);
        btn = profiles.findViewById(R.id.logout);
        name = profiles.findViewById(R.id.puname);
        mail = profiles.findViewById(R.id.puemail);
        phone = profiles.findViewById(R.id.puphone);
        under1 = profiles.findViewById(R.id.under);
        orders = profiles.findViewById(R.id.nooforders);
        amount = profiles.findViewById(R.id.amt);
        fauth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        DocumentReference df = fdb.collection("users").document(fauth.getCurrentUser().getUid());
        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                under1.setText(value.getString("name"));
                name.setText(value.getString("name"));
                mail.setText(value.getString("email"));
                phone.setText(value.getString("phone"));
                orders.setText(value.getString("orders"));
                amount.setText(value.getString("cost"));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fauth.signOut();
                Intent i = new Intent(getActivity(),Register_Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                getActivity().finish();

            }
        });

        return profiles;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}