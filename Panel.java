import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class Panel extends JPanel{
	public int width, height;
	public Panel(int w, int h) {
		super();
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(w, h));
		this.setBorder(BorderFactory.createLineBorder(Color.green));
		this.setBackground(Color.gray);
	}
	
	public void loc(int x, int y) { this.setBounds(x , y, this.width, this.height);}
	
}
