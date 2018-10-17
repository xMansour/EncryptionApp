package com.mansourappdevelopment.androidapp.encdec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by Mansour on 10/16/2018.
 */

public class InformationFragment extends Fragment {
    private View mView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Item> mItems;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_information, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mItems = new ArrayList<>();
        mItems.add(new Item("First", "bla bla bla 1"));
        mItems.add(new Item("Second", "bla bla bla 2"));
        mItems.add(new Item("Third", "bla bla bla 3"));
        mItems.add(new Item("Fourth", "bla bla bla 4"));

        mAdapter = new ItemsAdapter(getActivity(), mItems);
        mRecyclerView.setAdapter(mAdapter);

    }
}
