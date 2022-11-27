package com.example.kotlinexamproject.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinexamproject.R
import com.example.kotlinexamproject.databinding.FragmentCryptocurrenciesListItemsBinding
import com.example.kotlinexamproject.db.entities.CryptocurrencyEntity
import com.example.kotlinexamproject.models.CryptocurrencyModel
import com.example.kotlinexamproject.ui.MainActivity

class CryptocurrencyAdapter(private var cryptocurrencies: List<CryptocurrencyEntity>) :
    RecyclerView.Adapter<CryptocurrencyAdapter.CryptocurrenciesViewHolder>() {

    class CryptocurrenciesViewHolder(var binding: FragmentCryptocurrenciesListItemsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrenciesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            FragmentCryptocurrenciesListItemsBinding.inflate(layoutInflater, parent, false)

        return CryptocurrenciesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptocurrenciesViewHolder, position: Int) {
        var currentCryptocurrency = cryptocurrencies[position]

        holder.binding.apply {
            name = currentCryptocurrency.name
            symbol = currentCryptocurrency.symbol
            price = "Price: ${currentCryptocurrency.current_price} (USD)"
            
            Glide
                // context to use for requesting the image
                .with(root.context)
                // URL to load the asset from
                .load(currentCryptocurrency.image)
                .centerCrop()
                // image to be shown until online asset is downloaded
                .placeholder(R.drawable.no_image_avavailable)
                // ImageView to load the online asset into when ready
                .into(cryptocurrencyFlag)

            if (currentCryptocurrency.isFavourite) {
                favoriteIcon.isVisible = true
            }
        }

        holder.binding.root.setOnClickListener {
            val data = Bundle()
            data.putString("cryptocurrencyName", currentCryptocurrency.name)
            it.findNavController().navigate(R.id.action_firstFragment_to_secondFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return cryptocurrencies.size
    }


}