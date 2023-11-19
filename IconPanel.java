import java.awt.Image;

import javax.swing.*;

public class IconPanel extends Panel{
	JLabel label;
	ImageIcon img;
	
	public IconPanel(int w, int h, String path) {
		super(w, h);
		img = new ImageIcon(path);
		img = new ImageIcon(img.getImage().getScaledInstance(w-w/5, h-h/5, Image.SCALE_AREA_AVERAGING));
		label = new JLabel(img);
		add(label);
	}
}