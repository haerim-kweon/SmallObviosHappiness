package com.example.smallobvioshappiness;
import android.view.View;


public interface OnPostListItemClickListener {
    public void onItemClick(Post_List_Adapter.ItemViewHolder holder, View view, int position);
}
