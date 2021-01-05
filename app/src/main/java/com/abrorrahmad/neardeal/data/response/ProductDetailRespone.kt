import com.google.gson.annotations.SerializedName




data class ProductDetailRespone (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String?,
	@SerializedName("photo") val photo : String?,
	@SerializedName("store_name") val store_name : String?,
	@SerializedName("store_photo") val store_photo : String?,
	@SerializedName("price") val price : Int,
	@SerializedName("price_old") val price_old : Int,
	@SerializedName("description") val description : String?
)