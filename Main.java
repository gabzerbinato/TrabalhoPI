import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import java.util.regex.PatternSyntaxException;
import javax.swing.*;
import javax.swing.table.*;

public class Main implements ActionListener {
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
	DefaultComboBoxModel<String> postoComboBox = new DefaultComboBoxModel<>(),
			veiculoComboBox = new DefaultComboBoxModel<>();
	JComboBox selecionarPosto, selecionarVeiculo, filtroDropdown;
	JTable table;
	JTextField filtroText;
	JScrollPane tablePane;
	EnterField kmAtual, qtd, preco, data;
	DefaultTableModel model = new DefaultTableModel();
	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

	static int WIDTH = 1000, HEIGHT = 500;
	static String[] colunasTabela = {
			"Data", "Veículo", "Valor total", "km/L"
	};
	static Color DEF = new Color(255, 255, 255);

	public static void main(String[] args) {
		new Main();

	}

	public Main() {
		frame = new Frame(WIDTH, HEIGHT);
		p1 = new Panel(FlowLayout.LEFT, WIDTH / 100 * 57, HEIGHT, 2, 2, true);
		p2 = new Panel(FlowLayout.CENTER, WIDTH / 100 * 40, HEIGHT, 5, 5, true);

		header = new Panel(FlowLayout.LEFT, p1.width - WIDTH / 100, p1.height / 100 * 30, -1, 0, false);

		novoPosto.addActionListener(this);
		novoVeiculo.addActionListener(this);
		gravarRegistro.addActionListener(this);
		filtrar.addActionListener(this);
		botoesNovoCadastro = new Panel(FlowLayout.LEFT, header.width / 2, header.height, 5, 15, false);
		botoesNovoCadastro.add(novoPosto);
		botoesNovoCadastro.add(novoVeiculo);

		logoContainer = new IconPanel(header.width / 2 - WIDTH / 66, header.height, "logo.png");
		logoContainer.setOpaque(false);
		logoContainer.setLayout(new FlowLayout(0, 25, 0));
		header.add(botoesNovoCadastro);
		header.add(logoContainer);
		novoPosto.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height / 3));
		novoVeiculo.setPreferredSize(new Dimension(botoesNovoCadastro.width - 20, botoesNovoCadastro.height / 3));

		registros = new Panel(FlowLayout.LEFT, p1.width - WIDTH / 100, p1.height / 100 * 68, 0, 0, true);
		registroHeader = new Panel(FlowLayout.CENTER, registros.width - 3, registros.height / 10, 0, FlowLayout.CENTER,
				true);
		registroHeader.setBackground(Color.gray);
		registrarAbastecimento = new JLabel("Registrar Abastecimento");
		registroHeader.add(registrarAbastecimento);

		registroLeft = new Panel(FlowLayout.CENTER, registros.width / 2, registros.height / 3, 5, 15, false);
		registroRight = new Panel(FlowLayout.CENTER, registros.width / 2 - WIDTH / 150, registros.height / 3, 5, 10,
				false);

		selecionarPosto = criarComboBox(selecionarPosto, "Selecionar Posto", postoComboBox);
		selecionarVeiculo = criarComboBox(selecionarVeiculo, "Selecionar Veículo", veiculoComboBox);
		registroLeft.add(selecionarPosto);
		registroLeft.add(selecionarVeiculo);

		tipoAbastecimento = new JLabel("Tipo de abastecimento");
		registroRight.add(tipoAbastecimento);
		alcool = new JRadioButton("Álcool");
		alcool.setPreferredSize(new Dimension(registroRight.width / 100 * 95, registroRight.height / 4));
		alcool.setOpaque(false);
		gasolina = new JRadioButton("Gasolina");
		gasolina.setPreferredSize(new Dimension(registroRight.width / 100 * 95, registroRight.height / 4));
		gasolina.setOpaque(false);
		bg.add(alcool);
		bg.add(gasolina);
		registroRight.add(alcool);
		registroRight.add(gasolina);

		camposCont = new Panel(FlowLayout.LEFT, registros.width - 5, registros.height / 100 * 70, 2, 10, false);

		kmAtual = new EnterField("km atual");
		qtd = new EnterField("quantidade");
		preco = new EnterField("preço / L");
		data = new EnterField("Data");

		EnterField[] campos = { kmAtual, qtd, preco, data };

		for (EnterField ef : campos) {
			ef.init(new Dimension(WIDTH / 2, camposCont.height / 9));
			camposCont.add(ef);
		}

		filtros = new Panel(FlowLayout.CENTER, p2.width / 100 * 98, p2.height / 10, 2, 0, false);

		filtrarPor = new JLabel("Filtrar por:");
		filtrarPor.setPreferredSize(new Dimension(filtros.width, filtros.height / 3));
		filtroDropdown = new JComboBox(new String[] { "Veículo", "Data" });
		filtroText = new JTextField();
		filtroText.setPreferredSize(new Dimension(filtros.width / 2, filtros.height / 2));
		filtrar.setFocusPainted(false);

		filtros.add(filtrarPor);
		filtros.add(filtroDropdown);
		filtros.add(filtroText);
		filtros.add(filtrar);

		///////////////////

		table = new JTable();

		table.setRowSorter(sorter);

		for (String col : colunasTabela)
			model.addColumn(col);
		table.setModel(model);

		tablePane = new JScrollPane(table);
		tablePane.setPreferredSize(new Dimension(p2.width / 100 * 98, p2.height / 100 * 85));

		model = atualizarTabela(model);

		registros.add(registroHeader);
		registros.add(registroLeft);
		registros.add(registroRight);

		camposCont.add(gravarRegistro);

		registros.add(camposCont);

		p1.add(header);
		p1.add(registros);
		p2.add(filtros);
		p2.add(tablePane);
		frame.add(p1);
		frame.add(p2);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int resposta = 1;
		if (e.getSource() == novoPosto) {
			while (resposta != 0) {
				String nome = JOptionPane.showInputDialog("Nome do posto:");
				String cep = JOptionPane.showInputDialog("Insira o cep:");
				String numeroStr = JOptionPane.showInputDialog("Insira o numero:");

				if (nome == null || cep == null || numeroStr == null) {
					resposta = 0;
					continue;
				}
				nome = nome.trim();
				cep = cep.trim();
				int numero = 0;

				try {
					numero = Integer.parseInt(numeroStr.trim());
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "O número deve ser um valor inteiro.");
					continue;
				}

				if (!nome.equals("") && !cep.equals("") && numero != 0) {
					Localizacao localizacao = new Localizacao(cep, numero);
					Posto posto = new Posto(localizacao, nome);
					resposta = JOptionPane.showConfirmDialog(null, posto);
					if (resposta == 0) {
						posto.cadastrarPosto();
						postoComboBox.addElement(nome + " : " + localizacao);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
				}
			}
		} else if (e.getSource() == novoVeiculo) {

			while (resposta != 0) {
				String placa = JOptionPane.showInputDialog("Digite a placa do veículo:");
				String modelo = JOptionPane.showInputDialog("Digite o modelo do veículo:");
				String cor = JOptionPane.showInputDialog("Digite a cor do veículo:");

				if (placa == null || modelo == null || cor == null) {
					resposta = 0;
					continue;
				}
				placa = placa.trim();
				modelo = modelo.trim();
				cor = cor.trim();

				if (!placa.equals("") && !modelo.equals("") && !cor.equals("")) {
					Veiculo novoVeiculo = new Veiculo(placa, modelo, cor);
					resposta = JOptionPane.showConfirmDialog(null, novoVeiculo);
					if (resposta == 0) {
						novoVeiculo.cadastrarVeiculo();
						veiculoComboBox.addElement(novoVeiculo.getPlaca());
					}
				} else {
					JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
				}
			}
		} else if (e.getSource() == gravarRegistro) { // kmAtual, qtd, preco, data;
			String posto = selecionarPosto.getSelectedItem().toString();
			String veiculo = selecionarVeiculo.getSelectedItem().toString();
			int km = Integer.parseInt(kmAtual.getText());
			int qtdAbastecida = Integer.parseInt(qtd.getText());
			double precoL = Double.parseDouble(preco.getText());
			String dt = data.getText();
			SimpleDateFormat dataRecebida = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat dataConvertida = new SimpleDateFormat("yyyy-MM-dd");
			try {
				java.util.Date date = dataRecebida.parse(dt);
				dt = dataConvertida.format(date);
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			String tipo = "";
			if (alcool.isSelected()) {
				tipo = "Alcool";
			} else if (gasolina.isSelected()) {
				tipo = "Gasolina";
			} else if (tipo.equals("")) {
				JOptionPane.showMessageDialog(null, "Selecione o tipo de combustivel");
			}
			if (posto.equals("Selecionar Posto")) {
				JOptionPane.showMessageDialog(null, "Selecione um posto!");
			} else if (veiculo.equals("Selecionar Veículo")) {
				JOptionPane.showMessageDialog(null, "Selecione um veículo!");
			} else {
				int indiceDoisPontos = posto.indexOf(":");
				posto = posto.substring(indiceDoisPontos + 1, posto.length()).trim();
				salvarDados(posto, veiculo, km, qtdAbastecida, precoL, dt, tipo);
				System.out.println(posto);
				model = atualizarTabela(model);
			}
		} else if (e.getSource() == filtrar) {
			String text = filtroText.getText();
			int escolha;
			if (filtroDropdown.getSelectedItem().toString().equals("Data")) {
			escolha = 0;
			} else {
			escolha = 1;
			}
			RowFilter<DefaultTableModel, Object> rf = RowFilter.regexFilter("(?i)" + text, escolha);
			sorter.setRowFilter(rf);
		}
	}

	

	private void salvarDados(String posto, String veiculo, int km, int qtdAbastecida, double preco, String dt,
			String tipo) {
		try {
			ConectaMySQL conexao = new ConectaMySQL();
			Connection cn = conexao.openDB();
			// abro uma conexão para inserir novos dados em minha tabela Alunos no banco
			PreparedStatement ps = cn.prepareStatement(
					"INSERT INTO ABASTECIMENTO (KT_Atual, Data, PlacaVeiculo, Tipo, qtdAbastecida, precoLitro, localizacaoPosto) VALUES (?, ?,?,?,?,?,?)");
			ps.setInt(1, km);
			ps.setString(2, dt);
			ps.setString(3, veiculo);
			ps.setString(4, tipo);
			ps.setInt(5, qtdAbastecida);
			ps.setDouble(6, preco);
			ps.setString(7, posto);

			int linhasAfetadas = ps.executeUpdate();
			if (linhasAfetadas > 0) {
				JOptionPane.showMessageDialog(null, "Novo registro inserido com sucesso!");
				conexao.closePS(ps, cn);
			} else {
				JOptionPane.showMessageDialog(null, "Falha realizar registro ou registro já existe.");
			}
		} catch (

		SQLException status) {
			JOptionPane.showMessageDialog(null, "Falha ao realizar a operação.");
			status.printStackTrace();
		}
	}

	public void criarBotao(JButton novoBotao, int largura, int altura) {
		novoBotao.addActionListener(this);
		novoBotao.setPreferredSize(new Dimension(largura, altura));
	}

	public JComboBox criarComboBox(JComboBox novoComboBox, String texto, DefaultComboBoxModel<String> modeloComboBox) {
		novoComboBox = new JComboBox();
		modeloComboBox.addElement(texto);
		try {
			ConectaMySQL conexao = new ConectaMySQL();
			Connection cn = conexao.openDB();
			Statement st = cn.createStatement();
			if (texto.equals("Selecionar Posto")) {
				ResultSet rs = st.executeQuery("SELECT * FROM POSTO");
				while (rs.next()) {
					String nome = rs.getString("Nome");
					String localizacao = rs.getString("Localizacao");
					modeloComboBox.addElement(nome + " : " + localizacao);
				}
			} else if (texto.equals("Selecionar Veículo")) {
				ResultSet rs = st.executeQuery("SELECT * FROM VEICULO");
				while (rs.next()) {
					String placa = rs.getString("Placa");
					modeloComboBox.addElement(placa);
				}
			}
			conexao.closeDB();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Falha ao realizar a operação.\nVerifique se o registro já existe!");
			// e.printStackTrace();
		}
		novoComboBox.setModel(modeloComboBox);
		novoComboBox.setPreferredSize(new Dimension(registroLeft.width / 100 * 95, registroLeft.height / 4));
		return novoComboBox;
	}

	public DefaultTableModel atualizarTabela(DefaultTableModel model) {
		while (model.getRowCount() > 0) {
			model.removeRow(0);
		}
		try {
			ConectaMySQL conexao = new ConectaMySQL();
			Connection cn = conexao.openDB();
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM ABASTECIMENTO");
			while (rs.next()) {
				String data = rs.getString("Data");
				String veiculo = rs.getString("PlacaVeiculo");
				Double valor = rs.getDouble("precoLitro");
				int qt = rs.getInt("qtdAbastecida");
				SimpleDateFormat dataConvertida = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat dataRecebida = new SimpleDateFormat("yyyy-MM-dd");
				try {
					java.util.Date date = dataRecebida.parse(data);
					data = dataConvertida.format(date);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				model.addRow(new Object[] { data, veiculo, "R$" + (valor * qt), " - " });
			}
			conexao.closeDB();
		} catch (SQLException e) {
			System.out.println("Falha ao realizar a operação.");
			e.printStackTrace();
		}

		return model;
	}

}