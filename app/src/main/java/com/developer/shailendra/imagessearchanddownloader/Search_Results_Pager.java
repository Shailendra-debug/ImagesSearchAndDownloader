package com.developer.shailendra.imagessearchanddownloader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.DOWNLOAD_SERVICE;

public class Search_Results_Pager extends PagerAdapter {
    public static final String IMAGE_URL = "Image_url";
    public static final String IMAGE_NAME = "Image_name";
    private final int STORAGE_PERMISSION_CODE = 1;
    private final ArrayList<String> creatorName;
    private final ArrayList<String> WebformatURL;
    private final ArrayList<String> PageURL;
    private final Context context;
    private final TextView textView;
    public static final int PERMISSION_WRITE = 0;
    private String fileUri;
    LayoutInflater mLayoutInflater;
    public Search_Results_Pager(ArrayList<String> creatorName, ArrayList<String> webformatURL, ArrayList<String> pageURL, TextView text, Context context) {
        this.creatorName = creatorName;
        WebformatURL = webformatURL;
        PageURL = pageURL;
        this.context = context;
        this.textView=text;
    }
    @Override
    public int getCount() {
        return WebformatURL.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        mLayoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = mLayoutInflater.inflate(R.layout.search_results_pager, container, false);
        final ImageButton donload,shair,menu;
        final ImageView imageView=itemView.findViewById(R.id.image_view_detail);
        donload=itemView.findViewById(R.id.daownlode);
        shair=itemView.findViewById(R.id.shar);
        menu=itemView.findViewById(R.id.more);
        Picasso.get().load(WebformatURL.get(position)).placeholder(R.drawable.placeholder).fit().centerInside().into(imageView);
        textView.setText(creatorName.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,Detail.class).putExtra(IMAGE_URL,WebformatURL.get(position)).putExtra(IMAGE_NAME,creatorName.get(position)));
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(context,menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.popup);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.browser:
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PageURL.get(position))));
                                return true;
                            case R.id.set_wallpaper:

                                WallpaperManager wallpaperManager=WallpaperManager.getInstance(context);
                                Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
                                try {
                                    wallpaperManager.setBitmap(bitmap);
                                    Toast.makeText(context, "Wallpaper Set", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    Toast.makeText(context, "Wallpaper Not Set", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }
                                return true;
                            default:
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
        donload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daownlode(position);
            }
        });
        shair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
        if (checkPermission()) {
            shareImage(WebformatURL.get(position));
        }
            }
        });
        ViewPager viewPager=(ViewPager)container;
        viewPager.addView(itemView);
        return itemView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    private void shareImage(String url) {
        Picasso.get().load(url).into(new Target() {
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
        public void daownlode(int i) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            downlode(i);
        } else {
            requestStoragePermission(i);
        }
    }


    private void requestStoragePermission(final int i) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for Downloading Images")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            downlode(i);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }
    private void downlode(int i) {
        Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(WebformatURL.get(i)))
                .setTitle("Image Downloading")
                .setDescription(creatorName.get(i))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDestinationInExternalPublicDir("New Images Downloader", creatorName.get(i) + ".png");
        assert downloadManager != null;
        downloadManager.enqueue(request);
    }
}
