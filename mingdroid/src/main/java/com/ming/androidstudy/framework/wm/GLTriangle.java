package com.ming.androidstudy.framework.wm;

import android.opengl.GLES20;
import android.util.Log;
import com.ming.androidstudy.utils.Convert;
import com.ming.androidstudy.utils.OpenGLUtil;

import java.nio.FloatBuffer;

//https://blog.csdn.net/qq_32175491/article/details/79091647
//https://www.jianshu.com/p/8793f0fbd1e6
public class GLTriangle {
    private FloatBuffer vertexBuffer;
    private int mProgram;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };
    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    private float color[] = { 255, 0, 0, 1.0f };

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public GLTriangle() {
        // 一、图形创建
        // 1.顶点坐标转为ByteBuffer
        vertexBuffer = OpenGLUtil.floatBufferUtil(triangleCoords);

        // 二、绘制图形
        mProgram = OpenGLUtil.createProgram(vertexShaderCode, fragmentShaderCode);
    }

    // 5.draw
    public void draw() {
        if (mProgram == 0) {
            return;
        }

        // 清屏
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        // 将程序添加到OpenGL ES环境
        GLES20.glUseProgram(mProgram);

        // 获取顶点着色器的位置的句柄
        int positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 获取片段着色器的颜色的句柄
        int colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        // 启用三角形顶点位置的句柄
        GLES20.glEnableVertexAttribArray(positionHandle);
        //准备三角形坐标数据
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        // 设置绘制三角形的颜色
        GLES20.glUniform4fv(colorHandle, 1, color, 0);
        // 绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // 禁用顶点数组
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
