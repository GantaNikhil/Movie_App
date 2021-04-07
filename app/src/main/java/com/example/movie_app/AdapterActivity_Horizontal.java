package com.example.movie_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie_app.MovieCategory.Result;

import java.util.List;

public class AdapterActivity_Horizontal extends RecyclerView.Adapter<AdapterActivity_Horizontal.ViewHolderActivity_Horizontal> {
    private final List<Result> listItems;
    private final Context context;

    public AdapterActivity_Horizontal(List<Result> listItems, Context context) {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public ViewHolderActivity_Horizontal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_horizontal, parent, false);
        return new ViewHolderActivity_Horizontal(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderActivity_Horizontal holder, int position) {
        Result result = listItems.get(position);

        String title = result.getTitle();
        holder.txtTitle.setText(title);

        String rate = Double.toString(listItems.get(position).getVoteAverage());
        holder.rating.setText(rate);

        String URL = "https://image.tmdb.org/t/p/w500";
        Glide.with(context)
                .load(URL + listItems.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .into(holder.imageview);


        Result horizontalModel = listItems.get(position);
        holder.txtTitle.setText(horizontalModel.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result clickedData = listItems.get(position);

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DashBoardActivity.Original_Title, listItems.get(position).getOriginalTitle());
                intent.putExtra(DashBoardActivity.OverView, listItems.get(position).getOverview());
                intent.putExtra(DashBoardActivity.BackdropPath, listItems.get(position).getBackdropPath());
                intent.putExtra(DashBoardActivity.Release_Date, listItems.get(position).getReleaseDate());
                intent.putExtra(String.valueOf(DashBoardActivity.Lating), Double.toString(listItems.get(position).getVoteAverage()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Toast.makeText(v.getContext(), "You Clicked " + clickedData.getTitle(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolderActivity_Horizontal extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView rating;
        ImageView imageview;

        public ViewHolderActivity_Horizontal(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imageview = (ImageView) itemView.findViewById(R.id.imgIcon);
            rating = (TextView) itemView.findViewById(R.id.rating);
        }
    }
}