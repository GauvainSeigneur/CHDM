package seigneur.gauvain.chdm.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition

class CurrentExhibViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val mCurrentExhibImage = itemView.findViewById(R.id.mCurrentExhibImage) as ImageView


    fun bindTo(exhibition: Exhibition?) {
        //image
        Glide.with(itemView.context)
                .load(exhibition?.illustrationUrl)
                .into(mCurrentExhibImage)

    }


    companion object {
        fun create(parent: ViewGroup): CurrentExhibViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_current_exhib, parent, false)
            return CurrentExhibViewHolder(view)
        }
    }

}
