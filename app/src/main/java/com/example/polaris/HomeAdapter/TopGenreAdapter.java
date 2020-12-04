package com.example.polaris.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polaris.R;

import java.util.ArrayList;

public class TopGenreAdapter extends RecyclerView.Adapter<TopGenreAdapter.GenresViewHolder> {

    ArrayList<TopGenreHelperClass> topGenres;

    public TopGenreAdapter(ArrayList<TopGenreHelperClass> topGenres) {
        this.topGenres = topGenres;
    }

    @NonNull
    @Override
    public GenresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topgenres_carddesign, parent, false);
        GenresViewHolder genresViewHolder = new GenresViewHolder(view);
        return genresViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenresViewHolder holder, int position) {
        TopGenreHelperClass topGenreHelperClass = topGenres.get(position);
        holder.image.setImageResource(topGenreHelperClass.getImage());
        holder.title.setText(topGenreHelperClass.getTitle());
        holder.relativeLayout.setBackground(topGenreHelperClass.getGradient());
    }

    @Override
    public int getItemCount() {
        return topGenres.size();
    }

    public static class GenresViewHolder extends RecyclerView.ViewHolder {
        //Variables
        RelativeLayout relativeLayout;
        ImageView image;
        TextView title;

        public GenresViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            relativeLayout = itemView.findViewById(R.id.gradient_background);
            image = itemView.findViewById(R.id.mv_bollywood);
            title = itemView.findViewById(R.id.mv_bollywood_title);
        }
    }
}
