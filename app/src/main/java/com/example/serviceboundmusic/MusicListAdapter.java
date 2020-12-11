package com.example.serviceboundmusic;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {
    private List<Music> listMusic;
    private OnItemListClickListener onItemListClickListener;

    public MusicListAdapter(List<Music> listMusic,OnItemListClickListener onItemListClickListener) {
        this.listMusic = listMusic;
        this.onItemListClickListener = onItemListClickListener;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music music = listMusic.get(position);
        holder.setInfoFile(music);
    }

    @Override
    public int getItemCount() {
        return listMusic.size();
    }


    class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ic_play_video;
        TextView tv_file_name_rv;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            ic_play_video = itemView.findViewById(R.id.ic_play_video);
            tv_file_name_rv = itemView.findViewById(R.id.tv_file_name_rv);
            itemView.setOnClickListener(this);
        }

        public void setInfoFile(Music music){
            tv_file_name_rv.setText(music.getName());
        }

        @Override
        public void onClick(View view) {
            onItemListClickListener.onClickListener(listMusic.get(getAdapterPosition()),getAdapterPosition());
        }
    }

    public interface OnItemListClickListener{
        void onClickListener(Music music,int position);
    }


}
