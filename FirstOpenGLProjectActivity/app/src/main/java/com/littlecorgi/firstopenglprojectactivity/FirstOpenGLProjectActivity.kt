package com.littlecorgi.firstopenglprojectactivity

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class FirstOpenGLProjectActivity : AppCompatActivity() {

    private lateinit var glSurfaceView: GLSurfaceView
    private var rendererSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = GLSurfaceView(this)

        /**
         * 验证是否支持OpenGL ES 2.0
         */
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
                || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK build for x86")))

        /**
         * 配置渲染表面
         */
        if (supportsEs2) {
            // 配置这个surface视图
            glSurfaceView.setEGLContextClientVersion(2)

            // 配置Renderer
            glSurfaceView.setRenderer(FirstOpenGLProjectRenderer())
            rendererSet = true
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0", Toast.LENGTH_SHORT)
                .show()
            return
        }

        setContentView(glSurfaceView)
    }

    override fun onPause() {
        super.onPause()

        if (rendererSet)
            glSurfaceView.onPause()
    }

    override fun onResume() {
        super.onResume()

        if (rendererSet)
            glSurfaceView.onResume()
    }
}
