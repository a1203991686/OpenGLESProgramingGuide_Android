package com.littlecorgi.airhotckeywithbettermallets.objects

import android.opengl.GLES20.GL_TRIANGLE_FAN
import android.opengl.GLES20.glDrawArrays
import android.util.FloatMath
import com.littlecorgi.airhotckeywithbettermallets.util.Geometry
import javax.microedition.khronos.opengles.GL
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

        fun createPuck(puck: Geometry.Companion.Cylinder, numPoints: Int): GenerateData {
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

        interface DrawCommand {
            fun draw()
        }
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

        drawList.add(object: DrawCommand {
            override fun draw() {
                glDrawArrays(GL_TRIANGLE_FAN, startVertex, numVertices)
            }
        })
    }
}