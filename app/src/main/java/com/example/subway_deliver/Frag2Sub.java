package com.example.subway_deliver;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Frag2Sub extends Fragment {

    private String userId;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<SubReqData> arrayList;
    private String user, userPhone, obj, pickupAdd, receiver, receiverPhone, receiverAdd, objType,reqDate;
    private String jsonData;
    private SubAdapter subAdapter;
    private static String IP_ADDRESS = "river97.cafe24.com";
    private static String TAG = "subway_deliver";
    public static Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view =  inflater.inflate(R.layout.frag2_sub, container, false);


    recyclerView = (RecyclerView) view.findViewById(R.id.rvw);
    linearLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setHasFixedSize(true);

    arrayList = new ArrayList<>();

    subAdapter =  new SubAdapter(arrayList); //어댑터로부터 받은 것을 어레이리스트에 저장함
    recyclerView.setAdapter(subAdapter); //그 어댑터를 리사이클러뷰에 셋팅
    mContext = getContext();


//    if(getArguments() != null){ //나중에는 어차피 로그인시에 계속 id가 접속되있는 상태로 갈것기기 때문에 이거 없이 그냥 받기만 하면됨
//        userId = getArguments().getString("id");
//
//    }

    userId = "testId"; //나중에 가입만들어서 회원정보를 받아서 사용하자.

    if(userId == null){

        Log.d(TAG, "id 테스트 존재 = " + userId);

        Intent intent = new Intent(getActivity().getApplicationContext(), PopupActivity.class);
        intent.putExtra("type", PopupType.NORMAL);
        intent.putExtra("gravity", PopupGravity.CENTER);
        intent.putExtra("title", "알림");
        intent.putExtra("content","로그인을 먼저 진행하여 주십시오.");
        intent.putExtra("buttonCenter", "확인");
        startActivityForResult(intent, 1);

    }
    else{

        SelectUserData task = new SelectUserData();
        Log.d(TAG, "id 확인 = " + userId);

        task.execute("https://"  + IP_ADDRESS + "/loadDB_to_json.php", userId);

    }

        return view;
    }



    public class SelectUserData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute(){

            super.onPreExecute();;
            progressDialog = ProgressDialog.show(getActivity(), "배송 기록을 불러오는 중입니다.", null, true,true);
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(TAG, "POST response = " + result);


            if (result != null){

                jsonData = result;
                dataReceiveAnPrint();
            }


        }


        @Override
        protected String doInBackground(String... params) {
            String id = (String)params[1];
            String serverURL = (String)params[0];

            String postParameters = "id=" + id;

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

                Log.d(TAG, "getData Error = " + e);

                return null;

            }
        }
    }

    private void dataReceiveAnPrint(){

        String TAG_JSON="reqTmpData";
        //        String TAG_ID = "id";
        String TAG_NAME = "userName";
        String TAG_PHONE ="userPhone";
        String TAG_ADD ="pickupAdd";
        String TAG_OBJ ="deliveryObj";
        String TAG_TYPE ="ObjType";
        String TAG_RECEIVER ="receiverName";
        String TAG_RECEIVER_PHONE ="receiverPhone";
        String TAG_RECEIVE_ADD="deliveryAdd";
        String TAG_REQDATE="reqDate";


        try{


            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject item = jsonArray.getJSONObject(i); //각 루프마다 한줄 즉 row데이터 드렁있음

                String delivery_numTag = "No." + (i+1); //나중에 번호형식 만들어 DB저장해서 가져오자

                String userName = item.getString(TAG_NAME);
                String userPhone = item.getString(TAG_PHONE);
                String pickupAdd = item.getString(TAG_ADD);
                String deliveryObj = item.getString(TAG_OBJ);
                String ObjType = item.getString(TAG_TYPE);
                String receiverName = item.getString(TAG_RECEIVER);
                String receiverPhone = item.getString(TAG_RECEIVER_PHONE);
                String deliveryAdd = item.getString(TAG_RECEIVE_ADD);
                String reqDate = item.getString(TAG_REQDATE);

                SubReqData subReqData = new SubReqData();

                subReqData.setDelivery_numTag(delivery_numTag);

                subReqData.setTvS_deliveryObj(deliveryObj);
                subReqData.setTvS_requestDate(reqDate);
                subReqData.setTvS_receiver(receiverName);
                subReqData.setTvS_deliveryAdd(deliveryAdd);
                subReqData.setTvD_user(userName);
                subReqData.setTvD_userPhone(userPhone);
                subReqData.setTvD_pickupAdd(pickupAdd);
                subReqData.setTvD_deliveryObj(deliveryObj);
                subReqData.setTvD_objEstimate(ObjType);
                subReqData.setTvD_receiver(receiverName);
                subReqData.setTvD_receiverPhone(receiverPhone);
                subReqData.setTvD_deliveryAdd(deliveryAdd);
                subReqData.setTvD_requestDate(reqDate);

                arrayList.add(subReqData);
                subAdapter.notifyDataSetChanged();



                //    SubReqData subReqData = new SubReqData();



            }
        }catch (Exception e){

            Log.d(TAG, "showResult : ", e);
        }


    }


}
