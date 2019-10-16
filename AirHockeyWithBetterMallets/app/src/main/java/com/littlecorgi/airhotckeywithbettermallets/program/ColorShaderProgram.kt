package com.littlecorgi.airhotckeywithbettermallets.program

import android.content.Context
import android.opengl.GLES20.*
import com.littlecorgi.airhotckeywithbettermallets.R

class ColorShaderProgram(context: Context) :
    ShaderProgram(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader) {
    private val uMatrixLocation = glGetUniformLocation(program, U_MATRIX)
    private val aPositionLocation = glGetAttribLocation(program, A_POSITION)
    //    private val aColorLocation = glGetAttribLocation(program, A_COLOR)
    private val uColorLocation = glGetUniformLocation(program, U_COLOR)

//    public fun setUniforms(matrix: FloatArray) {
//        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
//
//    }

    public fun setUniforms(matrix: FloatArray, r: Float, g: Float, b: Float) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        glUniform4f(uColorLocation, r, g, b, 1f)
    }

    public fun getPositionAttributeLocation(): Int {
        return aPositionLocation
    }
}

