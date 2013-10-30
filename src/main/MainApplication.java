package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import main.screens.Screen;
import main.screens.StartScreen;
import asciiPanel.AsciiPanel;

public class MainApplication extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1060623638149583738L;

	private AsciiPanel terminal;
	private Screen screen;

	public MainApplication() {
		super();
		terminal = new AsciiPanel();
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
	}

	public void repaint() {
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
	}

	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		repaint();
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		MainApplication app = new MainApplication();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setTitle("Infinite Cavy Story");
		app.setVisible(true);
	}
}
