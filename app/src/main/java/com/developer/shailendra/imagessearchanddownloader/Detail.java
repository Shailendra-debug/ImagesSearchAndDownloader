package com.developer.shailendra.imagessearchanddownloader;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;



public class Detail extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView textView=findViewById(R.id.toolbartext);
        PhotoView photoView =  findViewById(R.id.photo_view);
        String name= getIntent().getStringExtra(Search_Results_Pager.IMAGE_NAME);
        String image= getIntent().getStringExtra(Search_Results_Pager.IMAGE_URL);
        textView.setText(name);
        Picasso.get().load(image).fit().centerInside().into(photoView);
    }

    public void back(View view) {
        onBackPressed();
    }
}
