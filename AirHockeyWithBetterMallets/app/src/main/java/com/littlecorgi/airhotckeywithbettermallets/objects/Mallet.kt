package com.littlecorgi.airhotckeywithbettermallets.objects

import com.littlecorgi.airhotckeywithbettermallets.data.VertexArray
import com.littlecorgi.airhotckeywithbettermallets.program.ColorShaderProgram
import com.littlecorgi.airhotckeywithbettermallets.util.Geometry

class Mallet(public val radius: Float, public val height: Float, numPointsAroundMallet: Int) {
//    companion object {
//        private const val POSITION_COMPONENT_COUNT = 2
//        private const val COLOR_COMPONENT_COUNT = 3
//        private const val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT
//        private val VERTEX_DATA = floatArrayOf(
//                0F, -0.4F, 0F, 0F, 1F,
//                0F, 0.4F, 1F, 0F, 0F
//        )
//    }
//
//    private val vertexArray: VertexArray
//
//    init {
//        vertexArray = VertexArray(VERTEX_DATA)
//    }
//
//    public fun bindData(colorProgram: ColorShaderProgram) {
//        vertexArray.setVertexAttribPointer(
//                0,
//                colorProgram.getPositionAttributeLocation(),
//                POSITION_COMPONENT_COUNT,
//                STRIDE
//        )
//        vertexArray.setVertexAttribPointer(
//                POSITION_COMPONENT_COUNT,
//                colorProgram.getColorAttributeLocation(),
//                COLOR_COMPONENT_COUNT,
//                STRIDE
//        )
//    }
//
//    public fun draw() {
//        glDrawArrays(GL_POINTS, 0, 2)
//    }

    companion object {
        private const val POSITION_COMPONENT_COUNT = 3
    }

    private val vertexArray: VertexArray
    private val drawList: List<ObjectBuilder.Companion.DrawCommand>

    init {
        val generatedData = ObjectBuilder.createMallet(
            Geometry.Companion.Point(0F, 0F, 0F),
            radius,
            height,
            numPointsAroundMallet
        )

        vertexArray = VertexArray(generatedData.vertexData)
        drawList = generatedData.drawList
    }

    public fun bindData(colorProgram: ColorShaderProgram) {
        vertexArray.setVertexAttribPointer(
            0,
            colorProgram.getPositionAttributeLocation(),
            POSITION_COMPONENT_COUNT,
            0
        )
    }

    public fun draw() {
        for (drawCommand in drawList) {
            drawCommand.draw()
        }
    }
}