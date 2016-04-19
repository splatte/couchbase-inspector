package com.github.splatte.couchbaseinspector;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.couchbase.lite.QueryRow;

import java.util.ArrayList;
import java.util.List;

public class CouchbaseAdapter extends RecyclerView.Adapter<CouchbaseAdapter.ViewHolder> {
    private List<QueryRow> mData;

    public CouchbaseAdapter() {
        mData = new ArrayList<>();
    }

    public void add(QueryRow item) {
        this.mData.add(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QueryRow item = mData.get(position);
        holder.txtTitle.setText(item.getDocumentId());
        holder.txtContent.setText(item.getDocument().getUserProperties().toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtContent;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            txtContent = (TextView) v.findViewById(R.id.txtContent);
        }
    }

}
