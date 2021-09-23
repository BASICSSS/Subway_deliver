package com.example.subway_deliver;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> implements OnIntentReceived{

    private ArrayList<MainData> arrayList;
    private static String TAG = "subway_deliver";
    private static String IP_ADDRESS = "river97.cafe24.com";
    private static final int POPUP_TYPE = 5;
    public Context mContext;
    private TextView orderNum;




    public MainAdapter(ArrayList<MainData> arrayList) {
        this.arrayList = arrayList;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliver_list, parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        orderNum = (TextView)view.findViewById(R.id.orderNum);


        return holder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.CustomViewHolder holder, int position) {


        holder.onBind(arrayList.get(position), position);



    }

//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
////        this.onActivityResult(requestCode, resultCode, intent);
//        Log.i("test", "onActivityResult" + requestCode);
//        Log.i("아우", "ㅅㄷㄴㅅ" + requestCode);
//
//        switch (requestCode) {
//
//            case POPUP_TYPE:
//                if(resultCode == RESULT_OK){
//                    PopupResult result = (PopupResult) intent.getSerializableExtra("result");
//
//                    if(result == PopupResult.LEFT){
//                        // 작성 코드
////                        Toast.makeText(getActivity(), "LEFT", Toast.LENGTH_SHORT).show();
//
//                        Log.i("approve", "해당 배송요청을 승인하였습니다.");
//
//
//                    } else if(result == PopupResult.RIGHT){
//                        // 작성 코드
////                        Toast.makeText(getActivity(), "RIGHT", Toast.LENGTH_SHORT).show();
//
//                        Log.i("approve", "해당 배송요청 승인을 취소하였습니다.");
//
//
//                    }
//                }
//                break;
//
//
//        }
//    }




    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }


    @Override public void onIntent(Intent i, int resultCode) {



    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private MainData data;


        protected TextView numTag;

        protected TextView orderNum;
        protected TextView pickupAdd;
        protected TextView deliverAdd;
        protected TextView deliverObj;
        protected TextView deliverEst;
        protected TextView requestDate;

        public CustomViewHolder( View itemView) {
            super(itemView);
            this.numTag = (TextView)itemView.findViewById(R.id.numTag);
            this.orderNum = (TextView)itemView.findViewById(R.id.orderNum);
            this.pickupAdd = (TextView)itemView.findViewById(R.id.pickupAdd);
            this.deliverAdd = (TextView)itemView.findViewById(R.id.deliverAdd);
            this.deliverObj = (TextView)itemView.findViewById(R.id.deliverObj);
            this.deliverEst = (TextView)itemView.findViewById(R.id.deliverEst);
            this.requestDate = (TextView)itemView.findViewById(R.id.requestDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });


        }

        public void onBind(MainData mainData, int position) {
            this.data = mainData;

            numTag.setText(arrayList.get(position).getNumTag());
            orderNum.setText(arrayList.get(position).getIdx()); // 주문번호
            pickupAdd.setText(arrayList.get(position).getPickupAdd());
            deliverAdd.setText(arrayList.get(position).getDeliverAdd());
            deliverObj.setText(arrayList.get(position).getDeliverObj());
            deliverEst.setText(arrayList.get(position).getDeliverEst());
            requestDate.setText(arrayList.get(position).getRequestDate());


        }
    }


}
