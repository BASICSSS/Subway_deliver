package com.example.subway_deliver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
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
import androidx.fragment.app.FragmentTransaction;

import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.app.Activity.RESULT_OK;

public class Frag1Sub extends Fragment {

    private View view;
    private TextView tmp;
    private TextView user_id;
    private Spinner category_spinner;
    private EditText address;
    private EditText address2;
    private Button request;
    private EditText user;
    private EditText obj;
    private EditText receiver;
    private EditText phone;
    private EditText user_phone;
    private LinearLayout category;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int SEARCH_ADDRESS_ACTIVITY2 = 10001;
    private static final int REQUEST_SEND_CODE = 2000;
    private static final int POPUP_TYPE = 2;

    private static String IP_ADDRESS = "river97.cafe24.com";
    private static String TAG = "subway_deliver";



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
        user_phone = (EditText) view.findViewById(R.id.user_phone_box);
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

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "testID";//일단 고정값 나중에 로그인 정보 받을것
                Log.i("test", "data:" + "이건 클릭확인 테스트");


                Intent intent = new Intent(getActivity().getApplicationContext(), PopupActivity.class);
                intent.putExtra("type", PopupType.SELECT);
                intent.putExtra("gravity", PopupGravity.LEFT);
                intent.putExtra("title", "배송 정보");
                intent.putExtra("user", "발송인 : " + user.getText().toString());
                intent.putExtra("obj", "배송 물품 : " + obj.getText().toString());
                intent.putExtra("pickupAdd", "픽업 주소 : " + address.getText().toString());
                intent.putExtra("receiver", "수취인 : " + receiver.getText().toString());
                intent.putExtra("receiverPhone", "수취인 번호 : " + phone.getText().toString());
                intent.putExtra("receiverAdd", "배송 주소 : " + address2.getText().toString());
                intent.putExtra("objType", "배송 견적 : " + category_spinner.getSelectedItem().toString());
                intent.putExtra("buttonLeft", "예");
                intent.putExtra("buttonRight", "아니요");
                startActivityForResult(intent, 2);




            }
        });



        return view;
    }

    public class InsertData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){

            super.onPreExecute();;
            progressDialog = ProgressDialog.show(getActivity(), "요청중입니다 기다려주십시오", null, true,true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response = " + result);

            String id = "testID";

            Frag2Sub frag2Sub = new Frag2Sub();
            Bundle bundle = new Bundle();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            bundle.putString("id", id);

            frag2Sub.setArguments(bundle);
            fragmentTransaction.replace(R.id.sub_frame_layout, frag2Sub); //itent에서 startactivty라고 봄ㄴ됨
            fragmentTransaction.commit();

            // 할일 다하고 비워줌
            user.setText("");
            obj.setText("");
            user_phone.setText("");
            address.setText("");
            receiver.setText("");
            phone.setText("");
            address2.setText("");
            category_spinner.setSelection(0);


        }


        @Override
        protected String doInBackground(String... params) {
            String id = (String)params[1];
            String userName = (String)params[2];
            String userPhone = (String)params[3];
            String pickupAdd = (String)params[4];
            String deliveryObj = (String)params[5];
            String objType = (String)params[6];
            String receiverName = (String)params[7];
            String receiverPhone = (String)params[8];
            String deliveryAdd = (String)params[9];

            String serverURL = (String)params[0];

            String postParameters = "id=" + id + "&userName=" + userName + "&userPhone=" + userPhone+ "&pickupAdd=" + pickupAdd+ "&deliveryObj=" + deliveryObj+ "&objType=" + objType+ "&receiverName=" + receiverName+ "&receiverPhone=" + receiverPhone+ "&deliveryAdd=" + deliveryAdd;

            try{

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code = " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK){
                    inputStream = httpURLConnection.getInputStream();

                }
                else {
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null ){

                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString();


            }catch (Exception e){

                Log.d(TAG, "InsertData Error = " + e);

                return new String("Error :" + e.getMessage());

            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("test", "onActivityResult" + requestCode);

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

            case POPUP_TYPE:
                if(resultCode == RESULT_OK){
                    PopupResult result = (PopupResult) intent.getSerializableExtra("result");

                    if(result == PopupResult.LEFT){
                        // 작성 코드
                        Toast.makeText(getActivity(), "LEFT", Toast.LENGTH_SHORT).show();

                        RequestComData();

                        Log.i("test", "데이터가 DB에 성공적으로 저장되었음");


                    } else if(result == PopupResult.RIGHT){
                        // 작성 코드
                        Toast.makeText(getActivity(), "RIGHT", Toast.LENGTH_SHORT).show();
                        Log.i("test", "취소");


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

    private void RequestComData(){

        String id = "testID";
        String userName = user.getText().toString();
        String userPhone = user_phone.getText().toString();
        String pickupAdd = address.getText().toString();
        String deliveryObj = obj.getText().toString();
        String objType = category_spinner.getSelectedItem().toString();

        String receiverName = receiver.getText().toString();
        String receiverPhone = phone.getText().toString();
        String deliveryAdd = address2.getText().toString();
        Log.i("test", "data:" + "이건 클릭확인 테스트");

        InsertData task = new InsertData();
        task.execute("https://"  + IP_ADDRESS + "/request_tmp_insert.php", id, userName, userPhone, pickupAdd, deliveryObj, objType, receiverName, receiverPhone, deliveryAdd);


    }
}
