package net.jt;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jdk.internal.dynalink.beans.StaticClass;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

public class JOGLTester implements GLEventListener{
	private GL2 gl;
	private GLUT glut;
	//アニメーション(displayの周期的呼び出し)用
	private Animator animator;
	private float blue;

	public JOGLTester() {
		blue = 0;
		JFrame frame = new JFrame("JOGLTester");

		GLCanvas canvas = new GLCanvas();
		canvas.addGLEventListener(this);

		frame.add(canvas);
		frame.setSize(300, 300);

		animator = new Animator(canvas);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});

		frame.setVisible(true);
		animator.start();
	}

	public void drawCube(float x, float y, float z, double r, double g, double b) {
		gl.glPushMatrix();
		gl.glTranslatef(x, y, z);
		gl.glColor3d(r, g, b);
		glut.glutWireCube(2);
		gl.glPopMatrix();
	}

	@Override
	public void display(GLAutoDrawable arg0) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		//gl.glTranslatef(0, 0, 10);
		//gl.glTranslatef(1F, 0, 0);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -5);
		drawCube(0, 0, 0, 0, 1, 0);
		drawCube(3.5F, 0, 0, 0, 0, 1);
		drawCube(-3.5F, 0, 0, 1, 0, 0);
		drawCube(0, 3.5F, 0, 1, 1, 0);
		drawCube(0, -3.5F, 0, 0, 1, 1);
		drawCube(3.5F, 3.5F, 0, 1, 0, 1);
		//右下のブロックだけ周期的に色を変える
		drawCube(3.5F, -3.5F, 0, 1, 1, blue);
		blue += 0.001;
		blue -= blue > 1 ? 1 : 0;
		drawCube(-3.5F, -3.5F, 0, 1, 0.5, 0);
		drawCube(-3.5F, 3.5F, 0, 0, 1, 0.5);
		gl.glPopMatrix();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void init(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();
		glut = new GLUT();
		//背景を白にする
		gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		float ratio = (float)height / (float)width;
		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustum(-1.0f, 1.0f, -ratio, ratio, 5.0f, 40.0f);

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -20.0f);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new JOGLTester();
			}
		});
	}
}
