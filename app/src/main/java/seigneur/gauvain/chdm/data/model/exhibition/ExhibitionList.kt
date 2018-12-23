package seigneur.gauvain.chdm.data.model.exhibition

import android.arch.persistence.room.Entity

@Entity
data class ExhibitionList (
    var exhibitions: List<Exhibition>
)

