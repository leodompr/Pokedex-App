package com.leonardo.pokedexapp.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leonardo.pokedexapp.R
import com.leonardo.pokedexapp.model.PokemonDaoModel

class FavoritesPokemonAdapter(private val onItemClick: (PokemonDaoModel) -> Unit) :
    RecyclerView.Adapter<FavoritesPokemonAdapter.ViewHolder>() {
    var itemList: List<PokemonDaoModel> = listOf()


    fun setDataSet(item: List<PokemonDaoModel>) {
        this.itemList = item

    }

    fun filterList(qrSearch: MutableList<PokemonDaoModel>) {
        this.itemList = qrSearch
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun linkItem(onItemClick: (PokemonDaoModel) -> Unit, item: PokemonDaoModel) {

            itemView.setOnClickListener {
                onItemClick(item)
            }

            val txtNamePokemon: TextView = itemView.findViewById(R.id.tv_pokemon_name_item)
            txtNamePokemon.text = item.name

            val tvPokemonType: TextView = itemView.findViewById(R.id.tv_pokemon_type_item)
            tvPokemonType.text = item.type

            val cardColorItem: CardView = itemView.findViewById(R.id.cvBackgroundColor)
            cardColorItem.setCardBackgroundColor((Color.parseColor(item.color)))


            val imgPokemon: ImageView = itemView.findViewById(R.id.imV_pokemon_item)
            Glide.with(imgPokemon)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPokemon)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pokemon_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.linkItem(onItemClick, item)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}