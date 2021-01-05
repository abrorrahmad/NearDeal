import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Store (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("lat") val lat : Double,
	@SerializedName("lng") val lng : Double,
	@SerializedName("photo") val photo : String,
	@SerializedName("telp") val telp : String,
	@SerializedName("description") val description : String,
	@SerializedName("open_hour") val open_hour : String,
	@SerializedName("address") val address : String,
	@SerializedName("created_at") val created_at : String?
) : Parcelable