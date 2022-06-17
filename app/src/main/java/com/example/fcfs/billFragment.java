package com.example.fcfs;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class billFragment extends Fragment {

    private BillViewModel mViewModel;
    TextView billno,order,status,amount;
    View bills;
    SharedPreferences sp;
    FirebaseAuth fauth;
    FirebaseFirestore fdb;

    public static billFragment newInstance() {
        return new billFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bills =  inflater.inflate(R.layout.bill_fragment, container, false);

        billno = bills.findViewById(R.id.nbill);
        order = bills.findViewById(R.id.norder);
        status= bills.findViewById(R.id.nstatus);
        amount = bills.findViewById(R.id.namount);
        sp = getActivity().getSharedPreferences("key", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit = sp.edit();

        billno.setText("Bill no :"+String.valueOf(sp.getInt("bill no",-1)));
        order.setText(sp.getString("order"," "));
        amount.setText("Amount :"+sp.getString("amount"," "));
        status.setText("Status: pending");

//        fauth= FirebaseAuth.getInstance();
//        fdb = FirebaseFirestore.getInstance();
//
//        shopsFragment s1 = new shopsFragment();
//
//        String str=s1.token;
//
//        Toast.makeText(getActivity(),""+str,Toast.LENGTH_LONG).show();

        return bills;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BillViewModel.class);
        // TODO: Use the ViewModel
    }

}