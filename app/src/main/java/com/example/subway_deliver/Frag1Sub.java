package com.example.subway_deliver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class Frag1Sub extends Fragment {

    private View view;
    private TextView tmp;
    private Spinner category_spinner;
    private EditText address;
    private EditText address2;
    private Button request;
    private EditText user;
    private EditText obj;
    private EditText receiver;
    private EditText phone;
    private LinearLayout category;
    private static final  int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final  int SEARCH_ADDRESS_ACTIVITY2 = 10001;

    //    private final TextWatcher textWatcher = new TextWatcher() {                       // 한번 호출로 처리
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            // 입력하기 전에 조치
//            Log.i("test", "seq1:" + s +" " + start +" " + count + " " +after );
//
//
//        }
//
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            // 입력난에 변화가 있을 시 조치
//            Log.i("test", "seq2:" + s + " " +start + " " +before + " " +count );
//
//        }
//
//        public void afterTextChanged(Editable s) {
//
//            Log.i("test", "seq3:" + s.length() + this.));
//
//            // 입력이 끝났을 때 조치
//        }
//    };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.frag1_sub, container, false);
        category = (LinearLayout) view.findViewById(R.id.category_spinner_layout);
        address2 = (EditText) view.findViewById(R.id.text_address2);
        address = (EditText) view.findViewById(R.id.text_address);
        user = (EditText) view.findViewById(R.id.user_box);
        obj = (EditText) view.findViewById(R.id.delivery_obj_box);
        receiver = (EditText) view.findViewById(R.id.receive_person_box);
        phone = (EditText) view.findViewById(R.id.receive_phone_num_box);
        request = (Button) view.findViewById(R.id.request_button);
        request.setEnabled(true);
        request.setVisibility(View.GONE);
        category.setVisibility(View.GONE);
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonActive();

            }
        });

        address2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonActive();

            }
        });
        user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonActive();

            }
        });
        obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonActive();

            }
        });
        receiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonActive();

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonActive();

            }
        });


        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("주소설정페이지", "주소입력창 클릭");
    //            Context ct;
                int status = NetworkStatus.getConnectivityStatus(getActivity());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {


                    Intent intent = new Intent(getActivity().getApplicationContext(), AddressWebviewActivity.class);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);

                }else {
                    Toast.makeText(getActivity(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        address2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("주소설정페이지", "주소입력창 클릭");
    //            Context ct;
                int status = NetworkStatus.getConnectivityStatus(getActivity());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {


                    Intent intent = new Intent(getActivity().getApplicationContext(), AddressWebviewActivity.class);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY2);

                }else {
                    Toast.makeText(getActivity(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        category_spinner = (Spinner) view.findViewById(R.id.category_spinner);
        tmp = (TextView) view.findViewById(R.id.category_tmp);

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tmp.setText(parent.getItemAtPosition(position).toString() + "측정기준 삽입");
                if(position != 0){
                    category.setVisibility(View.VISIBLE);

                }
                else{
                    category.setVisibility(View.GONE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category.setVisibility(View.GONE);





            }
        });



        return view;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("test", "onActivityResult");

        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.i("test", "data:" + data);
                        address.setText(data);
                    }
                }
                break;

            case SEARCH_ADDRESS_ACTIVITY2:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.i("test", "data:" + data);
                        address2.setText(data);
                    }
                }
                break;


        }
    }

    private void setButtonActive(){
        //요청사 정보
        if((TextUtils.isEmpty(user.getText().toString()) || TextUtils.isEmpty(address.getText().toString()) || TextUtils.isEmpty(address2.getText().toString()) || TextUtils.isEmpty(obj.getText().toString()) &&TextUtils.isEmpty(receiver.getText().toString()) || TextUtils.isEmpty(phone.getText().toString())) == false ){

            request.setVisibility(View.VISIBLE);

        }
        else{

            request.setVisibility(View.GONE);

        }

    }
}
