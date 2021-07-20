//package com.lcq.legal;
//
//import de.robv.android.xposed.XposedHelpers;
//
//public class sss {
//
//    void checkPermission() {
//        try {
//            int.class
//            Class<s> targetClass = Class.forName("ActivityManager");
//            val targetMethod = "checkComponentPermission";
//            val targetParam = Class.forName("okhttp3.Request");
//            XposedHelpers.findAndHookMethod(targetClass, targetMethod, String.javaClass, Int.javaClass ,Int.javaClass ,Boolean.javaClass, object : XC_MethodHook() {
//                @Throws(Throwable::class)
//                override fun beforeHookedMethod(param: MethodHookParam) {
//                    super.beforeHookedMethod(param)
//                    Log("beforeHookedMethod: " + param.method.name)
//                }
//
//                @Throws(Throwable::class)
//                override fun afterHookedMethod(param: MethodHookParam) {
//                    super.afterHookedMethod(param)
//                    Log("afterHookedMethod: " + param.method.name)
//                }
//            })
//        } catch (e:Exception) {
//            e.printStackTrace()
//        }
//    }
//}
