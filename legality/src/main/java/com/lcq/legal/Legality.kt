package com.lcq.legal

import android.app.Application
import android.content.pm.PackageManager
import android.util.Log
import com.swift.sandhook.xposedcompat.XposedCompat
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers

class Legality(private val application: Application, private val phase: String) {


//    一定情况出现，failed to open /lib64/libart.so， 所以崩溃注意
//    这里再传入一个时机，用以判断是否是用户之前，
    fun check() {

        if (!inited) {
            inited = true
            Log.e("wdnmd", "check: instance success")
            XposedCompat.cacheDir = application.cacheDir;
            XposedCompat.context = application.applicationContext
            XposedCompat.classLoader = application.classLoader
            XposedCompat.isFirstApplication = true
        }

//        checkNetWork()
        checkPermission()
    }

    fun checkNetWork() {
        try {
            val targetClass = Class.forName("okhttp3.OkHttpClient")
            val targetMethod = "newCall"
            val targetParam = Class.forName("okhttp3.Request")
            XposedHelpers.findAndHookMethod(targetClass, targetMethod, null, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    Log("beforeHookedMethod: " + param.method.name)
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    Log("afterHookedMethod: " + param.method.name)
                }
            })
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun checkPermission() {
        try {

            val targetClass = Class.forName("android.app.Activity")
            val targetMethod = "requestPermissions"
            val targetParam = Class.forName("java.lang.String")
            val targetParam1 = Class.forName("java.lang.String")
            val targetParam2 = Class.forName("okhttp3.Request")

            XposedHelpers.findAndHookMethod(targetClass, targetMethod, targetParam,targetParam, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    super.beforeHookedMethod(param)
                    Log("beforeHookedMethod: " + param.method.name)
                }

                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    Log("afterHookedMethod: " + param.method.name)
                }
            })
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    fun Log(msg: String) {
        if (phase == "sss") {
            Log.e("XposedCompat_Warning", msg)
        } else {
            Log.e("XposedCompat", msg)
        }
    }

    companion object {
        private var inited = false
    }

}