package com.example.smallobvioshappiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Post_Small_Adapter extends RecyclerView.Adapter<Post_Small_Adapter.ItemViewHolder> {
    private ArrayList<Post> posts;
    private OnSmallPostItemClickListener listener;

    public Post_Small_Adapter(ArrayList<Post> posts) {
        this.posts = posts;
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_small, viewGroup,false);

        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Post post = posts.get(i);

        itemViewHolder.title.setText(post.getTitle());
        Glide.with(itemViewHolder.itemView.getContext()).load(post.getImgURL()).into(itemViewHolder.image);




    }


    public void setOnItemClicklistener(OnSmallPostItemClickListener listener){
        this.listener = listener;
    }


    public void onItemClick(ItemViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView image;




        public ItemViewHolder(@NonNull View itemView){
            super(itemView);


            title = itemView.findViewById(R.id.post_samll_title);
            image = itemView.findViewById(R.id.post_small_image);
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


    void addItem(Post post){
        posts.add(post);
    }

    void removeItem(int position){
        posts.remove(position);
    }


    public Post getItem(int position){
        return posts.get(position);
    }
}
