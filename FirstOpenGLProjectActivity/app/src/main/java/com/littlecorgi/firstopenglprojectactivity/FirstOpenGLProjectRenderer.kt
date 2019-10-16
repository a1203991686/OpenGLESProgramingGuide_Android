package com.littlecorgi.firstopenglprojectactivity

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class FirstOpenGLProjectRenderer : GLSurfaceView.Renderer {

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        glClearColor(1.0F, 0.0F, 0.0F, 0.0F)
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        glViewport(0, 0, p1, p2)
    }

    override fun onDrawFrame(p0: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)
    }
}