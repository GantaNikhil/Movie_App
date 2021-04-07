package com.example.movie_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import static com.example.movie_app.DashBoardActivity.BackdropPath;
import static com.example.movie_app.DashBoardActivity.Lating;
import static com.example.movie_app.DashBoardActivity.Original_Title;
import static com.example.movie_app.DashBoardActivity.OverView;
import static com.example.movie_app.DashBoardActivity.Release_Date;

public class DetailActivity extends AppCompatActivity {
    TextView Originaltitle, Overview, ReleaseDate, Rating;
    ImageView Backdrop_Path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailactivity);

        Originaltitle = (TextView) findViewById(R.id.OriginalTitle);
        Overview = (TextView) findViewById(R.id.overview);
        ReleaseDate = (TextView) findViewById(R.id.releaseDate);
        Rating = (TextView) findViewById(R.id.rating);
        Backdrop_Path = (ImageView) findViewById(R.id.backdroppath);

        Intent intent = getIntent();

        if (intent.getStringExtra(BackdropPath) != null) {
            String bdp = "https://image.tmdb.org/t/p/w500";

            String OriginalTitle = intent.getStringExtra(String.valueOf(Original_Title));
            String overview = intent.getStringExtra(String.valueOf(OverView));
            String releaseDate = intent.getStringExtra(String.valueOf(Release_Date));
            String rating = String.valueOf(intent.getStringExtra(String.valueOf(Lating)));
            String backdroppath = intent.getStringExtra(String.valueOf(BackdropPath));

            Originaltitle.setText(OriginalTitle);
            Overview.setText(overview);
            ReleaseDate.setText(releaseDate);
            Rating.setText(rating);

            Glide.with(DetailActivity.this)
                    .load(bdp + backdroppath)
                    .placeholder(R.drawable.loading)
                    .into(Backdrop_Path);
        } else {
            Toast.makeText(this, "NO API DATA FOUND", Toast.LENGTH_SHORT).show();

        }
    }
}

