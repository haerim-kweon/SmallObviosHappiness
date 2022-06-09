package com.example.smallobvioshappiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ItemViewHolder> {
    private ArrayList<Comment> comments;

    public CommentAdapter(ArrayList<Comment> comments) {
        this.comments = comments;
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment, viewGroup,false);
        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Comment comment = comments.get(i);

//        itemViewHolder.image.setImageDrawable(chatting.getImage());
        itemViewHolder.nick.setText(comment.getNick());
        itemViewHolder.content.setText(comment.getContent());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
       // private ImageView image;
        private TextView nick;
        private TextView content;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
         //   image = itemView.findViewById(R.id.imageView2);
            nick = itemView.findViewById(R.id.comment_nick);
            content = itemView.findViewById(R.id.comment_content);
        }
    }


    void addItem(Comment comment){
        comments.add(comment);
    }

    void removeItem(int position){
        comments.remove(position);
    }


    public Comment getItem(int position){
        return comments.get(position);
    }

}
