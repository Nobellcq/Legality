package com.lcq.legal

import android.app.Application
import android.util.Log
import com.swift.sandhook.xposedcompat.XposedCompat
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class Legality(private val application: Application) {


//    一定情况出现，failed to open /lib64/libart.so， 所以崩溃注意
//    这里再传入一个时机，用以判断是否是用户之前，
    fun check() {
        if (!inited) {
            inited = true
            Log.e("wdnmd", "check: instance success")
            XposedCompat.cacheDir = application.getCacheDir();
            XposedCompat.context = application.applicationContext
            XposedCompat.classLoader = application.classLoader
            XposedCompat.isFirstApplication = true
        }

        try {
            val targetClass = Class.forName("okhttp3.OkHttpClient")
            val targetMethod = "newCall"
            val targetParam = Class.forName("okhttp3.Request")
            XposedHelpers.findAndHookMethod(targetClass, targetMethod, targetParam, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    Log.e("XposedCompat", "beforeHookedMethod: " + param.method.name)
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    Log.e("XposedCompat", "afterHookedMethod: " + param.method.name)
                }
            })
        } catch (e:Exception) {
            e.printStackTrace()
        }

    }
    companion object {
        private var inited = false

    }
}