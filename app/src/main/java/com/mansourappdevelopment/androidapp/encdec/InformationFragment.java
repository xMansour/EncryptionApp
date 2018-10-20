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
        mItems.add(new Item("Caesar Cipher", "In caesar cipher, we shift every character of the plain text" +
                " by a value equal to the key, it is changed to a character after it by the value of the key in the english alphabet" +
                ". When decrypting, we invert the rule so we get the character before it by the value of the key."));
        mItems.add(new Item("PlayFair Cipher", "In playfair cipher, the key is used to generate the 5x5 alphabet matrix" +
                " where the first characters of it are the key's characters. The plain text is divided into groups of 2 characters" +
                " and the characters are encrypted according to three rules. If the two characters are in the same row, they are" +
                " shifted to the right. If the two characters are in the same coulomn, they are shifted down. If they are" +
                " in different positions, the horizontal intersection in found for each character. When decrypting the first two rules" +
                " are inverted."));

        mAdapter = new ItemsAdapter(getActivity(), mItems);
        mRecyclerView.setAdapter(mAdapter);

    }
}
