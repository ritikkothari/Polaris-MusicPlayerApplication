package com.example.polaris.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polaris.R;

import java.util.ArrayList;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    ArrayList<ArtistHelperClass> artistCollection;

    public ArtistAdapter(ArrayList<ArtistHelperClass> artistHelperClass) {
        this.artistCollection = artistHelperClass;
    }

    public static  class ArtistViewHolder extends RecyclerView.ViewHolder {
        //Variables
        ImageView image;
        TextView title, description;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            image = itemView.findViewById(R.id.artist_image);
            title = itemView.findViewById(R.id.artist_name);
            description = itemView.findViewById(R.id.artist_description);
        }
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artists_card_design, parent, false);
        ArtistViewHolder artistViewHolder = new ArtistViewHolder(view);
        return artistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        ArtistHelperClass artistHelperClass = artistCollection.get(position);
        holder.image.setImageResource(artistHelperClass.getImage());
        holder.title.setText(artistHelperClass.getTitle());
        holder.description.setText(artistHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return artistCollection.size();
    }
}
