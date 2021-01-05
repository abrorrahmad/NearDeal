package com.abrorrahmad.neardeal.fragment

import Product
import ProductRespone
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.abrorrahmad.neardeal.ProductActivity
import com.abrorrahmad.neardeal.ProductDetailActivity
import com.abrorrahmad.neardeal.R
import com.abrorrahmad.neardeal.adapter.ProductitemAdapter
import com.abrorrahmad.neardeal.data.ApiClient
import com.abrorrahmad.neardeal.data.ApiEndpoint
import com.abrorrahmad.neardeal.util.PopupUtil
import kotlinx.android.synthetic.main.fragment_product.*
import retrofit2.Response


class ProductFragment : Fragment(), ProductitemAdapter.OnItemClickListenerProductAdapter {

    private var storeId : String? = null
    private lateinit var  productList: ArrayList<Product>
    private lateinit var adapter : ProductitemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        storeId = arguments?.getString(ProductActivity.STORE_ID)
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(activity, 2)
        rf_produk.layoutManager = gridLayoutManager

        productList = ArrayList()
        adapter = ProductitemAdapter(productList)

        rf_produk.adapter = adapter
        adapter.setListener(this)
        loadProdduct()
    }



    private fun loadProdduct() {


        PopupUtil.showLoading(activity, "Loading", "Please Wait")
        val apiEndPoint: ApiEndpoint = ApiClient.getClient(requireContext())!!.create(ApiEndpoint::class.java)
        val call: retrofit2.Call<ProductRespone> = apiEndPoint.getProduct(storeId!!)
//        val call2: Call<ResponseBody> = apiEndPoint.getProductTes(storeId!!)

        call.enqueue(object : retrofit2.Callback<ProductRespone>{
            override fun onResponse(call: retrofit2.Call<ProductRespone>, response: Response<ProductRespone?>) {

                PopupUtil.dismissDialog()
                if (response == null){
                    Toast.makeText(activity, "No Response", Toast.LENGTH_SHORT)
                    return
                }
                if (response.isSuccessful()){
                    val storeRespone: ProductRespone? = response.body()
                    Toast.makeText(getActivity(), "jumlah product ada "+response.body()!!.product.size, Toast.LENGTH_SHORT).show();
                   productList.addAll(storeRespone!!.product)
                    adapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: retrofit2.Call<ProductRespone>, t: Throwable) {
                PopupUtil.dismissDialog()
                Toast.makeText(activity, "gagal", Toast.LENGTH_SHORT).show()
                Log.d(ProductFragment::class.java.simpleName , t.toString())

            }

        })

    }

    override fun onItemClickProduct(productId: String) {
        val intent = Intent(activity, ProductDetailActivity::class.java)
        intent.putExtra(ProductDetailActivity.KEY_PRODUCT_ID, productId)
        startActivity(intent)
    }

}
