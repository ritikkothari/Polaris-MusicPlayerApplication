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

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    ArrayList<CategoriesHelperClass> categories;

    public CategoriesAdapter(ArrayList<CategoriesHelperClass> categories) {
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_carddesign, parent, false);
        CategoriesViewHolder categoriesViewHolder = new CategoriesViewHolder(view);
        return categoriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoriesHelperClass categoriesHelperClass = categories.get(position);
        holder.picture.setImageResource(categoriesHelperClass.getPicture());
        holder.desc.setText(categoriesHelperClass.getDesc());
        holder.relativeLayout.setBackground(categoriesHelperClass.getGradient());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        //Variables
        RelativeLayout relativeLayout;
        ImageView picture;
        TextView desc;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            relativeLayout = itemView.findViewById(R.id.background_gradient);
            picture = itemView.findViewById(R.id.ct_workout);
            desc = itemView.findViewById(R.id.ct_workout_title);
        }
    }
}
