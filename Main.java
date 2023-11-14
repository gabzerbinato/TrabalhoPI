import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Main {
	Frame frame;
	Panel p1, p2,
		  header, registros,
		  botoesNovoCadastro, logoContainer;
	JButton novoPosto = new JButton("Novo posto"), 
			novoVeiculo = new JButton("Novo veículo");
	
	int WIDTH = 1000, HEIGHT = 600;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		frame = new Frame(WIDTH, HEIGHT);
		p1 = new Panel(WIDTH/100*57, HEIGHT);
		p1.setLayout(new FlowLayout(5,5,FlowLayout.LEFT));
		p1.setBackground(Color.blue);
		p2 = new Panel(WIDTH/100*40, HEIGHT);
		
		header = new Panel(p1.width-WIDTH/100, p1.height/100*30);
		header.setBackground(Color.red);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		registros = new Panel(p1.width-WIDTH/100, p1.height/100*60);
		
		botoesNovoCadastro = new Panel(header.width/2, header.height);
		botoesNovoCadastro.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		
		logoContainer = new Panel(header.width/2 - WIDTH/66, header.height);
		logoContainer.setBackground(Color.yellow);
		
		novoPosto.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		novoVeiculo.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		
		botoesNovoCadastro.add(novoPosto);
		botoesNovoCadastro.add(novoVeiculo);
		
		frame.add(p1);
		frame.add(p2);
		
		p1.add(header);
		p1.add(registros);
		
		header.add(botoesNovoCadastro);
		header.add(logoContainer);
		
	}

}
