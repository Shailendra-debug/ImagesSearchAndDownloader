package com.developer.shailendra.imagessearchanddownloader;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;


import android.os.Build;
import android.os.Bundle;

import android.view.View;

import android.widget.TextView;

import java.util.ArrayList;

public class Search_Results extends AppCompatActivity  {
    ArrayList<String> WebformatURL=new ArrayList<>();
      ArrayList<String> PageURL=new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__results);
        TextView text = findViewById(R.id.title_text);
        ViewPager viewPager = findViewById(R.id.pager);
        WebformatURL=getIntent().getStringArrayListExtra(ExampleAdapter.SEND);
        ArrayList<String> creatorName = getIntent().getStringArrayListExtra(ExampleAdapter.SEND1);
        PageURL=getIntent().getStringArrayListExtra(ExampleAdapter.URL);
        int i=getIntent().getIntExtra(ExampleAdapter.POSITION,0);
        Search_Results_Pager pager=new Search_Results_Pager(creatorName,WebformatURL,PageURL, text,Search_Results.this);
        viewPager.setAdapter(pager);
        viewPager.setCurrentItem(i);
         //viewPager.setCurrentItem(position);
//        imageView = findViewById(R.id.image_view_detail);
//        TextView toolbar=findViewById(R.id.title_text);
//        creatorName= getIntent().getStringArrayListExtra(ExampleAdapter.SEND1);
//        WebformatURL= getIntent().getStringArrayListExtra(ExampleAdapter.SEND);
//        PageURL= getIntent().getStringArrayListExtra(ExampleAdapter.URL);
//
//        toolbar.setText(creatorName);
//        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Search_Results.this,Detail.class);
//                intent.putExtra("Imageurl",imageUrl);
//                intent.putExtra("Image",creatorName);
//                startActivity(intent);
//            }
//        });
    }
//    public void daownlode(View view) {
//        if (ContextCompat.checkSelfPermission(Search_Results.this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            downlode();
//        } else {
//            requestStoragePermission();
//        }
//    }
//    public void shar(View view) {
//        checkPermission();
//        if (checkPermission()) {
//            shareImage(imageUrl);
//        }
//    }
//    public void more(View view) {
//        PopupMenu popup = new PopupMenu(this, view);
//        popup.setOnMenuItemClickListener(this);
//        popup.inflate(R.menu.popup);
//        popup.show();
//    }
//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            new AlertDialog.Builder(this)
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed for Downloading Images")
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(Search_Results.this,
//                                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//                        }
//                    })
//                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//        }
//    }
//    private void downlode() {
//        Toast.makeText(Search_Results.this, "Downloading...",Toast.LENGTH_SHORT).show();
//        DownloadManager downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
//        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(imageUrl))
//                .setTitle("Image Downloading")
//                .setDescription(creatorName)
//                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//        request.setDestinationInExternalPublicDir("New Images Downloader",creatorName+".png");
//        assert downloadManager != null;
//        downloadManager.enqueue(request);
//    }
//    private void shareImage(String url) {
//        Picasso.get().load(url).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                try {
//                    File mydir = new File(Environment.getExternalStorageDirectory() + "/11zon");
//                    if (!mydir.exists()) {
//                        mydir.mkdirs();
//                    }
//                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
//                    FileOutputStream outputStream = new FileOutputStream(fileUri);
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                    outputStream.flush();
//                    outputStream.close();
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
//                Uri uri= Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(fileUri),null,null));
//                // use intent to share image
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.putExtra(Intent.EXTRA_TEXT, "New Images Downloader : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
//                share.setType("image/*");
//                share.putExtra(Intent.EXTRA_STREAM, uri);
//                startActivity(Intent.createChooser(share, "Share Image"));
//            }
//            @Override
//            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//
//            }
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//            }
//        });
//    }
//    runtime storage permission
//    public boolean checkPermission() {
//        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
//            return false;
//        }
//        return true;
//    }
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            //do somethings
//    //        downlode();
//        }
//    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.browser:
//                //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
//                return true;
//            case R.id.set_wallpaper:
//
//                WallpaperManager wallpaperManager=WallpaperManager.getInstance(this);
//                Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                try {
//                    wallpaperManager.setBitmap(bitmap);
//                    Toast.makeText(this, "Wallpaper Set", Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    Toast.makeText(this, "Wallpaper Not Set", Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }
//                return true;
//            default:
//        }
//        return false;
//    }

    public void back(View view) {
        onBackPressed();
    }
}