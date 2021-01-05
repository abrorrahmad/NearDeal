package com.abrorrahmad.neardeal.adapter

import Store
import android.media.Image
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.abrorrahmad.neardeal.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_store.view.*

class StoreitemAdapter (val list : List<Store>) :
    RecyclerView.Adapter<StoreitemAdapter.StoreViewHolder> () {

    interface OnItemClickListener{
        fun onItemClick(storeId: String)
    }


    private var onItemClickListener : OnItemClickListener? = null

    fun setListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_store, parent, false)

        return StoreViewHolder(view)

    }




    override fun onBindViewHolder(holder: StoreitemAdapter.StoreViewHolder, position: Int) {
        val store = list[position]

        holder.textTitle.text = store.name
        holder.textDeal.text = "Belum ada deal"

        Picasso.get().load(list[position].photo)
                .resize(50, 50).into(holder.image)

        onItemClickListener?.let {
            holder.container.setOnClickListener{
                onItemClickListener!!.onItemClick(list[position].id.toString())
            }
        }



    }


    override fun getItemCount(): Int {
        return list.size
    }


    inner class StoreViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView
        val textTitle : TextView
        val textDeal : TextView
        val container : CardView

        init {
            image = itemView.image
            textTitle = itemView.tvTitle
            textDeal = itemView.tvDeal
            container = itemView.container
        }

    }


}