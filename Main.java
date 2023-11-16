import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
	Frame frame;
	Panel p1, p2,
		  header, registros,
		  botoesNovoCadastro, logoContainer,
		  registroHeader, registroLeft, registroRight,
		  camposCont;
	JLabel registrarAbastecimento, tipoAbastecimento;
	JButton novoPosto = new JButton("Novo posto"), 
			novoVeiculo = new JButton("Novo veículo"),
			gravarRegistro = new JButton("Gravar registro");
	ButtonGroup bg = new ButtonGroup();
	JRadioButton alcool, gasolina;
	JComboBox selecionarPosto, selecionarVeiculo;
	JTable table;
	JScrollPane tablePane;
	EnterField kmAtual, qtd, preco, data;
	
	static int WIDTH = 1000, HEIGHT = 600;
	static String[] colunasTabela = {
		"Data", "Veículo", "Valor total", "km/L"	
	};
	static Color DEF = new Color(235,235,235);
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		frame = new Frame(WIDTH, HEIGHT);
		p1 = new Panel(WIDTH/100*57, HEIGHT);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		p1.setBackground(DEF);
		p2 = new Panel(WIDTH/100*40, HEIGHT);
		p2.setLayout(new FlowLayout(0, 5, 5));
		
		header = new Panel(p1.width-WIDTH/100, p1.height/100*30);
		//header.setBackground(Color.red);
		header.setOpaque(false);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		registros = new Panel(p1.width-WIDTH/100, p1.height/100*65);
		registros.setLayout(new FlowLayout(FlowLayout.LEFT, -1, 0));
		registros.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		registros.setBackground(DEF);
		
		botoesNovoCadastro = new Panel(header.width/2, header.height);
		botoesNovoCadastro.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		botoesNovoCadastro.setOpaque(false);
		botoesNovoCadastro.add(novoPosto);
		botoesNovoCadastro.add(novoVeiculo);
		
		logoContainer = new Panel(header.width/2 - WIDTH/66, header.height);
		logoContainer.setBackground(Color.yellow);
		
		header.add(botoesNovoCadastro);
		header.add(logoContainer);
		
		novoPosto.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		novoVeiculo.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		
		registroHeader = new Panel(registros.width - WIDTH/132, registros.height/10);
		registroHeader.setBackground(Color.gray);
		registroHeader.setBorder(BorderFactory.createLineBorder(Color.black));
		registrarAbastecimento = new JLabel("Registrar Abastecimento");
		registroHeader.add(registrarAbastecimento);
		
		registroLeft = new Panel(registros.width/2, registros.height/3);
		//registroLeft.setBackground(Color.orange);
		registroLeft.setOpaque(false);
		registroLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		registroRight = new Panel(registros.width/2 - WIDTH/150, registros.height/3);
		registroRight.setBackground(DEF);
		registroRight.setBorder(BorderFactory.createLineBorder(Color.black));
		registroRight.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		selecionarPosto = new JComboBox(new String[] {"Selecionar posto", "dois", "tres"});
		selecionarVeiculo = new JComboBox(new String[] {"Selecionar veículo", "dois", "tres"});
		selecionarPosto.setPreferredSize(new Dimension(registroLeft.width/100*95, registroLeft.height/4));
		selecionarVeiculo.setPreferredSize(new Dimension(registroLeft.width/100*95, registroLeft.height/4));
		registroLeft.add(selecionarPosto);
		registroLeft.add(selecionarVeiculo);
		
		tipoAbastecimento = new JLabel("Tipo de abastecimento");
		registroRight.add(tipoAbastecimento);
		alcool = new JRadioButton("Álcool");
		alcool.setPreferredSize(new Dimension(registroRight.width/100*95, registroRight.height/4));
		alcool.setOpaque(false);
		gasolina = new JRadioButton("Gasolina");
		gasolina.setPreferredSize(new Dimension(registroRight.width/100*95, registroRight.height/4));
		gasolina.setOpaque(false);
		bg.add(alcool);
		bg.add(gasolina);
		registroRight.add(alcool);
		registroRight.add(gasolina);
		
		registros.add(registroHeader);
		registros.add(registroLeft);
		registros.add(registroRight);
		
		camposCont = new Panel(registros.width, registros.height / 3 * 2);
		camposCont.setOpaque(false);
		camposCont.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 10));
		
		kmAtual = new EnterField("km atual");
		qtd = new EnterField("quantidade");
		preco = new EnterField("preço / L");
		data = new EnterField("Data");
		
		EnterField[] campos = {kmAtual, qtd, preco, data};
		
		for(EnterField ef : campos) {
			//ef.setPreferredSize(new Dimension(WIDTH/2, camposCont.height/9));
			ef.init(new Dimension(WIDTH/2, camposCont.height/9));
			camposCont.add(ef);
		}
		
		camposCont.add(gravarRegistro);
		
		registros.add(camposCont);
		
		p1.add(header);
		p1.add(registros);
		
		///////////////////
		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		for(String col : colunasTabela)
			model.addColumn(col);
		table.setModel(model);
		
		tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(p2.width-WIDTH/66, p2.height-WIDTH/66));
		
		for(int i = 0; i < 45; i++)
			model.addRow(new String[] {i + ""});
		
		p2.add(tablePane);
		
		//////////////////
		
		frame.add(p1);
		frame.add(p2);
		
		frame.pack();
		frame.setVisible(true);
		
	}

}import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main {
	Frame frame;
	Panel p1, p2,
		  header, registros,
		  botoesNovoCadastro, logoContainer,
		  registroHeader, registroLeft, registroRight,
		  camposCont;
	JLabel registrarAbastecimento, tipoAbastecimento;
	JButton novoPosto = new JButton("Novo posto"), 
			novoVeiculo = new JButton("Novo veículo"),
			gravarRegistro = new JButton("Gravar registro");
	ButtonGroup bg = new ButtonGroup();
	JRadioButton alcool, gasolina;
	JComboBox selecionarPosto, selecionarVeiculo;
	JTable table;
	JScrollPane tablePane;
	EnterField kmAtual, qtd, preco, data;
	
	static int WIDTH = 1000, HEIGHT = 600;
	static String[] colunasTabela = {
		"Data", "Veículo", "Valor total", "km/L"	
	};
	static Color DEF = new Color(235,235,235);
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		frame = new Frame(WIDTH, HEIGHT);
		p1 = new Panel(WIDTH/100*57, HEIGHT);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		p1.setBackground(DEF);
		p2 = new Panel(WIDTH/100*40, HEIGHT);
		p2.setLayout(new FlowLayout(0, 5, 5));
		
		header = new Panel(p1.width-WIDTH/100, p1.height/100*30);
		//header.setBackground(Color.red);
		header.setOpaque(false);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		registros = new Panel(p1.width-WIDTH/100, p1.height/100*65);
		registros.setLayout(new FlowLayout(FlowLayout.LEFT, -1, 0));
		registros.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		registros.setBackground(DEF);
		
		botoesNovoCadastro = new Panel(header.width/2, header.height);
		botoesNovoCadastro.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		botoesNovoCadastro.setOpaque(false);
		botoesNovoCadastro.add(novoPosto);
		botoesNovoCadastro.add(novoVeiculo);
		
		logoContainer = new Panel(header.width/2 - WIDTH/66, header.height);
		logoContainer.setBackground(Color.yellow);
		
		header.add(botoesNovoCadastro);
		header.add(logoContainer);
		
		novoPosto.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		novoVeiculo.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		
		registroHeader = new Panel(registros.width - WIDTH/132, registros.height/10);
		registroHeader.setBackground(Color.gray);
		registroHeader.setBorder(BorderFactory.createLineBorder(Color.black));
		registrarAbastecimento = new JLabel("Registrar Abastecimento");
		registroHeader.add(registrarAbastecimento);
		
		registroLeft = new Panel(registros.width/2, registros.height/3);
		//registroLeft.setBackground(Color.orange);
		registroLeft.setOpaque(false);
		registroLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		registroRight = new Panel(registros.width/2 - WIDTH/150, registros.height/3);
		registroRight.setBackground(DEF);
		registroRight.setBorder(BorderFactory.createLineBorder(Color.black));
		registroRight.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		selecionarPosto = new JComboBox(new String[] {"Selecionar posto", "dois", "tres"});
		selecionarVeiculo = new JComboBox(new String[] {"Selecionar veículo", "dois", "tres"});
		selecionarPosto.setPreferredSize(new Dimension(registroLeft.width/100*95, registroLeft.height/4));
		selecionarVeiculo.setPreferredSize(new Dimension(registroLeft.width/100*95, registroLeft.height/4));
		registroLeft.add(selecionarPosto);
		registroLeft.add(selecionarVeiculo);
		
		tipoAbastecimento = new JLabel("Tipo de abastecimento");
		registroRight.add(tipoAbastecimento);
		alcool = new JRadioButton("Álcool");
		alcool.setPreferredSize(new Dimension(registroRight.width/100*95, registroRight.height/4));
		alcool.setOpaque(false);
		gasolina = new JRadioButton("Gasolina");
		gasolina.setPreferredSize(new Dimension(registroRight.width/100*95, registroRight.height/4));
		gasolina.setOpaque(false);
		bg.add(alcool);
		bg.add(gasolina);
		registroRight.add(alcool);
		registroRight.add(gasolina);
		
		registros.add(registroHeader);
		registros.add(registroLeft);
		registros.add(registroRight);
		
		camposCont = new Panel(registros.width, registros.height / 3 * 2);
		camposCont.setOpaque(false);
		camposCont.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 10));
		
		kmAtual = new EnterField("km atual");
		qtd = new EnterField("quantidade");
		preco = new EnterField("preço / L");
		data = new EnterField("Data");
		
		EnterField[] campos = {kmAtual, qtd, preco, data};
		
		for(EnterField ef : campos) {
			//ef.setPreferredSize(new Dimension(WIDTH/2, camposCont.height/9));
			ef.init(new Dimension(WIDTH/2, camposCont.height/9));
			camposCont.add(ef);
		}
		
		camposCont.add(gravarRegistro);
		
		registros.add(camposCont);
		
		p1.add(header);
		p1.add(registros);
		
		///////////////////
		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		for(String col : colunasTabela)
			model.addColumn(col);
		table.setModel(model);
		
		tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(p2.width-WIDTH/66, p2.height-WIDTH/66));
		
		for(int i = 0; i < 45; i++)
			model.addRow(new String[] {i + ""});
		
		p2.add(tablePane);
		
		//////////////////
		
		frame.add(p1);
		frame.add(p2);
		
		frame.pack();
		frame.setVisible(true);
		
	}

}
