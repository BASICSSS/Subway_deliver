package com.example.subway_deliver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Frag1 extends Fragment {

    private View view;
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;



    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag1, container, false); //프레그 xml 연결

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), new LinearLayoutManager(this).getOrientation());

        recyclerView = (RecyclerView) view.findViewById(R.id.rvw);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),1));
        recyclerView.addItemDecoration(new RecyclerDecoration(30));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        Button btn_add = (Button)view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainData mainData = new MainData("No. 00001", "물품 : 잡화","Date : 2021.06.02","수령장소 : 인천광역시 남동구 이토타워3층 A호","배송장소 : 인천광역시 연수구 한양아파트 602동");
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();


            }
        });


        return view;

    }
}
