package achi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimationPanel extends JPanel implements ActionListener {
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private int frameNumber = 0;
	private final int fps = 12; // Frames per second
	private Timer timer;

	/*
	 * sets up the AnimationPanel class to handle animation using a Timer
	 * 
	 */
	public AnimationPanel() {
		int delay = 1000 / fps;
		timer = new Timer(delay, this);
		timer.setInitialDelay(0);
	}

	/*
	 * define the start and end coordinates for an animation move, reset the
	 * animation frame counter, and initiate the animation sequence using a timer.
	 * 
	 */
	public void setMove(int startX, int startY, int endX, int endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		frameNumber = 0;
		timer.start();
	}

	/*
	 * checks if the timer associated with an animation is currently running, and if
	 * so, it stops the timer
	 * 
	 */
	public void stopAnimation() {
		if (timer.isRunning()) {
			timer.stop();
		}
	}

	/*
	 * manages the progression of the animation frames and triggers the repaints
	 * until the animation is completed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (frameNumber < 40) {
			frameNumber++;
			repaint();
		} else {
			stopAnimation();
			frameNumber = 0;
		}
	}
}
