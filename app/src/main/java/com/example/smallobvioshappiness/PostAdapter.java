package com.example.smallobvioshappiness;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter  extends RecyclerView.Adapter<PostAdapter.ItemViewHolder> {
    private ArrayList<Post> posts;
    private OnPostItemClickListener listener;

    public PostAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post, viewGroup,false);

        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Post post = posts.get(i);


        itemViewHolder.category.setText(post.getCategory());
        itemViewHolder.title.setText(post.getTitle());
        itemViewHolder.time.setText(post.getCreatedAt());
        itemViewHolder.price.setText(String.valueOf(post.getPrice())+" 원");
        itemViewHolder.interest_num.setText(String.valueOf(post.getInterest_num()));
       // itemViewHolder.interest_state.set
        if(post.getInterest_state()==1){

        }
        else {

        }

        //itemViewHolder.interest_state.setImageDrawable(post.getInterest_state());


    }


    public void setOnItemClicklistener(OnPostItemClickListener listener){
        this.listener = listener;
    }


    public void onItemClick(ItemViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView category;
        private TextView title;
        private TextView price;
        private ImageButton interest_state;
        private TextView interest_num;
        private TextView time;



        public ItemViewHolder(@NonNull View itemView){
            super(itemView);


            category = itemView.findViewById(R.id.postcategory);
            title = itemView.findViewById(R.id.posttitle);
            time = itemView.findViewById(R.id.posttime);
            price = itemView.findViewById(R.id.postprice);
            interest_state = itemView.findViewById(R.id.interest_state);
            interest_num = itemView.findViewById(R.id.interest_num);

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
