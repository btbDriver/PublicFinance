package com.drive.finance.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect


/**
 * 在Application中初始化设备的尺寸信息
 */
fun initDisplayOpinion(mContext : Activity) {
    val dm = mContext.resources.displayMetrics
    density = dm.density
    densityDPI = dm.densityDpi
    screenWidthPx = dm.widthPixels
    screenHeightPx = dm.heightPixels

    screenWidthDip = px2dip(mContext.applicationContext, dm.widthPixels).toFloat()
    screenHeightDip = px2dip(mContext.applicationContext, dm.heightPixels).toFloat()
    statusBarHeight = getStatusBarHeight(mContext)
}

/**
 * 获取设备状态栏的高度
 */
private fun getStatusBarHeight(context: Activity): Int {
    val frame = Rect()
    context.window.decorView.getWindowVisibleDisplayFrame(frame)
    val statusBarHeight = frame.top
    /*// 获取标题栏高度
    val window = context.window
    val contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop()
    // statusBarHeight是上面所求的状态栏的高度
    val titleBarHeight = contentViewTop - statusBarHeight

    Log.i("test", "statusBarHeight=" + statusBarHeight + " contentViewTop="
            + contentViewTop + " titleBarHeight=" + titleBarHeight)


    var statusBarHeight1 = 0
    try {
        val clazz = Class.forName("com.android.internal.R\$dimen")
        val `object` = clazz.newInstance()
        val field = clazz.getField("status_bar_height")
        //反射出该对象中status_bar_height字段所对应的在R文件的id值
        //该id值由系统工具自动生成,文档描述如下:
        //The desired resource identifier, as generated by the aapt tool.
        val id = Integer.parseInt(field.get(`object`).toString())
        statusBarHeight1 = context.resources.getDimensionPixelSize(id)
        Log.i("test", "statusBarHeight1: " + statusBarHeight1)
    } catch (e: Exception) {
    }*/

    return statusBarHeight
}

/**
 * 设置状态栏字体图标为深色，需要MIUI6以上

 * @param isFontColorDark 是否把状态栏字体及图标颜色设置为深色
 * *
 * @return boolean 成功执行返回true
 */
fun setStatusBarLightMode(activity: Activity, isFontColorDark: Boolean): Boolean {
    val window = activity.window
    var result = false
    if (window != null) {
        val clazz = window::class.java
        try {
            var darkModeFlag = 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (isFontColorDark) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
            result = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
    return result
}

/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dip(context: Context, pxValue: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}


/**
 * 将px值转换为sp值，保证文字大小不变

 * @param pxValue （DisplayMetrics类中属性scaledDensity）
 * *
 * @return
 */
fun px2sp(context: Context, pxValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变

 * @param spValue （DisplayMetrics类中属性scaledDensity）
 * *
 * @return
 */
fun sp2px(context: Context, spValue: Float): Int {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}


var screenWidthPx: Int = 0 //屏幕宽 px
var screenHeightPx: Int = 0 //屏幕高 px
var density: Float = 0.toFloat()//屏幕密度
var densityDPI: Int = 0//屏幕密度
var screenWidthDip: Float = 0.toFloat()//  dp单位
var screenHeightDip: Float = 0.toFloat()//  dp单位
var statusBarHeight: Int = 0