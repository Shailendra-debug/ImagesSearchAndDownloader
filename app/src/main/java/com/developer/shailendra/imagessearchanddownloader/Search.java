package com.developer.shailendra.imagessearchanddownloader;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    private SimpleArcLoader process;
    private String query;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        TextView textView=findViewById(R.id.title_text);
        mRecyclerView = findViewById(R.id.recycler_view);
        query=getIntent().getStringExtra("send");
        textView.setText(query);
        process=findViewById(R.id.loader);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }
    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=16540928-953b26f5c191adbc64444e096&image_type=photo&pretty=true&page=1&per_page=200&q="+query;
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("hits");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String imageUrl = hit.getString("previewURL");
                                String creatorName = hit.getString("user");
                                String webformatURL = hit.getString("largeImageURL");
                                String pageURL = hit.getString("pageURL");
                                mExampleList.add(new ExampleItem(imageUrl,creatorName,webformatURL,pageURL));
                            }
                            mExampleAdapter = new ExampleAdapter(Search.this, mExampleList);
                            process.stop();
                            process.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                            mRecyclerView.setLayoutManager(layoutManager);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Search.this, "Data Not Lode", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(Search.this, "Data Not Lode", Toast.LENGTH_SHORT).show();

            }
        });
        mRequestQueue.add(request);
    }

    public void back(View view) {
        onBackPressed();
    }
}
