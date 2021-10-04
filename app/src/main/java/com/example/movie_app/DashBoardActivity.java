package com.example.movie_app;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movie_app.Models.VerticalModel;
import com.example.movie_app.MovieCategory.Result;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashBoardActivity extends AppCompatActivity {
    private ArrayList<Result> listItems;
    private final ArrayList<VerticalModel> verticalModelList = new ArrayList<>();

    public static final String BackdropPath = "ImageURL";
    public static final String OverView = "overview";
    public static final Double Lating = 9.8;
    public static final String Release_Date = "19-12-2021";
    public static final String Original_Title = " Mama";
    public static String Rate = "123";

    private View Logout;

    ProgressDialog progressDialog;
    private RecyclerView VerticalrecyclerView;
    private AdapterActivity_Horizontal adapterActivity_horizontal;

    private RequestQueue requestQueue;
    private String URL_DATA_POPULAR = "https://api.themoviedb.org/3/movie/popular?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1";
    private String URL_DATA_NOWPLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1";
    private String URL_DATA_TOPRATED = "https://api.themoviedb.org/3/movie/top_rated?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1";
    private String URL_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_layout);

        VerticalrecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        VerticalrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        requestQueue = Volley.newRequestQueue(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        getPopular();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are You Sure You Want to Log Out ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Logout = (View) findViewById(R.id.logOut);
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You Sure You Want to Log Out ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    Logout = (View) findViewById(R.id.logOut);
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    DashBoardActivity.super.onBackPressed();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getPopular() {
        listItems = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_DATA_POPULAR, null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject o = array.getJSONObject(i);
                            Result item = new Result();

                            item.setAdult(o.getBoolean("adult"));
                            item.setBackdropPath(o.getString("backdrop_path"));
                            item.setId(o.getInt("id"));
                            item.setOriginalLanguage(o.getString("original_language"));
                            item.setOriginalTitle(o.getString("original_title"));
                            item.setOverview(o.getString("overview"));
                            item.setPopularity(o.getDouble("popularity"));
                            item.setPosterPath(o.getString("poster_path"));
                            item.setReleaseDate(o.getString("release_date"));
                            item.setTitle(o.getString("title"));
                            item.setVideo(o.getBoolean("video"));
                            item.setVoteAverage(o.getDouble("vote_average"));
                            item.setVoteCount(o.getInt("vote_count"));
                            listItems.add(item);
                        }
                        VerticalModel verticalModel = new VerticalModel("Popular Movies", listItems);
                        verticalModelList.add(verticalModel);
                        getTopRated();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    getPopular();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getTopRated() {
        listItems = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_DATA_TOPRATED, null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject o = array.getJSONObject(i);
                            Result item = new Result();

                            item.setAdult(o.getBoolean("adult"));
                            item.setBackdropPath(o.getString("backdrop_path"));
                            item.setId(o.getInt("id"));
                            item.setOriginalLanguage(o.getString("original_language"));
                            item.setOriginalTitle(o.getString("original_title"));
                            item.setOverview(o.getString("overview"));
                            item.setPopularity(o.getDouble("popularity"));
                            item.setPosterPath(o.getString("poster_path"));
                            item.setReleaseDate(o.getString("release_date"));
                            item.setTitle(o.getString("title"));
                            item.setVideo(o.getBoolean("video"));
                            item.setVoteAverage(o.getDouble("vote_average"));
                            item.setVoteCount(o.getInt("vote_count"));
                            listItems.add(item);
                        }
                        VerticalModel verticalModel = new VerticalModel("Top Rated Movies ", listItems);
                        verticalModelList.add(verticalModel);
                        getUpcoming();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    getTopRated();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getUpcoming() {
        listItems = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_UPCOMING, null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject o = array.getJSONObject(i);
                            Result item = new Result();

                            item.setAdult(o.getBoolean("adult"));
                            item.setBackdropPath(o.getString("backdrop_path"));
                            item.setId(o.getInt("id"));
                            item.setOriginalLanguage(o.getString("original_language"));
                            item.setOriginalTitle(o.getString("original_title"));
                            item.setOverview(o.getString("overview"));
                            item.setPopularity(o.getDouble("popularity"));
                            item.setPosterPath(o.getString("poster_path"));
                            item.setReleaseDate(o.getString("release_date"));
                            item.setTitle(o.getString("title"));
                            item.setVideo(o.getBoolean("video"));
                            item.setVoteAverage(o.getDouble("vote_average"));
                            item.setVoteCount(o.getInt("vote_count"));
                            listItems.add(item);
                        }
                        VerticalModel verticalModel = new VerticalModel("Upcoming Movies ", listItems);
                        verticalModelList.add(verticalModel);
                        getNowPlaying();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    getUpcoming();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getNowPlaying() {
        listItems = new ArrayList<>();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_DATA_NOWPLAYING, null,
                response -> {
                    try {
                        JSONArray array = response.getJSONArray("results");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject o = array.getJSONObject(i);
                            Result item = new Result();

                            item.setAdult(o.getBoolean("adult"));
                            item.setBackdropPath(o.getString("backdrop_path"));
                            item.setId(o.getInt("id"));
                            item.setOriginalLanguage(o.getString("original_language"));
                            item.setOriginalTitle(o.getString("original_title"));
                            item.setOverview(o.getString("overview"));
                            item.setPopularity(o.getDouble("popularity"));
                            item.setPosterPath(o.getString("poster_path"));
                            item.setReleaseDate(o.getString("release_date"));
                            item.setTitle(o.getString("title"));
                            item.setVideo(o.getBoolean("video"));
                            item.setVoteAverage(o.getDouble("vote_average"));
                            item.setVoteCount(o.getInt("vote_count"));
                            listItems.add(item);
                        }
                        VerticalModel verticalModel = new VerticalModel("Now Playing", listItems);
                        verticalModelList.add(verticalModel);
                        setData();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    getNowPlaying();
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void setData() {
        VerticalrecyclerView.setAdapter(new AdapterActivity(DashBoardActivity.this, verticalModelList));
        progressDialog.dismiss();

    }
}