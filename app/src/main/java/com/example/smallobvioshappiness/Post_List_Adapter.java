package com.example.smallobvioshappiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Post_List_Adapter extends RecyclerView.Adapter<Post_List_Adapter.ItemViewHolder> {
    private ArrayList<Post_List> lists;
    private OnPostListItemClickListener listener;

    public Post_List_Adapter(ArrayList<Post_List> lists) {
        this.lists = lists;
    }


    @Override
    public int getItemCount() {
        return lists.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participate_list, viewGroup,false);
        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Post_List list = lists.get(i);

//        itemViewHolder.image.setImageDrawable(chatting.getImage());
        itemViewHolder.title.setText(list.getName());
        itemViewHolder.content.setText(list.getRecent_chat());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
       // private ImageView image;
        private TextView title;
        private TextView content;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
         //   image = itemView.findViewById(R.id.imageView2);
            title = itemView.findViewById(R.id.postlist_title);
            content = itemView.findViewById(R.id.postlist_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ItemViewHolder.this, view, position);
                    }
                }
            });
        }
    }

    public void setOnItemClicklistener(OnPostListItemClickListener listener){
        this.listener = listener;
    }


    public void onItemClick(Post_List_Adapter.ItemViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    void addItem(Post_List lists){
        lists.add(lists);
    }

    void removeItem(int position){
        lists.remove(position);
    }


    public Post_List getItem(int position){
        return lists.get(position);
    }


}
