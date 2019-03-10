package seigneur.gauvain.chdm.data.model.exhibition

import androidx.room.Entity

@Entity
data class ExhibitionList (
    var exhibitions: List<Exhibition>
)

