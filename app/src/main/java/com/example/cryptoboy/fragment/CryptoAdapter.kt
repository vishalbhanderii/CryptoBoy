package com.example.cryptoboy.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoboy.R
import com.example.cryptoboy.databinding.CryptoItemLayoutBinding
import com.example.cryptoboy.utils.Crypto

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.Viewholder>() {

    var data = mapOf<String, String?>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val crypto = Crypto.values()[position]
        val price = data[crypto.id]

        holder.update(crypto, price)
    }

    override fun getItemCount(): Int {
        return Crypto.values().size
    }


    class Viewholder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val binding = CryptoItemLayoutBinding.bind(itemView)

        fun update(crypto: Crypto, price: String?) {
            binding.code.text = crypto.code
            binding.name.text = crypto.friendlyName
            binding.price.text = price?.let {
                "$ $it"
            }
        }

        companion object {
            fun newInstance(parent: ViewGroup): Viewholder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.crypto_item_layout, parent, false)

                return Viewholder(view)
            }
        }
    }
}