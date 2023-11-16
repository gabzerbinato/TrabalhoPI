import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

public class EnterField extends JPanel{
	
	JTextField texto;
	JLabel nome;
	Dimension di;

	public EnterField(String t) {
		super();
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		this.setName(t);
		this.setBackground(Main.DEF);
		//this.setBorder(BorderFactory.createLineBorder(Main.DEF.darker(), 1));
		
		texto = new JTextField();
		nome = new JLabel(t);
		nome.setHorizontalAlignment(JLabel.LEFT);
		this.add(nome);
		this.add(texto);
	}
	
	public String getText() {
		return this.texto.getText();
	}
	
	
	public void init(Dimension d) {
		this.di = d;
		this.setPreferredSize(d);
		this.nome.setPreferredSize(new Dimension(d.width/7, d.height));
		this.texto.setPreferredSize(new Dimension(d.width/7*5, d.height-d.height/5));
	}
	
}
