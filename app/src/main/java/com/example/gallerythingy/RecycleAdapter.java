package com.example.gallerythingy;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private ArrayList<Uri> uriArrayList;

    public RecycleAdapter(ArrayList<Uri> uriArrayList, MainActivity itemClickListener) {
        this.uriArrayList = uriArrayList;
        this.itemClickListener = itemClickListener;
    }

    private itemClickListener itemClickListener;
    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_single_image,parent,false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        holder.imageView.setImageURI(uriArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        itemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView, itemClickListener itemClickListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemClickListener != null)
            {
                itemClickListener.itemclick(getAdapterPosition());
            }
        }
    }



    public interface itemDelete {
        void itemdelete(int position);
    }
    public interface itemClickListener {
        void itemclick(int position);

        void clicked(int getSize);
    }

}