package com.example.movie_app;

import android.app.Activity;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {
    //    implements AdapterActivity.OnItemClickListener
    private AdapterActivity adapter;
    private List<Result> listItems;
    // NavigationView navigationView;
    //   ActionBarDrawerToggle toggle;
    // DrawerLayout drawerLayout;
//   public String EXTRA_URL ="ImageURL";
//    public String OverView ="overview";

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private static final String URL_DATA = "https://api.themoviedb.org/3/movie/popular?api_key=f073cd04161a18649b124a481a6e9c4a&language=en-US&page=1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);

//
//        navigationView = (NavigationView) findViewById(R.id.navmenu);
//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        Toast.makeText(getApplicationContext(), "Home page is Open", Toast.LENGTH_LONG).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
//                    case R.id.fav:
//                        Toast.makeText(getApplicationContext(), "Fav page is Open", Toast.LENGTH_LONG).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
//                    case R.id.logOut:
//                        View Logout = findViewById(R.id.logOut);
//                        Logout.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                FirebaseAuth.getInstance().signOut();
//                                Intent intent = new Intent(DashBoardActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        });
//                        return false;
//                }
//                return true;
//            }
//        });

        listItems = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        loadRecyclerViewData();

    }

    private void loadRecyclerViewData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_DATA, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
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
                            adapter = new AdapterActivity(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            //   adapter.setOnItemClickListener(DetailActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
//    @Override
//    public void OnItemClick(int position) {
//        Intent detailIntent = new Intent(this, ListItem.class);
//        Result clickedItem = listItems.get(position);
//
//        detailIntent.putExtra(EXTRA_URL,clickedItem.getBackdropPath());
//        detailIntent.putExtra(OverView,clickedItem.getOverview());
//
//        startActivity(detailIntent);
//    }
}
