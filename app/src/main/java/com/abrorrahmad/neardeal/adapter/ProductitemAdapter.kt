package com.abrorrahmad.neardeal.adapter

import Product
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.abrorrahmad.neardeal.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_product.view.*

class ProductitemAdapter(private  var listProduct : List<Product>) :
RecyclerView.Adapter<ProductitemAdapter.ProductViewHolder>(){

    interface OnItemClickListenerProductAdapter{
        fun onItemClickProduct(storeId: String)
    }


    private var onItemClickListener : OnItemClickListenerProductAdapter? = null

    fun setListener(listener: OnItemClickListenerProductAdapter){
        this.onItemClickListener = listener
    }


    //View Holder
    inner class  ProductViewHolder(val view: View) : RecyclerView.ViewHolder(view){

        val context:CardView = view.cv_adapter_product

        fun bind(product: Product){
            view.tvProductName.text = product.name
            view.tvprice.text = "Rp. ${product.price}"

            Picasso.get().load(product.photo).into(view.imageView)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.item_product, parent, false)

        val viewHolder = ProductViewHolder(view)

        return viewHolder
    }

    override fun getItemCount(): Int {

        return listProduct.size

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {


        val oneProduct = listProduct[position]

        holder.bind(oneProduct)

        if (onItemClickListener != null ){
            holder.context.setOnClickListener {
                onItemClickListener!!.onItemClickProduct(listProduct[position].id.toString())
            }
        }

    }




}