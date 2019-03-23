package seigneur.gauvain.chdm.ui.objects

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.object_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.ui.base.BaseFragment
import timber.log.Timber

import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.utils.ImageRequestListener
import seigneur.gauvain.chdm.utils.ShakeSensor


class ObjectFragment : BaseFragment(), ShakeSensor.OnShakeListener, ImageRequestListener.Callback {

    private val mObjectViewModel by viewModel<ObjectViewModel>()

    private val mSensorManager:SensorManager? by lazy {
        mParentActivity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val mAccelerometer : Sensor ? by lazy {
        mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    private lateinit var mShakeSensor:ShakeSensor

    /**
     *  From Fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set up shake sensor
        mShakeSensor = ShakeSensor(mSensorManager, mAccelerometer, this)

    }

    /**
     *  From BaseFragment
     */
    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        Timber.d("onCreateView called")
        mObjectViewModel.init()
    }

    /**
     * From BaseFragment
     */
    override val fragmentLayout: Int
        get() = R.layout.object_fragment

    /**
     *  From Fragment
     */
    override fun onResume() {
        super.onResume()
        mShakeSensor.registerListener()
    }

    /**
     *  From Fragment
     */
    override fun  onPause() {
        super.onPause()
        mShakeSensor.unRegisterListener()
    }

    /**
     *  From Fragment
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData
        mObjectViewModel.mResultUiModel.observe(
            this,
            Observer { it ->
                if (it.inChdmObject!=null) {
                    val requestOption = RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .placeholder(R.drawable.ic_dashboard_black_24dp)
                        .error(R.drawable.ic_mtrl_checked_circle)

                    Timber.d("mObject observed ${it.inChdmObject.firstImageUrl}")
                    Glide.with(mParentActivity!!)
                        .load(Uri.parse(it.inChdmObject.firstImageUrl))
                        /*.thumbnail(Glide.with(this)
                            .load(Uri.parse(inObject?.firstImageUrl))
                            .apply(requestOption))*/
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(requestOption)
                        .listener(ImageRequestListener(this))
                        .into(imageObject)
                }
                else if (it.inError!=null) {
                    Toast.makeText(mParentActivity, "oopsy: $it",Toast.LENGTH_LONG).show()
                }

            }

        )
    }

    /**
     * Implementation of ImageRequestListener.Callback
     */
    override fun onLoadImageFailure(message: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    /**
     * Implementation of ImageRequestListener.Callback
     */
    override fun onLoadImageSuccess(dataSource: String) {

    }

    /**
     * ShakeSensor.OnShakeListener
     */
    override fun onShake(count:Int){
        if (count>=1){
            mObjectViewModel.askForRandomObject()
        }
    }

}
