package com.seunghyun.firebasetest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Item> items;

    CustomAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Item item = items.get(position);
        viewHolder.idTV.setText(item.getId());
        viewHolder.chatTV.setText(item.getChat());
        viewHolder.timeTV.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTV, chatTV, timeTV;

        ViewHolder(View itemView) {
            super(itemView);
            idTV = itemView.findViewById(R.id.id_tv);
            chatTV = itemView.findViewById(R.id.chat_tv);
            timeTV = itemView.findViewById(R.id.time_tv);
        }
    }
}
