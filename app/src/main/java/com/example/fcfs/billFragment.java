package com.example.fcfs;

import androidx.lifecycle.ViewModelProvider;

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
    FirebaseAuth fauth;
    FirebaseFirestore fdb;

    public static billFragment newInstance() {
        return new billFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bills =  inflater.inflate(R.layout.bill_fragment, container, false);

//        billno = bills.findViewById(R.id.nbill);
//        order = bills.findViewById(R.id.norder);
//        status= bills.findViewById(R.id.nstatus);
//        amount = bills.findViewById(R.id.namount);
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