package seigneur.gauvain.chdm.ui.exhibition.list.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView


import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Optional
import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.ui.exhibition.list.data.NetworkState
import seigneur.gauvain.chdm.ui.exhibition.list.data.Status


class NetworkStateViewHolder(itemView: View, exhibitionItemCallback: ExhibitionItemCallback) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.errorMessageTextView)
    lateinit var errorMessageTextView: TextView

    @BindView(R.id.retryLoadingButton)
    lateinit var retryLoadingButton: Button

    @BindView(R.id.loadingProgressBar)
    lateinit var loadingProgressBar: ProgressBar

    init {
        ButterKnife.bind(this, itemView)
        retryLoadingButton.setOnClickListener { _ -> exhibitionItemCallback.retry() }
    }

    fun bindTo(networkState: NetworkState) {
        //error message
        errorMessageTextView.visibility =
                if (networkState.message.isEmpty())
                    View.VISIBLE
                else
                    View.GONE
        errorMessageTextView.text = networkState.message

        //loading and retry
        retryLoadingButton.visibility = if (networkState.status === Status.FAILED) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (networkState.status === Status.RUNNING) View.VISIBLE else View.GONE
    }

    companion object {

        fun create(parent: ViewGroup, exhibitionItemCallback: ExhibitionItemCallback): NetworkStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_network_state, parent, false)
            return NetworkStateViewHolder(view, exhibitionItemCallback)
        }
    }

}
