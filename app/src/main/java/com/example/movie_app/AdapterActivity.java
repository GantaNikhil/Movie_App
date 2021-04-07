package com.example.movie_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_app.Models.VerticalModel;
import com.example.movie_app.MovieCategory.Result;

import java.util.List;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.ViewHolderActivity> {
    private final Context context;
    private final List<VerticalModel> arrayList;

    public AdapterActivity(Context context, List<VerticalModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderActivity onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view, parent, false);
        return new ViewHolderActivity(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderActivity holder, int position) {

        VerticalModel verticalModel = arrayList.get(position);
        String title = verticalModel.getTitle();
        holder.categorymovies.setText(title);

        List<Result> singleItem = verticalModel.getArrayList();
        AdapterActivity_Horizontal adpHorizontal;
        adpHorizontal = new AdapterActivity_Horizontal(singleItem, context);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(adpHorizontal);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderActivity extends RecyclerView.ViewHolder {
        TextView categorymovies;
        RecyclerView recyclerView;

        public ViewHolderActivity(@NonNull View itemView) {
            super(itemView);
            categorymovies = (TextView) itemView.findViewById(R.id.categoryMovies);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView1);
        }
    }
}