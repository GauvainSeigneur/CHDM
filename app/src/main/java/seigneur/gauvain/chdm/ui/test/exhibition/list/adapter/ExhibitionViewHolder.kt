package seigneur.gauvain.chdm.ui.test.exhibition.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition

class ExhibitionViewHolder private constructor(itemView: View, private val mExhibitionItemCallback: ExhibitionItemCallback) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val title = itemView.findViewById(R.id.shot_gif_label) as TextView
    val image = itemView.findViewById(R.id.shot_image) as ImageView

    init {
        image.setOnClickListener(this)
    }

    fun bindTo(exhibition: Exhibition) {
        //title
        title.text=exhibition.title
        //image
        Glide.with(itemView.context)
                .load(exhibition.illustrationUrl)
                .into(image)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.shot_image -> mExhibitionItemCallback.onItemclicked(adapterPosition)
        }
    }

    companion object {
        fun create(parent: ViewGroup, exhibitionItemCallback: ExhibitionItemCallback): ExhibitionViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_exhibition, parent, false)
            return ExhibitionViewHolder(view, exhibitionItemCallback)
        }
    }

}
