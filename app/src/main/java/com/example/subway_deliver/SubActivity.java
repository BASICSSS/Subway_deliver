package com.example.subway_deliver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SubActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView; //하단바
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1Sub frag1Sub;
    private Frag2Sub frag2Sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        bottomNavigationView = findViewById(R.id.customer_bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_submit:
                        setFrag(0);
                        break;
                    case R.id.action_deliver_state:
                        setFrag(1);
                        break;
                }

                return true;
            }
        });
        frag1Sub = new Frag1Sub(); //객체생성
        frag2Sub = new Frag2Sub(); //객체생성
        setFrag(0); // 첫프레그화면을 지정



    }





    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.sub_frame_layout, frag1Sub);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.sub_frame_layout, frag2Sub);
                ft.commit();
                break;
        }

    }
}