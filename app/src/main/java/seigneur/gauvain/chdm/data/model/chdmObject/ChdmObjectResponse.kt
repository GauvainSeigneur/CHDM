package seigneur.gauvain.chdm.data.model.chdmObject

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class ChdmObjectResponse(
     @SerializedName("object")
     var theObject: ChdmObject
)




