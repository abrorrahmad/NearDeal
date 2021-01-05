import com.google.gson.annotations.SerializedName




data class Product (

	@SerializedName("id") val id : Int,
	@SerializedName("store_id") val store_id : Int,
	@SerializedName("name") val name : String?,
	@SerializedName("price") val price : Int,
	@SerializedName("description") val description : String?,
	@SerializedName("photo") val photo : String?
)