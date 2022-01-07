package com.example.dogbreedshowcase.listadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreedshowcase.R
import com.example.dogbreedshowcase.model.DogImage
import com.squareup.picasso.Picasso

class DogImageAdapter(private val context: Context, private val dogBreedImages: List<DogImage>
) : RecyclerView.Adapter<DogImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.gallery_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dogBreedImages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dogBreedImages[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dogBreedImage = itemView.findViewById<ImageView>(R.id.dogBreedImage)

        fun bind(image: DogImage) {
            Picasso.get().load(image.imageUrl).into(dogBreedImage)
        }
    }


}