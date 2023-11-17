import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import main.*;

public class Janela {
	
	Main main;
	Frame frame;
	Panel p1, p2,
		  header, registros,
		  botoesNovoCadastro, logoContainer,
		  registroHeader, registroLeft, registroRight,
		  camposCont, filtros;
	JLabel registrarAbastecimento, tipoAbastecimento, filtrarPor;
	JButton novoPosto = new JButton("Novo posto"), 
			novoVeiculo = new JButton("Novo veículo"),
			gravarRegistro = new JButton("Gravar registro"),
			filtrar = new JButton("Filtrar");
	ButtonGroup bg = new ButtonGroup();
	JRadioButton alcool, gasolina;
	JComboBox selecionarPosto, selecionarVeiculo, filtroDropdown;
	JTable table;
	JScrollPane tablePane;
	JTextField filtroText;
	EnterField kmAtual, qtd, preco, data;

	public Janela(Main m) {
		
		this.main = m;
		
		frame = new Frame(Main.WIDTH, Main.HEIGHT);
		p1 = new Panel(Main.WIDTH/100*57, Main.HEIGHT);
		p1.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
		p1.setBackground(Main.DEF);
		p2 = new Panel(Main.WIDTH/100*40, Main.HEIGHT);
		p2.setLayout(new FlowLayout(0, 5, 5));
		
		header = new Panel(p1.width-Main.WIDTH/100, p1.height/100*30);
		//header.setBackground(Color.red);
		header.setOpaque(false);
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		registros = new Panel(p1.width-Main.WIDTH/100, p1.height/100*65);
		registros.setLayout(new FlowLayout(FlowLayout.LEFT, -1, 0));
		registros.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		registros.setBackground(Main.DEF);
		
		botoesNovoCadastro = new Panel(header.width/2, header.height);
		botoesNovoCadastro.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 15));
		botoesNovoCadastro.setOpaque(false);
		botoesNovoCadastro.add(novoPosto);
		botoesNovoCadastro.add(novoVeiculo);
		
		logoContainer = new IconPanel(header.width/2 - Main.WIDTH/66, header.height, System.getProperty("user.dir") + "\\src\\res\\" + "IMG-20231113-WA0047-removebg-preview.png");
		logoContainer.setOpaque(false);
		logoContainer.setLayout(new FlowLayout(0, 25, 0));
		
		header.add(botoesNovoCadastro);
		header.add(logoContainer);
		
		novoPosto.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		novoPosto.setFocusPainted(false);
		novoVeiculo.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height/3));
		novoVeiculo.setFocusPainted(false);
		
		registroHeader = new Panel(registros.width - Main.WIDTH/132, registros.height/10);
		registroHeader.setBackground(Color.gray);
		registroHeader.setBorder(BorderFactory.createLineBorder(Color.black));
		registrarAbastecimento = new JLabel("Registrar Abastecimento");
		registroHeader.add(registrarAbastecimento);
		
		registroLeft = new Panel(registros.width/2, registros.height/3);
		//registroLeft.setBackground(Color.orange);
		registroLeft.setOpaque(false);
		registroLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
		registroRight = new Panel(registros.width/2 - Main.WIDTH/150, registros.height/3);
		registroRight.setBackground(Main.DEF);
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
		alcool.setFocusPainted(false);
		gasolina = new JRadioButton("Gasolina");
		gasolina.setPreferredSize(new Dimension(registroRight.width/100*95, registroRight.height/4));
		gasolina.setOpaque(false);
		gasolina.setFocusPainted(false);
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
			//ef.setPreferredSize(new Dimension(Main.WIDTH/2, camposCont.height/9));
			ef.init(new Dimension(Main.WIDTH/2, camposCont.height/9));
			camposCont.add(ef);
		}
		
		gravarRegistro.setFocusPainted(false);
		gravarRegistro.setName("gravar");
		camposCont.add(gravarRegistro);
		
		registros.add(camposCont);
		
		p1.add(header);
		p1.add(registros);
		
		novoPosto.addActionListener(m.al);
		novoPosto.setName("novoPosto");
		novoVeiculo.addActionListener(m.al);
		novoVeiculo.setName("novoVeiculo");
		gravarRegistro.addActionListener(m.al);
		
		///////////////////
		
		filtros = new Panel(p2.width-Main.WIDTH/66, p2.height/10);
		filtros.setOpaque(false);
		filtros.setLayout(new FlowLayout(0));
		
		filtrarPor = new JLabel("Filtrar por:");
		filtrarPor.setPreferredSize(new Dimension(filtros.width, filtros.height/3));
		filtroDropdown = new JComboBox(new String[] {"Veículo", "Data"});
		filtroText = new JTextField();
		filtroText.setPreferredSize(new Dimension(filtros.width/2, filtros.height/3 + Main.WIDTH/134));
		filtrar.setFocusPainted(false);
		
		filtros.add(filtrarPor);
		filtros.add(filtroDropdown);
		filtros.add(filtroText);
		filtros.add(filtrar);
		
		table = new JTable();
		DefaultTableModel model = new DefaultTableModel();
		for(String col : Main.colunasTabela)
			model.addColumn(col);
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		
		tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(p2.width-Main.WIDTH/66, p2.height-Main.WIDTH/66 - p2.height/10));
		
		for(int i = 0; i < 45; i++)
			model.addRow(new String[] {i == 12 ? 135 + "" : i + ""});
		
		p2.add(filtros);
		p2.add(tablePane);
		
		//////////////////
		
		frame.add(p1);
		frame.add(p2);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	private class Frame extends JFrame{
		public Frame(int w, int h) {
			super();
			this.setSize(new Dimension(w, h));
			this.setResizable(false);
			this.getContentPane().setBackground(Main.DEF);
			this.setLocationRelativeTo(null);
			this.setLayout(new FlowLayout(5, 5, FlowLayout.LEFT));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//this.setLayout(new GridLayout(1,2));
			this.setVisible(true);
		}
	}
	
	private class Panel extends JPanel{
		public int width, height;
		public Panel(int w, int h) {
			super();
			this.width = w;
			this.height = h;
			this.setPreferredSize(new Dimension(w, h));
			//this.setBorder(BorderFactory.createLineBorder(Color.green));
			this.setBackground(Color.gray);
		}
	}
	
	private class EnterField extends JPanel{
		
		JTextField texto;
		JLabel nome;
		Dimension di;

		public EnterField(String t) {
			super();
			
			this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
			this.setName(t);
			this.setBackground(Main.DEF);
			//this.setBorder(BorderFactory.createLineBorder(Main.Main.DEF.darker(), 1));
			
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

	private class IconPanel extends Panel{
		JLabel label;
		ImageIcon img;
		
		public IconPanel(int w, int h, String path) {
			super(w, h);
			img = new ImageIcon(path);
			img = new ImageIcon(img.getImage().getScaledInstance(w-w/5, h-h/5, Image.SCALE_FAST));
			label = new JLabel(img);
			add(label);
		}
	}
	
}
