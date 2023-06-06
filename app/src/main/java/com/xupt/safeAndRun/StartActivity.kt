package com.xupt.safeAndRun

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import com.xupt.safeAndRun.modulesbase.libbase.cache.Preferences
import com.xupt.safeAndRun.modulespublic.common.constant.KeyPool
import com.xupt.safeAndRun.modulespublic.common.constant.RoutePath
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun onStart() {
        super.onStart()
        val navController = findNavController(R.id.app_fragment_container)
        //check the token, if token exist, login directly.
        val token = Preferences.getString(KeyPool.TOKEN, "")
        if (token.isNullOrEmpty()) {
            val request = NavDeepLinkRequest.Builder
                .fromUri(RoutePath.LOG_IN.toUri())
                .build()
            navController.navigate(request)
        } else {
            navController.navigate(R.id.nav_main)
        }
        this.finish()
    }
}