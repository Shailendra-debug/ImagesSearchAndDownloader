package com.developer.shailendra.imagessearchanddownloader;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class DownloadedAdapter extends RecyclerView.Adapter<DownloadedAdapter.downloadedHolder> {
    public static final String DOWNLOADED_ADAPTER_POSITION = "DownloadedAdapter_position";
    public static final String DOWNLOADED_ADAPTER_FILES_LIST = "DownloadedAdapter_Files_List";
    private final Context context;
    private final ArrayList<DownloadedModel> filesList;

    public DownloadedAdapter(Context context, ArrayList<DownloadedModel> filesList) {
        this.context = context;
        this.filesList = filesList;
    }


    @NonNull
    @Override
    public downloadedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,null,false);
        return new downloadedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull downloadedHolder holder, final int position) {
        final DownloadedModel files = filesList.get(position);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Picasso.get()
                .load(files.getUri())
                .fit().centerInside()
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,Downloaded_Results.class);
                intent.putExtra(DOWNLOADED_ADAPTER_POSITION,position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filesList.size();
    }

    public static class downloadedHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        ImageView imageView;
        public downloadedHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            relativeLayout=itemView.findViewById(R.id.parent_layout);
        }
    }
}
