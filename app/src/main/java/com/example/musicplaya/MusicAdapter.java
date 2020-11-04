package com.example.musicplaya;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<MusicFile> mMusicFiles;

    MusicAdapter(Context mContext, ArrayList<MusicFile> mMusicFiles) {
        this.mContext = mContext;
        this.mMusicFiles = mMusicFiles;
    }

    @NonNull
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MyViewHolder holder, final int position) {
        holder.music_fileName.setText(mMusicFiles.get(position).getTitle());
        byte[] image = getAlbumArt(mMusicFiles.get(position).getPath());
        if (image != null) {
            Glide.with(mContext).asBitmap().load(image).into(holder.music_img);
        }
        else {
            Glide.with(mContext).load(R.drawable.musicnote).into(holder.music_img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMusicFiles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView music_fileName;
        ImageView music_img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            music_fileName = itemView.findViewById(R.id.music_fileName);
            music_img = itemView.findViewById(R.id.music_img);
        }
    }

    private byte[] getAlbumArt(String uri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
