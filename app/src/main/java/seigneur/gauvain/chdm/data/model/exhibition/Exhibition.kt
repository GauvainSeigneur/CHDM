package seigneur.gauvain.chdm.data.model.exhibition

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import seigneur.gauvain.chdm.data.model.chdmObject.ChdmObject
import java.util.*

@Entity
data class Exhibition (
    //API
    @PrimaryKey
    @SerializedName("id")
    var id: Long,
    @SerializedName("url")
    var url: String,
    var title: String,
    var text: String,
    @SerializedName("date_start")
    var date_start: Date,
    @SerializedName("date_end")
    var date_end: Date,
    @SerializedName("is_active")
    var is_active:Boolean,
    //INNER VAR FOR APP
    var exhibObjects: ExhibitionObjects,
    var illustrationUrl:String?=""
) {
    //A class may be marked as inner to be able to access members of outer class.
    //Inner classes carry a reference to an object of an outer class
    inner class ExhibitionObjects {
       val objects=ArrayList<ChdmObject>()
    }
    //todo - make a method to get first non null object from exhibObjects: ChdmObjectList
}

