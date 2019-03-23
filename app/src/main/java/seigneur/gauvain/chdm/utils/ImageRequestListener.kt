package seigneur.gauvain.chdm.utils


import android.graphics.drawable.Drawable
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition


class ImageRequestListener(private val callback: Callback? = null) : RequestListener<Drawable> {

    interface Callback {
        fun onLoadImageFailure(message: String?)
        fun onLoadImageSuccess(dataSource: String)
    }

    override fun onLoadFailed(e: GlideException?,
                              model: Any,
                              target: Target<Drawable>,
                              isFirstResource: Boolean): Boolean {

        callback?.onLoadImageFailure(e?.message)
        return false
    }

    override fun onResourceReady(resource: Drawable,
                                 model: Any,
                                 target: Target<Drawable>,
                                 dataSource: DataSource,
                                 isFirstResource: Boolean): Boolean {

        callback?.onLoadImageSuccess(dataSource.toString())
        target.onResourceReady(resource, DrawableCrossFadeTransition(1000, isFirstResource))
        return true
    }
}