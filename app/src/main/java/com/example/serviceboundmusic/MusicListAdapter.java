package com.example.serviceboundmusic;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.MusicViewHolder> {

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class MusicViewHolder extends RecyclerView.ViewHolder {
        ImageView ic_play_video;
        TextView tv_file_name_rv;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            ic_play_video = itemView.findViewById(R.id.ic_play_video);
            tv_file_name_rv = itemView.findViewById(R.id.tv_file_name_rv);
        }

    }
}
