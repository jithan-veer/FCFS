package com.example.fcfs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.TelephonyNetworkSpecifier;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.viewholder> {

    Context context;
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

    }

    @Override
    public int getItemCount() {
        return ar.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        TextView order,phone;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            order = itemView.findViewById(R.id.horder);
            phone= itemView.findViewById(R.id.hphone);
        }
    }
}
