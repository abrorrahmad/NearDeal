import com.google.gson.annotations.SerializedName




data class StoreRespone (

	@SerializedName("succes") val succes : Boolean,
	@SerializedName("store") val store : List<Store>
)