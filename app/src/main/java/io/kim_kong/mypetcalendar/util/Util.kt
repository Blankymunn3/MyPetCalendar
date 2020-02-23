package io.kim_kong.mypetcalendar.util

import android.app.Activity
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import io.kim_kong.mypetcalendar.R


class Util {
    companion object {

        @JvmStatic
        fun setIconTinkDark(activity: Activity?, hasToolBar: Boolean) {
            val window: Window = activity!!.window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

                if(hasToolBar) {
                    window.statusBarColor = ContextCompat.getColor(activity, R.color.colorWhite)
                } else {
                    window.statusBarColor = ContextCompat.getColor(activity, R.color.colorMainBack)
                }

                val decor = activity.window.decorView
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // 마시멜로우 버전보다 낮으면 상단바 색상을 어두운 색상으로 변경하여 아이콘이 보이도록 조정
                window.statusBarColor = ContextCompat.getColor(activity, R.color.colorBlack)
            }
        }

        @JvmStatic
        fun showToast(message: String, activity: Activity) {
            val inflater = activity.layoutInflater
            val toastDesign = inflater.inflate(R.layout.toast_design, activity.findViewById(R.id.toast_design_root))

            val text = toastDesign.findViewById<TextView>(R.id.TextView_toast_design)
            text.text = message

            val toast = Toast(activity.applicationContext)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = toastDesign
            toast.show()
        }
    }
 }