package com.example.subway_deliver;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.CustomViewHolder>{

    ArrayList<SubReqData> arrayList;
    private int prePosition = -1;
    private static String TAG = "subway_deliver";
    private SparseBooleanArray selectedItems = new SparseBooleanArray(); //애니메이션


//    private Context context;


    //어댑터가 어레이리스트를 인자로 받아서 넣을 수 있도록함
    public SubAdapter(ArrayList<SubReqData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
//    @org.jetbrains.annotations.NotNull
    @Override
    public SubAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        context = parent.getContext(); //애니메이션
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_tmp_list, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);



        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {


        holder.onBind(arrayList.get(position), position);

//        changeVisibility(selectedItems.get(position));

        holder.itemView.setTag(position);
        Log.d(TAG, "에니매이션테스트11 = " + holder.itemView);
        Log.d(TAG, "에니매이션테스트22 = " + holder.itemView.getTag());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(TAG, "에니매이션테스트 = " + selectedItems.get(position));
                Log.d(TAG, "posison 번호 = " + position);


                if(selectedItems.get(position)){
                    Log.d(TAG, "포지션 테스트1 " + selectedItems.get(position));
                    Log.d(TAG, "포지션 테스트1 " + selectedItems);


                    selectedItems.delete(position);

                }else{
                    Log.d(TAG, "포지션 테스트2 " + selectedItems.get(position));
                    Log.d(TAG, "포지션 테스트2 " + selectedItems);

                    selectedItems.delete(prePosition);
                    selectedItems.put(position, true);
                }

                if(prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                Log.d(TAG, "포지션 테스트3 " + prePosition + position);
                prePosition = position;
                Log.d(TAG, "포지션 테스트4 " + prePosition + position);



            }

        });


    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {

        private SubReqData data;

        protected ConstraintLayout detail_layout;
        protected ConstraintLayout simple_layout;


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

            this.detail_layout = (ConstraintLayout)itemView.findViewById(R.id.detail_layout);
            this.simple_layout = (ConstraintLayout)itemView.findViewById(R.id.simple_layout);


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


            //여기 뷰홀더 온클릭에서는 아이템뷰안에 요소들 클릭이벤트 정의해주자

            this.delivery_numTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(v.getContext(),data.getDelivery_numTag(), Toast.LENGTH_SHORT).show();

                }
            });


        }


        private void changeVisibility(final boolean isExpanded) { //애니메이션이 아주 정교하지 않은 나중에 손보자
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열

            int detailH = 1050;
            int simpleH = 483;


            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, detailH) : ValueAnimator.ofInt(detailH, 0);

            // Animation이 실행되는 시간, n/1000초
            va.setDuration(400);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {

                    // imageView의 높이 변경
                    detail_layout.getLayoutParams().height = (int) animation.getAnimatedValue();
                    detail_layout.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    detail_layout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                    simple_layout.setVisibility(isExpanded ? View.GONE : View.VISIBLE);
                }
            });
            // Animation start
            va.start();
        }


        public void onBind(SubReqData subReqData, int position) {
            this.data = subReqData;

            if(detail_layout.getVisibility() == View.GONE){

                Log.d(TAG, "레이아웃 확인테스트@@ " + simple_layout.getVisibility() + detail_layout.getVisibility());


            }



            delivery_numTag.setText(arrayList.get(position).getDelivery_numTag());

            tvS_deliveryObj.setText(arrayList.get(position).getTvS_deliveryObj());
            tvS_deliveryObj.setText(arrayList.get(position).getTvS_deliveryObj());
            tvS_requestDate.setText(arrayList.get(position).getTvS_requestDate());
            tvS_deliveryAdd.setText(arrayList.get(position).getTvS_deliveryAdd());
            tvS_receiver.setText(arrayList.get(position).getTvS_receiver());

            tvD_user.setText(arrayList.get(position).getTvD_user());
            tvD_userPhone.setText(arrayList.get(position).getTvD_userPhone());
            tvD_pickupAdd.setText(arrayList.get(position).getTvD_pickupAdd());
            tvD_deliveryObj.setText(arrayList.get(position).getTvD_deliveryObj());
            tvD_objEstimate.setText(arrayList.get(position).getTvD_objEstimate());
            tvD_receiver.setText(arrayList.get(position).getTvD_receiver());
            tvD_receiverPhone.setText(arrayList.get(position).getTvD_receiverPhone());
            tvD_deliveryAdd.setText(arrayList.get(position).getTvD_deliveryAdd());
            tvD_requestDate.setText(arrayList.get(position).getTvD_requestDate());

            Log.d(TAG, "바인드 테스트!! " + selectedItems.get(position) + position);


            changeVisibility(selectedItems.get(position));





        }

//        public void onWindowFocusChanged() { //레이아웃 높이 확인용
////        super.onWindowFocusChanged(hasFocus);
//
//            int detail_layHeight = detail_layout.getHeight();
//            int simple_layHeight = simple_layout.getHeight();
//            int wd = simple_layout.getWidth();
//            Log.d(TAG, "레이아웃 확인테스트@@ " + simple_layHeight +" " + detail_layHeight + " " + wd );
//
//
//
//        }



    }


}

