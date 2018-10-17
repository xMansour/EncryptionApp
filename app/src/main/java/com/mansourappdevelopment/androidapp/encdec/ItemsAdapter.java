package com.mansourappdevelopment.androidapp.encdec;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mansour on 10/17/2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private Context mContext;
    private ArrayList<Item> mItems;

    public ItemsAdapter(Context context, ArrayList<Item> Items) {
        mContext = context;
        mItems = Items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        return new ItemsViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Item currentItem = mItems.get(position);
        String title = currentItem.getAlgorithmName();
        String description = currentItem.getDescritption();

        holder.mTextViewTitle.setText(title);
        holder.mTextViewDescription.setText(description);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewTitle;
        public TextView mTextViewDescription;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.editTextTitle);
            mTextViewDescription = itemView.findViewById(R.id.editTextDescription);
        }
    }

}
