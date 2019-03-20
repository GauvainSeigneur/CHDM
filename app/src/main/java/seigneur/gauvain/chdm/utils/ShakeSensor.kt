package seigneur.gauvain.chdm.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import timber.log.Timber

class ShakeSensor(private var mSensorManager:SensorManager?,
                  private var mAccelerometer : Sensor?,
                  private var mOnShakeListener:OnShakeListener?) : SensorEventListener {

    private var mShakeTimestamp: Long = 0
    private var mShakeCount: Int = 0

    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val gX = x / SensorManager.GRAVITY_EARTH
                val gY = y / SensorManager.GRAVITY_EARTH
                val gZ = z / SensorManager.GRAVITY_EARTH

                // gForce will be close to 1 when there is no movement.
                val lol :Double = (gX * gX + gY  * gY  + gZ * gZ).toDouble()
                val gForce = Math.sqrt(lol).toFloat()

                if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                    val now = System.currentTimeMillis()
                    // ignore shake events too close to each other (500ms)
                    if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                        return
                    }

                    // reset the shake count after SHAKE_COUNT_RESET_TIME_MS of no shakes
                    if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                        mShakeCount = 0
                    }

                    mShakeTimestamp = now
                    mShakeCount++

                    Timber.d("on shake $mShakeCount")
                    mOnShakeListener?.onShake(mShakeCount)

                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Timber.d("onAccuracyChanged")
    }

    fun registerListener() {
        mSensorManager?.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unRegisterListener() {
        mSensorManager?.unregisterListener(this)
    }

    interface OnShakeListener {

        fun onShake(count:Int)

    }

    companion object {
        const val SHAKE_THRESHOLD_GRAVITY       = 2.7f
        const val SHAKE_SLOP_TIME_MS            = 500
        const val SHAKE_COUNT_RESET_TIME_MS     = 1000
    }
}