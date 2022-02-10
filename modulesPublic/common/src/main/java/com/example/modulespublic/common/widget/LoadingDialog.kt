package com.example.modulespublic.common.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.modulespublic.common.R

class LoadingDialog(context : Context, themeResId: Int = R.style.LoadingDialogTheme)
    : Dialog(context, themeResId){

    private val handler: Handler = Handler(context.mainLooper)
    private val mLayout: View
    private val tvMsg: TextView
    private val ivIcon: ImageView
    private val pbLoading: ProgressBar
        init {
            window?.setDimAmount(0f)
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            mLayout = inflater.inflate(R.layout.layout_loading_dialog, null, false)
            tvMsg = mLayout.findViewById(R.id.tv_msg)
            ivIcon = mLayout.findViewById(R.id.iv_icon)
            pbLoading = mLayout.findViewById(R.id.pb_loading)
            setContentView(mLayout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ))
            setCancelable(true)
            setCanceledOnTouchOutside(false)
        }
    fun disMissDelayed(duration: Int){
        handler.postDelayed(this::dismiss, duration.toLong())
    }
    fun setTextColor(color: Int) = this.apply { tvMsg.setTextColor(color) }

    fun setIcon(resId: Int) = this.apply { ivIcon.setImageResource(resId) }

    fun setIcon(bitmap: Bitmap) = this.apply { ivIcon.setImageBitmap(bitmap) }

    fun setMessage(message: String) = this.apply { tvMsg.setText(message) }

    fun setCancel(b: Boolean) = this.apply { setCancelable(b) }

    fun setCancelOnTouchOutSide(b: Boolean) = this.apply { super.setCanceledOnTouchOutside(b) }

    fun getMessage() = tvMsg.text.toString()

    fun setCancelDisabled() = this.apply { super.setCanceledOnTouchOutside(false)
    super.setCancelable(false)}

    fun setType(type: TYPE) = this.apply {
        when (type) {
            TYPE.TYPE_LOADING -> {
                pbLoading.visibility = View.VISIBLE
                ivIcon.visibility = View.GONE
            }
            TYPE.TYPE_MSG -> {
                pbLoading.visibility = View.GONE
                ivIcon.visibility = View.VISIBLE
            }
        }
    }
    enum class TYPE{
        TYPE_LOADING,
        TYPE_MSG
    }
    companion object{


    }

}