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
    private FragIng fragIng;
///////////////////// 요기서부터는 서버데이터 가져오는것
    private TextView tv_outPut;

//////////////////////역여기서 부터는 db넣기 테스트



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
                    case R.id.action_delivery_ing:
                        setFrag(1);
                        break;
                    case R.id.action_mylist:
                        setFrag(2);
                        break;
                }

                return true;
            }
        });
        frag1 = new Frag1(); //객체생성
        frag2 = new Frag2(); //객체생성
        fragIng = new FragIng();
        setFrag(0); // 첫프레그화면을

        ///////////////////////////////////////////////////////////////////////////


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
                ft.replace(R.id.main_frame_layout, fragIng);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.main_frame_layout, frag2);
                ft.commit();
                break;
        }

    }
}