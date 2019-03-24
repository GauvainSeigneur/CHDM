package seigneur.gauvain.chdm.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.data.model.exhibition.Exhibition


class CurrentExhibListAdapter(private val mContext: Context?,
                              private var mData: List<Exhibition>)
    : RecyclerView.Adapter<CurrentExhibViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentExhibViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_current_exhib, parent, false)
        return CurrentExhibViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrentExhibViewHolder, position: Int) {
        val item = mData[position]
        holder.bindTo(item)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    /**
     *
     */
    fun addCurrentExhibitions(inList: List<Exhibition>) {
        mData = inList
        notifyDataSetChanged()
    }

}
