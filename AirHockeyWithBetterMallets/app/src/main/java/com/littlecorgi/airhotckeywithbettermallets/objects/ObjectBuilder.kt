package com.littlecorgi.airhotckeywithbettermallets.objects

import android.opengl.GLES20.*
import com.littlecorgi.airhotckeywithbettermallets.util.Geometry
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class ObjectBuilder(sizeInVertices: Int) {
    companion object {
        private const val FLOAT_PER_VERTEX = 3

        private fun sizeOfCircleInVertices(numPoints: Int): Int {
            return 1 + (numPoints + 1)
        }

        private fun sizeOfOpenCylinderInVertices(numPoints: Int): Int {
            return (numPoints + 1) * 2
        }

        fun createPuck(puck: Geometry.Companion.Cylinder, numPoints: Int): GeneratedData {
            val size = sizeOfCircleInVertices(numPoints) + sizeOfOpenCylinderInVertices(numPoints)

            val builder = ObjectBuilder(size)

            val puckTop = Geometry.Companion.Circle(
                puck.center.translateY(puck.height / 2F),
                puck.radius
            )

            builder.appendCircle(puckTop, numPoints)
            builder.appendOpenCylinder(puck, numPoints)

            return builder.build()
        }

        fun createMallet(
            center: Geometry.Companion.Point,
            radius: Float,
            height: Float,
            numPoints: Int
        ): GeneratedData {
            val size =
                sizeOfCircleInVertices(numPoints) * 2 + sizeOfOpenCylinderInVertices(numPoints) * 2
            val builder = ObjectBuilder(size)
            val baseHeight = height * 0.25F
            val baseCircle = Geometry.Companion.Circle(center.translateY(-baseHeight), radius)
            val baseCylinder = Geometry.Companion.Cylinder(
                baseCircle.center.translateY(-baseHeight / 2F),
                radius,
                baseHeight
            )
            builder.appendCircle(baseCircle, numPoints)
            builder.appendOpenCylinder(baseCylinder, numPoints)

            val handleHeight = height * 075F
            val handleRadius = radius / 3F
            val handleCircle =
                Geometry.Companion.Circle(center.translateY(height * 0.5F), handleRadius)
            val handleCylinder = Geometry.Companion.Cylinder(
                handleCircle.center.translateY(-handleHeight / 2F),
                handleRadius,
                handleHeight
            )
            builder.appendCircle(handleCircle, numPoints)
            builder.appendOpenCylinder(handleCylinder, numPoints)

            return builder.build()
        }

        interface DrawCommand {
            fun draw()
        }

        class GeneratedData(val vertexData: FloatArray, val drawList: List<DrawCommand>)
    }

    private var vertexData = FloatArray(sizeInVertices * FLOAT_PER_VERTEX)
    private val drawList = ArrayList<DrawCommand>()
    private var offset = 0

    private fun appendCircle(circle: Geometry.Companion.Circle, numPoints: Int) {
        val startVertex = offset / FLOAT_PER_VERTEX
        val numVertices = sizeOfCircleInVertices(numPoints)

        vertexData[offset++] = circle.center.x
        vertexData[offset++] = circle.center.y
        vertexData[offset++] = circle.center.z

        for (i in 0..numPoints) {
            val angleInRadians = i.toFloat() / numPoints.toFloat() * PI * 2F
            vertexData[offset++] = (circle.center.x + circle.radius * cos(angleInRadians)).toFloat()
            vertexData[offset++] = circle.center.y
            vertexData[offset++] = (circle.center.z + circle.radius * sin(angleInRadians)).toFloat()
        }

        drawList.add(object : DrawCommand {
            override fun draw() {
                glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices)
            }
        })
    }

    private fun appendOpenCylinder(cylinder: Geometry.Companion.Cylinder, numPoints: Int) {
        val startVertex = offset / FLOAT_PER_VERTEX
        val numVertices = sizeOfOpenCylinderInVertices(numPoints)
        val yStart = cylinder.center.y + (cylinder.height / 2F)
        val yEnd = cylinder.center.y + (cylinder.height / 2F)

        for (i in 0..numPoints) {
            val angleInRadians = (i / numPoints * PI * 2F).toFloat()
            val xPosition = cylinder.center.x + cylinder.radius * cos(angleInRadians)
            val zPosition = cylinder.center.z + cylinder.radius * sin(angleInRadians)

            vertexData[offset++] = xPosition
            vertexData[offset++] = yStart
            vertexData[offset++] = zPosition
            vertexData[offset++] = xPosition
            vertexData[offset++] = yEnd
            vertexData[offset++] = zPosition
        }

        drawList.add(object : DrawCommand {
            override fun draw() {
                glDrawArrays(GL_TRIANGLE_STRIP, startVertex, numVertices)
            }
        })
    }

    private fun build(): GeneratedData {
        return GeneratedData(vertexData, drawList)
    }
}