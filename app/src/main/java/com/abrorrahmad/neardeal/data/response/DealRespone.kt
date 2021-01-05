import com.google.gson.annotations.SerializedName




data class DealRespone (

		@SerializedName("success") val success : Boolean,
		@SerializedName("deal") val deal : List<Deal>
)