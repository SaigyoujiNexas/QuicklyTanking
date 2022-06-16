package com.xupt.safeAndRun.modulesCore.login.widget

import androidx.annotation.DrawableRes

data class MultiFabItem(
    val id: Int,
    @DrawableRes val iconRes: Int,
    val label : String = ""

)
