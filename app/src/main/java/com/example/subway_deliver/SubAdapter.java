package com.example.subway_deliver;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.CustomViewHolder>{

    ArrayList<SubReqData> arrayList;

    //어댑터가 어레이리스트를 인자로 받아서 넣을 수 있도록함
    public SubAdapter(ArrayList<SubReqData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
//    @org.jetbrains.annotations.NotNull
    @Override
    public SubAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_tmp_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubAdapter.CustomViewHolder holder, int position) {

        holder.delivery_numTag.setText(arrayList.get(position).getDelivery_numTag());

        holder.tvS_deliveryObj.setText(arrayList.get(position).getTvS_deliveryObj());
        holder.tvS_deliveryObj.setText(arrayList.get(position).getTvS_deliveryObj());
        holder.tvS_requestDate.setText(arrayList.get(position).getTvS_requestDate());
        holder.tvS_deliveryAdd.setText(arrayList.get(position).getTvS_deliveryAdd());
        holder.tvS_receiver.setText(arrayList.get(position).getTvS_receiver());

        holder.tvD_user.setText(arrayList.get(position).getTvD_user());
        holder.tvD_userPhone.setText(arrayList.get(position).getTvD_userPhone());
        holder.tvD_pickupAdd.setText(arrayList.get(position).getTvD_pickupAdd());
        holder.tvD_deliveryObj.setText(arrayList.get(position).getTvD_deliveryObj());
        holder.tvD_objEstimate.setText(arrayList.get(position).getTvD_objEstimate());
        holder.tvD_receiver.setText(arrayList.get(position).getTvD_receiver());
        holder.tvD_receiverPhone.setText(arrayList.get(position).getTvD_receiverPhone());
        holder.tvD_deliveryAdd.setText(arrayList.get(position).getTvD_deliveryAdd());
        holder.tvD_requestDate.setText(arrayList.get(position).getTvD_requestDate());
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView delivery_numTag;

        protected TextView tvS_deliveryObj;
        protected TextView tvS_requestDate;
        protected TextView tvS_deliveryAdd;
        protected TextView tvS_receiver;

        protected TextView tvD_user;
        protected TextView tvD_userPhone;
        protected TextView tvD_pickupAdd;
        protected TextView tvD_deliveryObj;
        protected TextView tvD_objEstimate;
        protected TextView tvD_receiver;
        protected TextView tvD_receiverPhone;
        protected TextView tvD_deliveryAdd;
        protected TextView tvD_requestDate;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            //열결해주는 작업
            this.delivery_numTag = (TextView)itemView.findViewById(R.id.delivery_numTag);

            this.tvS_deliveryObj = (TextView)itemView.findViewById(R.id.tvS_deliveryObj);
            this.tvS_requestDate = (TextView)itemView.findViewById(R.id.tvS_requestDate);
            this.tvS_deliveryAdd = (TextView)itemView.findViewById(R.id.tvS_deliveryAdd);
            this.tvS_receiver = (TextView)itemView.findViewById(R.id.tvS_receiver);

            this.tvD_user = (TextView)itemView.findViewById(R.id.tvD_user);
            this.tvD_userPhone = (TextView)itemView.findViewById(R.id.tvD_userPhone);
            this.tvD_pickupAdd = (TextView)itemView.findViewById(R.id.tvD_pickupAdd);
            this.tvD_deliveryObj = (TextView)itemView.findViewById(R.id.tvD_deliveryObj);
            this.tvD_objEstimate = (TextView)itemView.findViewById(R.id.tvD_objEstimate);
            this.tvD_receiver = (TextView)itemView.findViewById(R.id.tvD_receiver);
            this.tvD_receiverPhone = (TextView)itemView.findViewById(R.id.tvD_receiverPhone);
            this.tvD_deliveryAdd = (TextView)itemView.findViewById(R.id.tvD_deliveryAdd);
            this.tvD_requestDate = (TextView)itemView.findViewById(R.id.tvD_requestDate);

        }
    }
}
