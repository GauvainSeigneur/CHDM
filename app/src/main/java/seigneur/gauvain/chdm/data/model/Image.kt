package seigneur.gauvain.chdm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Image (
    @PrimaryKey
    @SerializedName("image_id")
    var image_id: Long,
    @SerializedName("url")
    var url: String? =""
)

