import java.awt.*;

import javax.swing.*;

public class Panel extends JPanel{
	public int width, height;
	public Panel(int w, int h) {
		super();
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(w, h));
		this.setOpaque(true);
	}

	public Panel(int posicao, int w, int h, int hgap, int vgap, boolean borda) {
		//super();
		this.width = w;
		this.height = h;
		this.setPreferredSize(new Dimension(w, h));
		this.setLayout(new FlowLayout(posicao, hgap, vgap));
		this.setBackground(Color.WHITE);
		if (borda) {
			this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		}
	}
	
	public void loc(int x, int y) { this.setBounds(x , y, this.width, this.height);}
	
}