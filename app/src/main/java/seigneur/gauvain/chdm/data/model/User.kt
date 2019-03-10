package seigneur.gauvain.chdm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class User (
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    var name: String
)

