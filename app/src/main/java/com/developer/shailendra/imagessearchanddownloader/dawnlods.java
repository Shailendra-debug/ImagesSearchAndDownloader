package com.developer.shailendra.imagessearchanddownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class dawnlods extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dawnlods);
        recyclerView=findViewById(R.id.recyerview);
        textView=findViewById(R.id.text);
        textView.setVisibility(View.INVISIBLE);
        setUpRecyclerView();
    }
    private void setUpRecyclerView() {
        recyclerView.setHasFixedSize(true);
        DownloadedAdapter recyclerViewAdapter = new DownloadedAdapter(dawnlods.this, getData());
        recyclerView.setAdapter(recyclerViewAdapter);
        GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void back(View view) {
        onBackPressed();
    }
    private ArrayList<DownloadedModel> getData() {
        ArrayList<DownloadedModel> filesList = new ArrayList<>();
        DownloadedModel f;
        File targetDirector = Environment.getExternalStoragePublicDirectory("New Images Downloader/");
        File[] files = targetDirector.listFiles();
        //            noImageText.setVisibility(View.INVISIBLE);
        try {
            Arrays.sort(files, new Comparator() {
                public int compare(Object o1, Object o2) {

                    if (((File) o1).lastModified() > ((File) o2).lastModified()) {
                        return -1;
                    } else if (((File) o1).lastModified() < ((File) o2).lastModified()) {
                        return +1;
                    } else {
                        return 0;
                    }
                }

            });

            for (File file : files) {
                f = new DownloadedModel();
                f.setUri(Uri.fromFile(file));
                filesList.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            textView.setVisibility(View.VISIBLE);

        }
        return filesList;
    }
}