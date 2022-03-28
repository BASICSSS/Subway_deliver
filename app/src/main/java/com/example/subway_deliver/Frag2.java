package com.example.subway_deliver;

import android.app.FragmentBreadCrumbs;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Frag2 extends Fragment {

    private View view;
    private String deliveryMan_Id;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private String jsonData;


    private static String IP_ADDRESS = "river97.cafe24.com";
    private static String TAG = "subway_deliver";

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag2, container, false); //프레그 xml 연결


        recyclerView = (RecyclerView) view.findViewById(R.id.rvw);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        arrayList = new ArrayList<>();

        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);


        deliveryMan_Id = "홍길동"; //나중에 가입만들어서 회원정보를 받아서 사용하자

        SelectDeliveryData task = new SelectDeliveryData();
        Log.d(TAG, "배달자 확인 = " + deliveryMan_Id);

        task.execute("https://"  + IP_ADDRESS + "/Delivery_loadDB_to_json.php", deliveryMan_Id);




        return view;

    }

    public class SelectDeliveryData extends AsyncTask<String, Void, String> {

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
//                dataReceiveAnPrint();
//                FragmentTransaction ft = getFragmentManager()
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

//    private void dataReceiveAnPrint(){
//
//        String TAG_JSON="reqTmpData";
//
//        String TAG_NAME = "userName";
//        String TAG_PHONE ="userPhone";
//        String TAG_ADD ="pickupAdd";
//        String TAG_OBJ ="deliveryObj";
//        String TAG_TYPE ="ObjType";
//        String TAG_RECEIVER ="receiverName";
//        String TAG_RECEIVER_PHONE ="receiverPhone";
//        String TAG_RECEIVE_ADD="deliveryAdd";
//        String TAG_REQDATE="reqDate";
//        String TAG_ACCEPTDATE="acceptDate";
//
//
//        try{
//
//
//            JSONObject jsonObject = new JSONObject(jsonData);
//            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
//
//            for(int i=0; i<jsonArray.length(); i++)
//            {
//                JSONObject item = jsonArray.getJSONObject(i); //각 루프마다 한줄 즉 row데이터 드렁있음
//
//                String delivery_numTag = "No." + (i+1); //나중에 번호형식 만들어 DB저장해서 가져오자
//
//                String userName = item.getString(TAG_NAME);
//                String userPhone = item.getString(TAG_PHONE);
//                String pickupAdd = item.getString(TAG_ADD);
//                String deliveryObj = item.getString(TAG_OBJ);
//                String ObjType = item.getString(TAG_TYPE);
//                String receiverName = item.getString(TAG_RECEIVER);
//                String receiverPhone = item.getString(TAG_RECEIVER_PHONE);
//                String deliveryAdd = item.getString(TAG_RECEIVE_ADD);
//                String reqDate = item.getString(TAG_REQDATE);
//
//                MainData mainData = new MainData();
//
//                mainData.setNumTag(delivery_numTag);
//
//                mainData.setDeliverObj(deliveryObj);
//                mainData.setRequestDate(reqDate);
//                mainData.setTvS_receiver(receiverName); // 생각해보니까 그냥 sub에 구혓해놨던 거 그대로(SubReqData) 쓰면 안되나? 함 해보자
//                mainData.setTvS_deliveryAdd(deliveryAdd);
//                mainData.setTvD_user(userName);
//                mainData.setTvD_userPhone(userPhone);
//                mainData.setTvD_pickupAdd(pickupAdd);
//                mainData.setTvD_deliveryObj(deliveryObj);
//                mainData.setTvD_objEstimate(ObjType);
//                mainData.setTvD_receiver(receiverName);
//                mainData.setTvD_receiverPhone(receiverPhone);
//                mainData.setTvD_deliveryAdd(deliveryAdd);
//                mainData.setTvD_requestDate(reqDate);
//
//                arrayList.add(mainData);
//                mainAdapter.notifyDataSetChanged();
//
//
//
//                //    SubReqData subReqData = new SubReqData();
//
//
//
//            }
//        }catch (Exception e){
//
//            Log.d(TAG, "showResult : ", e);
//        }
//
//
//    }

}
