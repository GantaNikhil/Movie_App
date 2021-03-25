package com.example.movie_app;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.LauncherActivity.ListItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ConcurrentModificationException;
import java.util.List;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.ViewHolderActivity> {
    private List<Result> listItems;
    private Context context;
    ImageView imageview;


//       private OnItemClickListener mlistener;
//    public  interface OnItemClickListener{
//        void OnItemClick(int position);
//    }
//
//    public void setOnItemClickListener(OnItemClickListener listener ){
//        mlistener=listener;
//    }


    public AdapterActivity( List<Result> listItems, Context context) {
        this.context = context;
        this.listItems = listItems;
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
        Result result = listItems.get(position);
        holder.txttitle.setText(result.getTitle());

        String URL = "https://image.tmdb.org/t/p/w500" + result.getPosterPath();
        Picasso.get()
                .load(URL)
                .placeholder(R.drawable.loading)
                .into(imageview);
    }

    @Override
    public int getItemCount() {

        return listItems.size();
    }

    public class ViewHolderActivity extends RecyclerView.ViewHolder {
        TextView txttitle;

        public ViewHolderActivity(@NonNull View itemView) {
            super(itemView);
            txttitle = (TextView) itemView.findViewById(R.id.txtTitle);
            imageview = (ImageView) itemView.findViewById(R.id.imgIcon);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mlistener!=null){
//                        int position =getAdapterPosition();
//                        if(position !=RecyclerView.NO_POSITION){
//                            mlistener.OnItemClick(position);
//                        }
//                    }
//                }
//            });
        }
    }
}
