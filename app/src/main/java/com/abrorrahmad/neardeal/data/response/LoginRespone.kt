import com.google.gson.annotations.SerializedName




data class LoginRespone (

	@SerializedName("success") val success : Boolean,
	@SerializedName("user") val user : User
)