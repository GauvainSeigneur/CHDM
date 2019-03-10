package seigneur.gauvain.chdm.data.model.chdmObject

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import seigneur.gauvain.chdm.data.model.Image
import timber.log.Timber
import java.util.ArrayList


@Entity
data class ChdmObject (
    @PrimaryKey
    @SerializedName("id")
    var id: Long,
    @SerializedName("images")
    var imgList: ArrayList<Map<String, Image>>
) {

    /**
     * get first available (non-empty) image url from the list of Images of the current CHDM object
     */
    val firstImageUrl: String?
        get() {
            var url: String? = null
            if (!imagesOfObject.isEmpty()) {
                //add label loop@ to indicates to compiler that this method is a loop in order to break it when we want
                //see https://kotlinlang.org/docs/reference/returns.html to check labels in Kotlin
                loop@ for (i in 0 until imgList.size) {
                    if (!imagesOfObject[i].url.isNullOrEmpty())
                        url = imagesOfObject[i].url
                    //break the loop when we found a non-empty value for URL
                    break@loop
                }
            }
            return url
        }

    /**
     * Convert <Map<String, Image> of image received into a list of the Value (Image)
     */
    private val imagesOfObject:ArrayList<Image>
        get() {
            var valueList =ArrayList<Image>()
            if (!imgList.isEmpty()) {
                for (i in 0 until imgList.size) {
                    valueList = ArrayList(imgList[i].values)
                }
            }
            Timber.d("valueList: " + valueList)
            return valueList
        }

    companion object {
        //useless since we convert the map into a list
        val IMAGE_O     = "o"
        val IMAGE_B     = "b"
        val IMAGE_Z     = "z"
        val IMAGE_N     = "n"
        val IMAGE_D     = "d"
        val IMAGE_SQ    = "sq"
    }
}


