package com.littlecorgi.airhotckeywithbettermallets.objects

import com.littlecorgi.airhotckeywithbettermallets.data.VertexArray
import com.littlecorgi.airhotckeywithbettermallets.program.ColorShaderProgram
import com.littlecorgi.airhotckeywithbettermallets.util.Geometry

class Puck(
    public val radius: Float,
    public val height: Float,
    numPointsAroundPuck: Int
) {
    companion object {
        const val POSITION_COMPONENT_COUNT = 3
    }


    private val vertexArray: VertexArray
    private val drawList: List<ObjectBuilder.Companion.DrawCommand>

    init {
        val generatedData = ObjectBuilder.createPuck(
            Geometry.Companion.Cylinder(
                Geometry.Companion.Point(
                    0F,
                    0F,
                    0F
                ), radius, height
            ), numPointsAroundPuck
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