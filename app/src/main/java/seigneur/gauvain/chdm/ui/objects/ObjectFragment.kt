package seigneur.gauvain.chdm.ui.objects

import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeTransition
import kotlinx.android.synthetic.main.object_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import seigneur.gauvain.chdm.ui.base.BaseFragment
import timber.log.Timber

import seigneur.gauvain.chdm.R
import seigneur.gauvain.chdm.utils.ShakeSensor


class ObjectFragment : BaseFragment(), ShakeSensor.OnShakeListener, MyImageRequestListener.Callback {

    private val mObjectViewModel by viewModel<ObjectViewModel>()

    lateinit var mSensorManager:SensorManager
    private var mAccelerometer : Sensor ?= null

    private lateinit var mShakeSensor:ShakeSensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate called")
        //from https://expertise.jetruby.com/how-to-implement-motion-sensor-in-a-kotlin-app-b70db1b5b8e5
        //https://medium.com/@enzoftware/lets-play-with-the-android-accelerometer-kotlin-%EF%B8%8F-ed92981b0a6c
        mSensorManager = mParentActivity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // focus in accelerometer
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        //set up shake sensor
        mShakeSensor = ShakeSensor(mSensorManager, mAccelerometer, this)

    }

    override fun onFailure(message: String?) {
        Toast.makeText(mParentActivity, "Fail to load: $message", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(dataSource: String) {
        Toast.makeText(mParentActivity, "Loaded from: $dataSource", Toast.LENGTH_SHORT).show()
    }

    override fun onShake(count:Int){
        Timber.d("onCreateView called")
        if (count>=1){
            mObjectViewModel.askForRandomObject()
        }
    }

    override fun onCreateView(inRootView: View, inSavedInstanceState: Bundle?) {
        Timber.d("onCreateView called")
        mObjectViewModel.init()
    }

    override fun onResume() {
        super.onResume()
        mShakeSensor.registerListener()
    }


    override fun  onPause() {
        super.onPause()
        mShakeSensor.unRegisterListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listen LiveData
        mObjectViewModel.mObject.observe(
            this,
            Observer { inObject ->

                val requestOption = RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .placeholder(R.drawable.ic_dashboard_black_24dp)
                    .error(R.drawable.ic_mtrl_checked_circle)

                Timber.d("mObject observed ${inObject?.firstImageUrl}")
                Glide.with(mParentActivity!!)
                    .load(Uri.parse(inObject?.firstImageUrl))
                    /*.thumbnail(Glide.with(this)
                        .load(Uri.parse(inObject?.firstImageUrl))
                        .apply(requestOption))*/
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOption)
                    .listener(MyImageRequestListener(this))
                    .into(imageObject)

            }

        )
    }



    /**
     * From BaseFragment
     */
    override val fragmentLayout: Int
        get() = R.layout.object_fragment

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f
        private const val SHAKE_SLOP_TIME_MS = 500
        private const val SHAKE_COUNT_RESET_TIME_MS = 1000
    }

}
