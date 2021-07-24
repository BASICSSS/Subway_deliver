package com.example.subway_deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView; //하단바
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
///////////////////// 요기서부터는 서버데이터 가져오는것
    private TextView tv_outPut;

//////////////////////역여기서 부터는 db넣기 테스트
    private static String IP_ADDRESS = "river97.cafe24.com";
    private static String TAG = "subway_deliver";
    private EditText mEditTextName;
    private EditText mEditTextCountry;
    private TextView mTextViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_list:
                        setFrag(0);
                        break;
                    case R.id.action_mylist:
                        setFrag(1);
                        break;
                }

                return true;
            }
        });
        frag1 = new Frag1(); //객체생성
        frag2 = new Frag2(); //객체생성
        setFrag(0); // 첫프레그화면을

        ///////////////////////////////////////////////////////////////////////////
        mEditTextName = (EditText)findViewById(R.id.editText_main_name);
        mEditTextCountry = (EditText)findViewById(R.id.editText_main_country);
        mTextViewResult = (TextView) findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());

        Button buttonIsert = (Button)findViewById(R.id.button_main_insert);
        buttonIsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mEditTextName.getText().toString();
                String country = mEditTextCountry.getText().toString();

                InsertData task = new InsertData();
                task.execute("https://"  + IP_ADDRESS + "/request_tmp_insert.php", name, country);


                mEditTextName.setText("");
                mEditTextCountry.setText("");
            }
        });




        ///////////////////////////////////////////////////////////////////////////
        tv_outPut = (TextView) findViewById(R.id.tv_outPut);
        String url = "https://river97.cafe24.com/http_test.html";

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();



    }


    public class InsertData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        @Override
        protected void  onPreExecute(){
            super.onPreExecute();

            progressDialog = ProgressDialog.show(MainActivity.this, "please Wait", null, true, true);
        }
        @Override
        protected void  onPostExecute(String result){
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response = " + result);

        }



        @Override
        protected String doInBackground(String... params) {
            String name = (String)params[1];
            String country = (String)params[2];

            String serverURL = (String)params[0];

            String postParameters = "name=" + name + "&country=" + country;

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
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader  = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
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


    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            tv_outPut.setText(s);
        }
    }




    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame_layout, frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.main_frame_layout, frag2);
                ft.commit();
                break;
        }

    }
}