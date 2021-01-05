import com.google.gson.annotations.SerializedName




data class Deal (

		@SerializedName("id") val id : Int,
		@SerializedName("start_date") val start_date : String?,
		@SerializedName("end_date") val end_date : String?,
		@SerializedName("discount") val discount : Int,
		@SerializedName("product") val product : Product
)