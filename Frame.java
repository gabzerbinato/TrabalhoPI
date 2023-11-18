import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Frame extends JFrame{
	public Frame(int w, int h) {
		super();
		this.setSize(new Dimension(w, h));
		this.setResizable(false);
		this.getContentPane().setBackground(new Color(255,255,255));
		this.setLocationRelativeTo(null);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}