package com.example.subway_deliver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class FragIng extends Fragment {

    private View view;
    private String idx;
    private String jsonData;
    private static String IP_ADDRESS = "river97.cafe24.com";
    private static String TAG = "subway_deliver";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_ing, container, false);

        // 이제 여기서 번들로 idx 받고 셋팅해주자
        idx = getArguments().getString("curr_idx");

        if(idx == null){

            Intent intent = new Intent(getActivity().getApplicationContext(), PopupActivity.class);
            intent.putExtra("type", PopupType.NORMAL);
            intent.putExtra("gravity", PopupGravity.CENTER);
            intent.putExtra("title", "알림");
            intent.putExtra("content","존재하지 않는 요청건입니다.");
            intent.putExtra("buttonCenter", "확인");
            startActivityForResult(intent, 1); //


        }else {

            SelectCurData task = new SelectCurData();

            task.execute("https://"  + IP_ADDRESS + "/CurrData_loadDB_to_json.php", idx);



        }

        return view;

    }


    public class SelectCurData extends AsyncTask<String, Void, String> {

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
            String idx = (String)params[1];
            String serverURL = (String)params[0];

            String postParameters = "idx=" + idx;

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
        //        String TAG_IDX = "idx";
        String TAG_NAME = "userName";
        String TAG_PHONE ="userPhone";
        String TAG_ADD ="pickupAdd";
        String TAG_OBJ ="deliveryObj";
        String TAG_TYPE ="ObjType";
        String TAG_RECEIVER ="receiverName";
        String TAG_RECEIVER_PHONE ="receiverPhone";
        String TAG_RECEIVE_ADD="deliveryAdd";
        String TAG_REQDATE="reqDate";
        String TAG_ACCEPTDATE="acceptDate";


        try{


            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            JSONObject item = jsonArray.getJSONObject(0); //각 루프마다 한줄 즉 row데이터 드렁있음

            String orderNum = "No. " + idx; //나중에 번호형식 만들어 DB저장해서 가져오자

            String userName = item.getString(TAG_NAME);
            String userPhone = item.getString(TAG_PHONE);
            String pickupAdd = item.getString(TAG_ADD);
            String deliveryObj = item.getString(TAG_OBJ);
            String ObjType = item.getString(TAG_TYPE);
            String receiverName = item.getString(TAG_RECEIVER);
            String receiverPhone = item.getString(TAG_RECEIVER_PHONE);
            String deliveryAdd = item.getString(TAG_RECEIVE_ADD);
            String reqDate = item.getString(TAG_REQDATE);
            String acceptDate = item.getString(TAG_ACCEPTDATE);

            SubReqData subReqData = new SubReqData();

            subReqData.setDelivery_numTag(orderNum);

            subReqData.setTvS_deliveryObj(deliveryObj); //서브에 다 되있으니 먼저 서브로 셋팅해보자
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
//
//            arrayList.add(subReqData);
//            subAdapter.notifyDataSetChanged();



                //    SubReqData subReqData = new SubReqData();

        }catch (Exception e){

            Log.d(TAG, "showResult : ", e);
        }


    }



}
