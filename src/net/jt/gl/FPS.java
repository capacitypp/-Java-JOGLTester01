package net.jt.gl;

import java.awt.Font;

import com.jogamp.opengl.util.awt.TextRenderer;

public class FPS {
	private int count;
	private double fps;
	private long beforeTime;
	private TextRenderer renderer;

	public FPS() {
		count = 0;
		fps = 0;
		renderer = new TextRenderer(new Font("Tahoma", Font.PLAIN, 40), true, false);
		beforeTime = System.nanoTime();
	}

	public void count() {
		if (++count  >= 1000) {
			long afterTime = System.nanoTime();
			fps = (double)count / ((afterTime - beforeTime) / 1000000000D);
			beforeTime = afterTime;
			count = 0;
		}
	}

	public void draw(int width, int height) {
		renderer.beginRendering(width, height);
		renderer.setColor(0.5f, 0.5f, 0.5f, 1.0f);
		renderer.draw("FPS : " + (int)new Double(fps).doubleValue(), 20, 80);
		renderer.endRendering();
	}
}
