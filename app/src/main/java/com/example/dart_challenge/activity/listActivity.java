package com.example.dart_challenge.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.dart_challenge.R;
import com.example.dart_challenge.adapter.Adapter;
import com.example.dart_challenge.model.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
data listing activity
 */

public class listActivity extends AppCompatActivity {

    Data d;
    private RecyclerView recyclerList;
    private List<Data> datas = new ArrayList<>();
    private SearchView searchView;
    private TextView noResults;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerList = findViewById(R.id.recyclerItems);
        noResults = new TextView(this);
        noResults.setVisibility(View.GONE);
        linearLayout = findViewById(R.id.linearLayoutList);
        noResults.setBackgroundColor(getResources().getColor(R.color.gray_background));
        noResults.setText(getResources().getString(R.string.warning));
        noResults.setTextColor(getResources().getColor(R.color.colorPrimarytext));
        noResults.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        noResults.setGravity(Gravity.CENTER);
        linearLayout.addView(noResults);

        // layout definition
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerList.setLayoutManager(layoutManager);

        // adapter definition
        final Adapter adapter = new Adapter(datas);
        recyclerList.setAdapter(adapter);

        // getting the data with a GET request using volley
        String url = "https://api.github.com/repositories";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                // storing the data on a local object
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject dataObject = response.getJSONObject(i);
                        d = new Data(dataObject.getString("name"), dataObject.getString("description"), dataObject.getString("url"));
                        datas.add(d);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });

        queue.add(arrayRequest);


        // filtering
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() > 2) {
                    adapter.getFilter().filter(s);
                    adapter.notifyDataSetChanged();

                    // wait to adapter values to update
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // show the text view with a warning
                            if (adapter.getItemCount() == 0)
                                noResults.setVisibility(View.VISIBLE);
                            else
                                noResults.setVisibility(View.GONE);
                        }
                    }, 100);


                } else {
                    adapter.getFilter().filter("");
                    adapter.notifyDataSetChanged();
                }

                return false;
            }
        });
    }
}
