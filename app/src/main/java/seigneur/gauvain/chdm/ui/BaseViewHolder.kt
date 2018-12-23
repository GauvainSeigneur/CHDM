package seigneur.gauvain.chdm.ui

import android.support.v7.widget.RecyclerView
import android.view.View

import butterknife.ButterKnife

/**
 * Base view holder for RecyclerView. Allows to bind views easily
 */
open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        ButterKnife.bind(this, itemView)
    }
}
