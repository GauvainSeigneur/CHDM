package seigneur.gauvain.chdm.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Image (
    @PrimaryKey
    @SerializedName("image_id")
    var image_id: Long,
    @SerializedName("url")
    var url: String? =""
)

