package zlc.season.rxdownload.demo

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import zlc.season.rxdownload4.utils.log
import java.lang.reflect.Constructor
import java.lang.reflect.Method


class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            try {
                val legalityClass = Class.forName("com.lcq.legal.Legality")
                val method = legalityClass.getDeclaredMethod("check")
                val constructor = legalityClass.getDeclaredConstructor(Application::class.java).newInstance(this);
                method.isAccessible = true
                method.invoke(constructor)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        RxJavaPlugins.setErrorHandler {
            if (it is UndeliverableException) {
                //do nothing
            } else {
                it.log()
            }
        }
    }

    companion object {
        private const val TAG = "wdndm"
    }
}