package com.example.tripmaps.ui.carousel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tripmaps.R

class CarouselAdapter(val data : ArrayList<String>, val context : Context) : RecyclerView.Adapter<CarouselAdapter.VH>() {
    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image : ImageView = itemView.findViewById(R.id.carouselImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.carousel_one_item, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(data[position]).into(holder.image)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}