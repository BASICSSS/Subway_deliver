package com.example.subway_deliver;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {

    private ArrayList<MainData> arrayList;
    private static String TAG = "subway_deliver";


    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.CustomViewHolder holder, int position) {
        holder.tv_delivery_no.setText(arrayList.get(position).getTv_delivery_no());
        holder.tv_delivery_category.setText(arrayList.get(position).getTv_delivery_category());
        holder.tv_delivery_date.setText(arrayList.get(position).getTv_delivery_date());
        holder.tv_delivery_pickup.setText(arrayList.get(position).getTv_delivery_pickup());
        holder.tv_delivery_destination.setText(arrayList.get(position).getTv_delivery_destination());


        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curNo = holder.tv_delivery_no.getText().toString();
                Toast.makeText(v.getContext(), curNo, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "tag num11 = " + v.getTag());


            }

        });



        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                remove(holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position){
        try{
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){

            ex.printStackTrace();
        }


    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView tv_delivery_no;
        protected TextView tv_delivery_category;
        protected TextView tv_delivery_date;
        protected TextView tv_delivery_pickup;
        protected TextView tv_delivery_destination;
        public CustomViewHolder( View itemView) {
            super(itemView);
            this.tv_delivery_no = (TextView) itemView.findViewById(R.id.tv_delivery_no);
            this.tv_delivery_category = (TextView) itemView.findViewById(R.id.tv_delivery_category);
            this.tv_delivery_date = (TextView) itemView.findViewById(R.id.tv_delivery_date);
            this.tv_delivery_pickup = (TextView) itemView.findViewById(R.id.tv_delivery_pickup);
            this.tv_delivery_destination = (TextView) itemView.findViewById(R.id.tv_delivery_destination);
        }
    }
}
