package com.ajith.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter  extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder>
{
    private ArrayList<CategoryRVModal> categoryRVModals;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    /**
     *
     * ArrayList of Categories is stored in the list . each category of news is an item in the recycler view
     *
     * An interface is here created to accomplish the feature of changeing the news content based onthe
     * clicking of  the categories
     *
     * */
    public CategoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModals, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModals = categoryRVModals;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }


    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the layout file

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv,parent,false);
        return  new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                CategoryRVModal categoryRVModal = categoryRVModals.get(position);
                holder.categoryTV.setText(categoryRVModal.getCategory());


                Picasso.get().load(categoryRVModal.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(holder.getAdapterPosition());
            }
        });

    }

//    @SuppressLint("RecyclerView")
//    @Override
//    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position ) {
//        //for fillingt the content into the view
//        CategoryRVModal categoryRVModal = categoryRVModals.get(position);
//        holder.categoryTV.setText(categoryRVModal.getCategory());
//        Picasso.get().load(categoryRVModal.getCategoryImageUrl()).into(holder.categoryIV);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                categoryClickInterface.onCategoryClick(position);
//            }
//        });
//
//    }

    @Override
    public int getItemCount() {
        return categoryRVModals.size();
    }

    public interface CategoryClickInterface
    {
        void onCategoryClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView categoryTV;
        ImageView categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
