package com.littlecorgi.airhotckeywithbettermallets.program

import android.content.Context
import android.opengl.GLES20.glUseProgram
import com.littlecorgi.airhotckeywithbettermallets.util.ShaderHelper
import com.littlecorgi.airhotckeywithbettermallets.util.TextResourceReader

open class ShaderProgram(context: Context, vertexShaderResourceId: Int, fragmentShaderResourceId: Int) {

    companion object {
        const val U_MATRIX = "u_Matrix"
        const val U_TEXTURE_UNIT = "u_TextureUnit"

        const val A_POSITION = "a_Position"
        const val A_COLOR = "a_Color"
        const val A_TEXTURE_COORDINATES = "a_TextureCoordinates"
    }


    protected val program: Int = ShaderHelper.buildProgram(
            TextResourceReader.readTextFileFromResource(
                    context, vertexShaderResourceId
            ),
            TextResourceReader.readTextFileFromResource(
                    context, fragmentShaderResourceId
            )
    )

    public fun useProgram() {
        glUseProgram(program)
    }

}