package com.developer.shailendra.imagessearchanddownloader;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Downloaded_Pager extends PagerAdapter {
    private String fileUri;
    private Context context;
    private  ArrayList<DownloadedModel> filesList;
    LayoutInflater mLayoutInflater;

    public Downloaded_Pager(Context context, ArrayList<DownloadedModel> filesList) {
        this.context = context;
        this.filesList = filesList;
    }

    public Downloaded_Pager() {
    }

    @Override
    public int getCount() {
        return filesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final DownloadedModel files = filesList.get(position);
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = mLayoutInflater.inflate(R.layout.downloaded_pager, container, false);

        // referencing the image view from the item.xml file
        ImageButton shar=(ImageButton)itemView.findViewById(R.id.shar);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.image_view_detail);
        ImageView  wallpaper = (ImageView) itemView.findViewById(R.id.set_wallpaper);
        shar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(files.getUri());
            }
        });
        wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperManager wallpaperManager=WallpaperManager.getInstance(context);
                Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(context, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(context, "Wallpaper Not Set", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        // setting the image in the imageView
//        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(files.getUri())
                .fit()
                .centerCrop()
                .into(imageView);
        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(itemView);
        return itemView;
    }

    private void shareImage(Uri uri) {
        Picasso.get().load(uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }
                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Uri uri= Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), BitmapFactory.decodeFile(fileUri),null,null));
                // use intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, "New Images Downloader : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(Intent.createChooser(share, "Share Image"));
            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
