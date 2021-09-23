package com.example.subway_deliver;

import android.app.Activity;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
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

import static android.app.Activity.RESULT_OK;

public class Frag1 extends Fragment {

    private String userId;
    private TextView orderNum;
    private String jsonData;
    private View view;
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private static String IP_ADDRESS = "river97.cafe24.com";
    private static String TAG = "subway_deliver";



    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), new LinearLayoutManager(this).getOrientation());


//        데코
//        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),1));
//        recyclerView.addItemDecoration(new RecyclerDecoration(30));

        view = inflater.inflate(R.layout.frag1, container, false); //프레그 xml 연결

        recyclerView = (RecyclerView) view.findViewById(R.id.rvw);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true); //리사이클러뷰에 크기변화가 없을때, 변화하지 못하게 해 비용을 줄임 (리사이클러뷰에 목적성과 부합)

        arrayList = new ArrayList<>();

        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);
        orderNum = (TextView) view.findViewById(R.id.orderNum);

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

            SelectDeliverDataList task = new SelectDeliverDataList();
            Log.d(TAG, "id 확인 = " + userId);

            task.execute("https://"  + IP_ADDRESS + "/All_loadDB_to_json.php", userId);

        }


        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d(TAG, "클릭된아아아아아아아");


                TextView orderNum = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.orderNum);
                String idx = orderNum.getText().toString(); //요렇게 하나하나 연결하자.

                PreChecker task = new PreChecker();
                task.execute("https://"  + IP_ADDRESS + "/exist_checker.php", idx);


            }
        });
        return view;

    }


    public class SelectDeliverDataList extends AsyncTask<String, Void, String> {

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

            String postParameters = "id=" + id; //일단 아이디정보는 가지고 있어 보자

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


    public class PreChecker extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute(){

            super.onPreExecute();;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d(TAG, "POST response = " + result);
            Intent intent = new Intent( getContext() , PopupActivity.class);

            if(!result.equals("0")){

                intent.putExtra("order", result);//현재 가장 최근 만들어진 사이클뷰의 값으로 설정되고있음
                intent.putExtra("type", PopupType.EMPTY_SELECT);
                intent.putExtra("gravity", PopupGravity.LEFT);
                intent.putExtra("title", "승인확인");
                intent.putExtra("content", "해당 요청정보를 나의 배송건으로 승인하시겠습니까?");
                intent.putExtra("buttonLeft", "예");
                intent.putExtra("buttonRight", "아니오");
                startActivityForResult(intent, 5);


            }
            else{
                intent.putExtra("type", PopupType.NORMAL);
                intent.putExtra("gravity", PopupGravity.CENTER);
                intent.putExtra("title", "안내사항");
                intent.putExtra("content", "이미 승인된 요청입니다. 다시 확인하여 주십시오.");
                intent.putExtra("buttonCenter", "확인");
                startActivityForResult(intent, 1);

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

                Log.d(TAG, "InsertData Error = " + e);

                return new String("Error :" + e.getMessage());

            }
        }
    }

    public class AcceptUpdate extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute(){

            super.onPreExecute();;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            Log.d(TAG, "POST response = " + result);

            //요기도 팝업처리해야겟다.

        }


        @Override
        protected String doInBackground(String... params) {
            String idx = (String)params[1];
            String deliveryMan = (String)params[2];
            String serverURL = (String)params[0];

            String postParameters = "idx=" + idx + "&deliveryMan=" + deliveryMan;

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

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {

            case 1:
                if(resultCode == RESULT_OK){

//                    mainAdapter.notifyDataSetChanged(); 이건 안됨
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.detach(this).attach(this).commit();
                }

                break;

            case 5:
                if(resultCode == RESULT_OK){
                    PopupResult result = (PopupResult) intent.getSerializableExtra("result");
                    String order = intent.getStringExtra("idx");

                    if(result == PopupResult.LEFT){
                        // 작성 코드
                        Toast.makeText(getActivity(), "LEFT", Toast.LENGTH_SHORT).show();

                        String deliveryMan = "홍길동"; //이것도 id처럼 받아서 하자. 배송자 id == 이름?

                        AcceptUpdate task = new AcceptUpdate();
                        Log.d(TAG, "확인 = " + order + deliveryMan);

                        task.execute("https://"  + IP_ADDRESS + "/accept_update.php", order, deliveryMan);



                    } else if(result == PopupResult.RIGHT){
                        // 작성 코드
                        Toast.makeText(getActivity(), "RIGHT", Toast.LENGTH_SHORT).show();

                    }
                }
                break;

        }


    }

    private void dataReceiveAnPrint(){

        String TAG_JSON="reqTmpData";
        String TAG_IDX = "idx";
        String TAG_ADD ="pickupAdd";
        String TAG_OBJ ="deliveryObj";
        String TAG_RECEIVE_ADD="deliveryAdd";
        String TAG_REQDATE="reqDate";
        String TAG_TYPE ="ObjType";


        try{


            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject item = jsonArray.getJSONObject(i); //각 루프마다 한줄 즉 row데이터 드렁있음

                String delivery_numTag = "No." + (i+1); //나중에 번호형식 만들어 DB저장해서 가져오자

                String pickupAdd = item.getString(TAG_ADD);
                String deliveryObj = item.getString(TAG_OBJ);
                String deliveryAdd = item.getString(TAG_RECEIVE_ADD);
                String reqDate = item.getString(TAG_REQDATE);
                String estimate = item.getString(TAG_TYPE);
                String idx = item.getString(TAG_IDX);

                MainData mainData = new MainData();

                mainData.setIdx(idx);
                mainData.setNumTag(delivery_numTag);
                mainData.setPickupAdd(pickupAdd);
                mainData.setDeliverObj(deliveryObj);
                mainData.setDeliverAdd(deliveryAdd);
                mainData.setRequestDate(reqDate);
                mainData.setDeliverEst(estimate);


                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();






            }
        }catch (Exception e){

            Log.d(TAG, "showResult : ", e);
        }


    }



}
