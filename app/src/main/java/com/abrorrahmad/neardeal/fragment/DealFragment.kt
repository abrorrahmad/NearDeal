package com.abrorrahmad.neardeal.fragment

import Deal
import com.abrorrahmad.neardeal.adapter.DealItemAdapter
import DealRespone
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.abrorrahmad.neardeal.ProductActivity
import com.abrorrahmad.neardeal.R
import com.abrorrahmad.neardeal.data.ApiClient
import com.abrorrahmad.neardeal.data.ApiEndpoint

import com.abrorrahmad.neardeal.util.PopupUtil
import kotlinx.android.synthetic.main.fragment_deal.*
import retrofit2.Call
import retrofit2.Response


class DealFragment : Fragment() {

    private var storeId: String? = null
    private  lateinit var dealList : ArrayList<Deal>
    private lateinit var adapter : DealItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storeId = arguments?.getString(ProductActivity.STORE_ID)
        println(storeId)
        return inflater.inflate(R.layout.fragment_deal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_deal.layoutManager = GridLayoutManager(view.context, 2)

        dealList = ArrayList()
        adapter = DealItemAdapter(view.context, dealList)

        rv_deal.adapter = adapter

        loadDeals()
    }

    private fun loadDeals() {

        val apiEndPoint: ApiEndpoint = ApiClient.getClient(requireContext())!!.create(ApiEndpoint::class.java)
        val call: retrofit2.Call<DealRespone> = apiEndPoint.getDeal(storeId!!)

        call.enqueue(object :retrofit2.Callback<DealRespone?>{
            override fun onResponse(call: Call<DealRespone?>, response: Response<DealRespone?>) {
                PopupUtil.dismissDialog()

                if (response.body() == null){
                    Toast.makeText(activity,"No Response", Toast.LENGTH_SHORT)
                    return
                }
                if (response.isSuccessful()) {
                    val dealRespone: DealRespone? = response.body()
                    Toast.makeText(getActivity(), "jumlah product ada "+response.body()!!.deal.size, Toast.LENGTH_SHORT).show();
                    dealList.addAll(dealRespone!!.deal)
                    adapter.notifyDataSetChanged()

                }
            }


            override fun onFailure(call: Call<DealRespone?>, t: Throwable) {
                PopupUtil.dismissDialog()
                Toast.makeText(activity, "gagal", Toast.LENGTH_SHORT).show()

            }


        })




    }
}