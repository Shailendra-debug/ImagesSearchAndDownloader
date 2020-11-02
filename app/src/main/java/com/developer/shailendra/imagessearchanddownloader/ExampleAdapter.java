package com.developer.shailendra.imagessearchanddownloader;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    public static final String SEND = "SEND";
    public static final String SEND1 = "send";
    public static final String URL = "url";
    public static final String POSITION = "position";
    private final Context mContext;
    private final ArrayList<ExampleItem> mExampleList;
    private final ArrayList<String> creatorName=new ArrayList<>();
    private final ArrayList<String> WebformatURL=new ArrayList<>();
    private final ArrayList<String> PageURL=new ArrayList<>();


    public ExampleAdapter(Context context, ArrayList<ExampleItem> exampleList) {
        mContext = context;
        mExampleList = exampleList;
    }
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v);
    }
    @Override
    public void onBindViewHolder(final ExampleViewHolder holder, final int position) {
        ExampleItem currentItem = mExampleList.get(position);
        final String imageUrl = currentItem.getImageUrl();
        Picasso.get().load(imageUrl).placeholder(R.drawable.place).fit().centerInside().into(holder.mImageView);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedeta();
                Intent intent=new Intent(mContext,Search_Results.class);
                intent.putExtra(SEND,WebformatURL);
                intent.putExtra(SEND1,creatorName);
                intent.putExtra(URL,PageURL);
                intent.putExtra(POSITION,position);
                mContext.startActivity(intent);
            }
        });
    }

    private void savedeta() {
        for (ExampleItem e:mExampleList) {
            PageURL.add(e.getPageURL());
            WebformatURL.add(e.getWebformatURL());
            creatorName.add(e.getCreator());
        }

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
     static class ExampleViewHolder extends RecyclerView.ViewHolder {
         RelativeLayout parentLayout;
         ImageView mImageView;
         ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
