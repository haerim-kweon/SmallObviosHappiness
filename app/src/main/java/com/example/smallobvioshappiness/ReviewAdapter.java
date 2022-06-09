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

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ItemViewHolder> {
    private ArrayList<Review> reviews;
    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    private OnReviewItemClickListener listener;


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review, viewGroup,false);
        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Review review = reviews.get(i);

//        itemViewHolder.image.setImageDrawable(chatting.getImage());
        itemViewHolder.title.setText(review.getTitle());
        itemViewHolder.content.setText(review.getContent());
        Glide.with(itemViewHolder.itemView.getContext()).load(review.getImgUrl()).into(itemViewHolder.imageView);

    }

    public void setOnItemClicklistener(OnReviewItemClickListener listener){
        this.listener = listener;
    }
    class ItemViewHolder extends RecyclerView.ViewHolder{
       // private ImageView image;
        private TextView title;
        private TextView content;
        private ImageView imageView;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
         //   image = itemView.findViewById(R.id.imageView2);
            title = itemView.findViewById(R.id.review_title);
            content = itemView.findViewById(R.id.review_content);
            imageView = itemView.findViewById(R.id.review_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ReviewAdapter.ItemViewHolder.this, view, position);
                    }
                }
            });
        }
    }


    void addItem(Review review){
        reviews.add(review);
    }

    void removeItem(int position){
        reviews.remove(position);
    }


    public Review getItem(int position){
        return reviews.get(position);
    }

}
