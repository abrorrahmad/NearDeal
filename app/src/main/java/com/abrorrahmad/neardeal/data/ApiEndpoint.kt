package com.abrorrahmad.neardeal.data

import DealRespone
import LoginRespone
import ProductDetailRespone
import ProductRespone
import StoreRespone
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint{

    @GET("get_store.php")
    fun getListStore( @Query("lat") lat : Double, @Query("lng") lng : Double) : Call<StoreRespone>


    @GET("get_product.php")
    fun getProduct(@Query("store_id") store_id: String) : Call<ProductRespone>

    @GET("get_deal.php")
    fun getDeal(@Query("store_id") store_id: String) : Call<DealRespone>

    @GET("get_product_detail.php")
    fun getProductDetail(@Query("product_id") product_id : String): Call<ProductDetailRespone>

    @FormUrlEncoded
    @POST("login.php")
    fun login(@Field("username") username: String,
              @Field("password") password: String): Call<LoginRespone>

}