package com.example.fcfs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.viewholder> {

    static Context context;
    ArrayList<OrderModel> ar;

    public myAdapter(Context context, ArrayList<OrderModel> ar) {
        this.context = context;
        this.ar = ar;
    }

    @NonNull
    @Override
    public myAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.tokenupdated,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myAdapter.viewholder holder, int position) {

        OrderModel om = ar.get(position);

        holder.order.setText(OrderModel.order);
        holder.phone.setText(OrderModel.phone);
        holder.p.setText("BILL NO: "+String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView order,phone,p;
        Button c,m,can;
        Context con;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            order = itemView.findViewById(R.id.horder);
            phone= itemView.findViewById(R.id.hphone);
            p=itemView.findViewById(R.id.pos);
//            c=itemView.findViewById(R.id.call);
//            m=itemView.findViewById(R.id.);
//            c=itemView.findViewById(R.id.call);


        }
    }
}
