package com.example.smallobvioshappiness;
import android.view.View;


public interface OnPostItemClickListener {
    public void onItemClick(PostAdapter.ItemViewHolder holder, View view, int position);
}
