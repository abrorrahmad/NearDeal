package com.abrorrahmad.neardeal

import ProductDetailRespone
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.abrorrahmad.neardeal.data.ApiClient
import com.abrorrahmad.neardeal.data.ApiEndpoint
import com.abrorrahmad.neardeal.models.Cart
import com.abrorrahmad.neardeal.util.PopupUtil
import com.squareup.picasso.Picasso
import io.realm.Realm
import io.realm.Sort
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.fragment_store.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailActivity : AppCompatActivity() {
    companion object {
        val KEY_PRODUCT_ID = "key_product_id"
    }

    var detailRespone: ProductDetailRespone? = null
    var productId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        btn_buy.setOnClickListener {
            buyProduct()
        }

        productId = intent.getStringExtra(KEY_PRODUCT_ID)
        loadProductDetail()
    }

    private fun buyProduct() {
        val realm = Realm.getDefaultInstance()
        // get product detail
        val name = detailRespone!!.name
        val price = java.lang.Double.parseDouble(detailRespone!!.price.toString())
        val photo = detailRespone!!.photo

        realm.executeTransaction(object : Realm.Transaction {
            override fun execute(realm: Realm) {
                // find last id
                val carts = realm.where(Cart::class.java)
                    .findAll()

                var lastId = 0

                if (carts.size > 0) {
                    lastId = carts.first()!!.id
                }

                val cart = Cart()
                cart.id = lastId + 1
                cart.productId = productId
                cart.productName = name
                cart.price = price
                cart.photo = photo

                realm.copyToRealm(cart)

                PopupUtil.showMsg(
                    this@ProductDetailActivity, "Berhasil ditambahkan ke keranjang belanja",
                    PopupUtil.SHORT
                )
            }
        })

        realm.close()

    }


    private fun loadProductDetail() {

        PopupUtil.showLoading(this, "", "Loading product detail....")

        val apiEndPoint = ApiClient.getClient(this)!!.create(ApiEndpoint::class.java)
        val call = apiEndPoint!!.getProductDetail(productId!!)

        call.enqueue(object : Callback<ProductDetailRespone> {
            override fun onResponse(
                call: Call<ProductDetailRespone>,
                response: Response<ProductDetailRespone>
            ) {
                PopupUtil.dismissDialog()
                val detailResponse = response.body()

                if (detailResponse != null) {
                    Picasso.get().load(detailResponse.photo).into(image_view)
                    Picasso.get().load(detailResponse.store_photo).into(profile_image)

                    tv_store_name.text = detailResponse.store_name
                    tv_description.text = detailResponse.description
                    tv_price_old.text = detailResponse.price_old.toString()
                    tv_price.text = detailRespone?.price.toString()

                    tv_price_old.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    title = detailResponse.name

                    this@ProductDetailActivity.detailRespone = detailRespone
                }
            }

            override fun onFailure(call: Call<ProductDetailRespone>, t: Throwable) {
                PopupUtil.dismissDialog()

            }
        })

    }


}