package com.developer.shailendra.imagessearchanddownloader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Downloaded_Results extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded__results);
        ViewPager viewPager = findViewById(R.id.Downloaded_Pager);
        Downloaded_Pager adapter=new Downloaded_Pager(this,getData());
        int i =getIntent().getIntExtra(DownloadedAdapter.DOWNLOADED_ADAPTER_POSITION,0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(i);
    }

    public void back(View view) {
        onBackPressed();
    }
    private ArrayList<DownloadedModel> getData() {
        ArrayList<DownloadedModel> filesList = new ArrayList<>();
        DownloadedModel f;
        File targetDirector = Environment.getExternalStoragePublicDirectory("New Images Downloader/");
        File[] files = targetDirector.listFiles();
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

        }
        return filesList;
    }
}