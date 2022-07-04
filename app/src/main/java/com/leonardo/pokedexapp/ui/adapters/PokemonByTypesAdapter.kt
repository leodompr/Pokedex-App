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
import com.leonardo.pokedexapp.model.PokemonUiModel

class PokemonByTypesAdapter(private val onItemClick: (PokemonUiModel) -> Unit) :
    RecyclerView.Adapter<PokemonByTypesAdapter.ViewHolder>() {
    var itemList: List<PokemonUiModel> = listOf()
    var color : String = "#FFFFFF"


    fun setDataSet(item: List<PokemonUiModel>, color: String) { //Alimenta a RecyclerView
        this.itemList = item
        this.color = color
    }

    fun filterList(qrSearch: MutableList<PokemonUiModel>) {
        this.itemList = qrSearch
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun linkItem(onItemClick: (PokemonUiModel) -> Unit, item: PokemonUiModel, color: String) {

            itemView.setOnClickListener {
                onItemClick(item)
            }

            val txtNamePokemon: TextView = itemView.findViewById(R.id.tv_pokemon_name_item)
            txtNamePokemon.text = item.name

            val cardColorItem: CardView = itemView.findViewById(R.id.cvBackgroundColor)
            cardColorItem.setCardBackgroundColor((Color.parseColor(color)))

            val imgPokemon: ImageView = itemView.findViewById(R.id.imV_pokemon_item)
            Glide.with(imgPokemon)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPokemon)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.pokemon_by_type_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.linkItem(onItemClick, item, color)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}