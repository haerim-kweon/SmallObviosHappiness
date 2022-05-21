package com.example.smallobvioshappiness;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ItemViewHolder> {
    private ArrayList<Chat> chats;

    public ChatAdapter(ArrayList<Chat> chats) {
        this.chats = chats;
    }


    @Override
    public int getItemCount() {
        return chats.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.person_profile, viewGroup,false);
        return new ItemViewHolder(view);
    }

    //데이터를 레이아웃에 어떻게 넣어줄지
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        Chat chatting = chats.get(i);

//        itemViewHolder.image.setImageDrawable(chatting.getImage());
        itemViewHolder.name.setText(chatting.getName());
        itemViewHolder.recent_chat.setText(chatting.getRecent_chat());
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
       // private ImageView image;
        private TextView name;
        private TextView recent_chat;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
         //   image = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.profile_nickname);
            recent_chat = itemView.findViewById(R.id.recent_chat);

        }
    }


    void addItem(Chat chat){
        chats.add(chat);
    }

    void removeItem(int position){
        chats.remove(position);
    }


}
