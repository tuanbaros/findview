package com.simple.findview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ViewById;
import com.simple.findviewbyid.FindView;
import java.util.List;

/**
 * Created by FRAMGIA\nguyen.thanh.tuan on 06/07/2017.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

    private List<String> mList;

    public SimpleAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @ViewById(R.id.text_number)
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            FindView.bind(this, itemView);
        }

        void bindData(String s) {
            mTextView.setText(s);
        }
    }
}
