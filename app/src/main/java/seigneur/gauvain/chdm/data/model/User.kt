package seigneur.gauvain.chdm.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class User (
    @PrimaryKey
    @SerializedName("id")
    var id: Int,
    var name: String
)

