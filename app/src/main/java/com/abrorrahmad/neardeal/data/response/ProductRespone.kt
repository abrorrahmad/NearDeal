import com.google.gson.annotations.SerializedName



data class ProductRespone (

	@SerializedName("succes") val succes : Boolean,
	@SerializedName("product") val product : List<Product>
)