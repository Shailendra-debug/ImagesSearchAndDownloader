package com.developer.shailendra.imagessearchanddownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class Search_Data extends AppCompatActivity {
    private EditText editText;
    private ArrayList<String>mlist=new ArrayList<>();
    private DataListAdapter dataListAdapter=new DataListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__data);
        editText=findViewById(R.id.search);
        loadData();
        ImageButton key = findViewById(R.id.keyboard);
        ListView listView=findViewById(R.id.list);
        listView.setAdapter(dataListAdapter);
        KeyboardVisibilityEvent.isKeyboardVisible(this);
        editText.setOnEditorActionListener(editorListener);
        key.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Search Img");
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),a.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mlist.add(String.valueOf(editText.getText()));
                saveData();
                dataListAdapter.notifyDataSetChanged();
                Intent intent=new Intent(Search_Data.this,Search.class);
                String da= String.valueOf(editText.getText());
                intent.putExtra("send",da);
                startActivity(intent);
            }
            return false;
        }
    };
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mlist);
        editor.putString("task list", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList>() {}.getType();
        mlist = gson.fromJson(json, type);
        if (mlist == null) {
            mlist = new ArrayList<>();
        }
    }
    public void back(View view) {
    onBackPressed();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK && null != data) {

                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                assert result != null;
                editText.setText(result.get(0));
                mlist.add(result.get(0));
                saveData();
                dataListAdapter.notifyDataSetChanged();
                Intent intent = new Intent(Search_Data.this, Search.class);
                intent.putExtra("send", result.get(0));
                startActivity(intent);// set the input data to the editText alongside if want to.
            }
        }
    }

    private class DataListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.grid, viewGroup, false);
            TextView title;
            ImageView i1;
            title =row.findViewById(R.id.text);
            i1=row.findViewById(R.id.btn);
            title.setText(mlist.get(i));
            RelativeLayout relativeLayout=row.findViewById(R.id.view);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Search_Data.this, Search.class);
                    intent.putExtra("send", mlist.get(i));
                    startActivity(intent);
                }
            });
            i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mlist.remove(i);
                    saveData();
                    dataListAdapter.notifyDataSetChanged();
                }
            });
            return (row);
        }
    }
}