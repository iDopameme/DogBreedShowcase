package com.example.dogbreedshowcase.listadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreedshowcase.R
import com.example.dogbreedshowcase.model.DogBreeds
import com.example.dogbreedshowcase.model.DogImage
import com.example.dogbreedshowcase.model.Favorites
import com.squareup.picasso.Picasso

class DogBreedAdapter(private val context: Context,
                      private val dogBreeds: List<DogBreeds>,
                      private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<DogBreedAdapter.ViewHolder>() {

    interface ItemClickListener {
        fun onItemClick(dogBreeds: DogBreeds)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.main_dog_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dogBreeds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dogBreeds[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dogBreedName = itemView.findViewById<TextView>(R.id.dog_breed_name)

        fun bind(dogBreeds: DogBreeds) {
            dogBreedName.text = dogBreeds.breed
            itemView.setOnClickListener {
                itemClickListener.onItemClick(dogBreeds)
            }
        }
    }
}