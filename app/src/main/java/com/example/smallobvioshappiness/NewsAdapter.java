package com.example.smallobvioshappiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemViewHolder> {
    private ArrayList<Chat> notice;

    public NewsAdapter(ArrayList<Chat> notice) {
        this.notice = notice;
    }


    @Override
    public int getItemCount() {
        return notice.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notice, viewGroup,false);
        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Chat notices = notice.get(i);

        itemViewHolder.contents.setText(notices.getName());
        itemViewHolder.title.setText(notices.getRecent_chat());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView contents;
        private TextView title;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            contents = itemView.findViewById(R.id.notice_contents);
            title = itemView.findViewById(R.id.notice_title);

        }
    }


    void addItem(Chat notice){
        notice.add(notice);
    }

    void removeItem(int position){
        notice.remove(position);
    }


}
