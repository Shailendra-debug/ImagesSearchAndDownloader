package com.developer.shailendra.imagessearchanddownloader;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.leo.simplearcloader.SimpleArcLoader;

import net.yslibrary.android.keyboardvisibilityevent.BuildConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    private SimpleArcLoader process;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recycler);
        process=findViewById(R.id.loade);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.navigation_menu);
        navigationView.setItemIconTintList(null);
        final DrawerLayout drawerLayout = findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        drawerLayout.closeDrawer(GravityCompat.START);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.downloaded:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent intent=new Intent(MainActivity.this,dawnlods.class);
                        startActivity(intent);
                        break;
                        case R.id.share:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;
                    case R.id.rating:
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.privacy:
                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/document/d/e/2PACX-1vQ4-puYQ0dT_d1B7jPSGg8FCVo356Tf8TvcI_r87pI3cSm4r4BMIX52xy0GwRGIc99IGSCxLfYr1V9d/pub" )));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.terms:

                        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://docs.google.com/document/d/e/2PACX-1vQOjN5duXyZU-_iGWUMpx12UT7CZbwsYd6HTsM82LP8ig8KkGlTV20jW9QwrMK2Hx9vZwab0iniu9Fd/pub" )));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;case R.id.contact:
                        Intent intentmail = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:"+"shailendrakushwaha732@gmail.com"));
                        startActivity(intentmail);
                        break;
                }
                return true;
            }
        });
    }

    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=16540928-953b26f5c191adbc64444e096&image_type=photo&pretty=true&page=3&per_page=200";
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
                                String pageURL=hit.getString("pageURL");
                                mExampleList.add(new ExampleItem(imageUrl,creatorName,webformatURL,pageURL));
                            }
                            mExampleAdapter = new ExampleAdapter(MainActivity.this, mExampleList);
                            process.stop();
                            process.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mRecyclerView.setAdapter(mExampleAdapter);
                            GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
                            mRecyclerView.setLayoutManager(layoutManager);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Data Not Lode1", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d("MY_TAG", "onErrorResponse: "+error);
                Toast.makeText(MainActivity.this, "Data Not Lode2", Toast.LENGTH_SHORT).show();
                process.stop();
                process.setVisibility(View.INVISIBLE);
            }
        });
        mRequestQueue.add(request);
    }

    public void search(View view) {
        Intent intent=new Intent(getApplicationContext(),Search_Data.class);
        startActivity(intent);

    }
}
