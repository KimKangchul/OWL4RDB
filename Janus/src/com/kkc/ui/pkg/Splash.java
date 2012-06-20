package com.kkc.ui.pkg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Splash extends Window {
	private Image splashImage;
	private int imgWidth, imgHeight;
	private String imgName;
	private static final int BORDERSIZE = 5;
	private static final Color BORDERCOLOR = Color.gray;
	Toolkit tk;

	public Splash(Frame f, String imgName) {
		super(f);
		this.imgName = imgName;
		tk = Toolkit.getDefaultToolkit();
		splashImage = loadSplashImage();
		showSplashScreen();
		f.addWindowListener(new WindowListener());
	}

	public Image loadSplashImage() {
		MediaTracker tracker = new MediaTracker(this);
		Image result;
		result = tk.getImage(imgName);
		tracker.addImage(result, 0);
		try {
			tracker.waitForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		imgWidth = result.getWidth(this);
		imgHeight = result.getHeight(this);
		return (result);
	}

	public void showSplashScreen() {
		Dimension screenSize = tk.getScreenSize();
		setBackground(BORDERCOLOR);
		int w = imgWidth + (BORDERSIZE * 2);
		int h = imgHeight + (BORDERSIZE * 2);
		int x = (screenSize.width - w) / 2;
		int y = (screenSize.height - h) / 2;
		setBounds(x, y, w, h);
		setVisible(true);
	}

	public void paint(Graphics g) {
		g.drawImage(splashImage, BORDERSIZE, BORDERSIZE, imgWidth, imgHeight,
				this);
	}

	class WindowListener extends WindowAdapter {
		public void windowActivated(WindowEvent we) {
			setVisible(false);
			dispose();
		}
	}
}
