package com.abrorrahmad.neardeal.adapter

import Deal
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrorrahmad.neardeal.R


import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_deal.view.*

import org.joda.time.DateTime
import org.joda.time.Days



import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



class DealItemAdapter(private val mContext: Context, private val dealList: List<Deal>) : RecyclerView.Adapter<DealItemAdapter.MyViewHolder>() {
    private val layoutInflater = LayoutInflater.from(mContext)
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(deal: Deal){
            itemView.tv_name.text = deal.product?.name
            Picasso.get().load(deal.product?.photo).into(itemView.imageView)

            itemView.tv_price_old.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            itemView.tv_price_old.text = deal.product?.price.toString()
            val discount = Integer.parseInt(deal.discount.toString())
            val priceOld = deal.product?.price!!
            val priceNew = (priceOld - priceOld * discount / 100).toDouble()
            itemView.tv_price.text = priceNew.toString()

            val today = Date()
            try {
                val startDate = simpleDateFormat.parse(deal.start_date)
                val endDate = simpleDateFormat.parse(deal.end_date)

                val delta1 = today.time - startDate.time
                val delta2 = today.time - endDate.time

                val delta1InDays = Days.daysBetween(DateTime(today.time), DateTime(startDate.time)).days
                val delta2InDays = Days.daysBetween(DateTime(today.time), DateTime(endDate.time)).days

                if (delta1 < 0) {
                    //holder.tvDate.setText(String.format("Diskon Mulai %d Hari Lagi", Math.abs(delta1InDays)));
                    itemView.tv_date.text = "Diskon Mulai " + Math.abs(delta1InDays) + "Hari Lagi"
                } else if (delta1 >= 0 && delta2 <= 0) {

                    when {
                        delta1InDays == 0 -> {
                            itemView.tv_date.text = "Diskon Mulai Hari ini"
                        }
                        delta2InDays == 0 -> {
                            itemView.tv_date.text = "Diskon Berakhir Hari ini"
                        }
                        else -> {
                            itemView.tv_date.text = "Diskon Berakhir " + Math.abs(delta2InDays + 1) + "Hari Lagi"

                        }
                    }

                    itemView.tv_date.text = "Diskon Berakhir " + Math.abs(delta2InDays + 1) + "Hari Lagi"

                } else {
                    itemView.tv_date.text = "Diskon Berakhir " + Math.abs(delta2InDays + 1) + "Hari Lagi"
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(layoutInflater.inflate(R.layout.item_deal, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val deal = dealList[position]
        holder.bind(deal)
    }

    override fun getItemCount(): Int {
        return dealList.size
    }
}