package com.example.carownerinfoapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class listFrag extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter myadapter;
    RecyclerView.LayoutManager layoutManager;
    View view;

    public listFrag() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView=view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        myadapter=new carAdapter(this.getActivity(),carApplication.cars);
        recyclerView.setAdapter(myadapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_list, container, false);

        return view;
    }
}