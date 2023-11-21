import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;

public class Janela extends JFrame implements ActionListener {
    JFrame frame;
    JPanel p1, p2, header, registros, botoesNovoCadastro, registroHeader, filtros, camposCont, registroRight,
            registroLeft;
    JLabel registrarAbastecimento, tipoAbastecimento, filtrarPor;
    JButton novoPosto, novoVeiculo, gravarRegistro, filtrar;
    ButtonGroup bg = new ButtonGroup();
    JRadioButton alcool, gasolina;
    JComboBox selecionarPosto, selecionarVeiculo, filtroDropdown;
    JTable table;
    JTextField filtroText;
    JScrollPane tablePane;
    DefaultTableModel model = new DefaultTableModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

    static int WIDTH = 1000, HEIGHT = 500;
    static String[] colunasTabela = { "Data", "Veículo", "Valor total", "km/L" };

    JTextField kmAtual;
    JTextField qtd;
    JTextField preco;
    public JTextField data;

    public static void main(String[] args) {
        new Janela();
    }

    public Janela() {
        frame = criarFrame(WIDTH, HEIGHT);
        p1 = criarPainel(FlowLayout.LEFT, WIDTH / 100 * 57, HEIGHT, 2, 2, true);
        p2 = criarPainel(FlowLayout.CENTER, WIDTH / 100 * 40, HEIGHT, 5, 5, true);
        header = criarPainel(FlowLayout.LEFT, p1.getWidth() - WIDTH / 100, p1.getHeight() / 100 * 30, -1, 0, false);

        header = criarPainel(FlowLayout.LEFT, p1.getWidth() - 7, p1.getHeight() / 100 * 30, -1, 0, false);

        System.out.println(header.getWidth());
        System.out.println(header.getHeight());
        botoesNovoCadastro = criarPainel(FlowLayout.LEFT, header.getWidth() / 2, header.getHeight() - 5, 5, 15, false);
        novoPosto = criarBotao(botoesNovoCadastro, botoesNovoCadastro.getWidth() - 20,
                botoesNovoCadastro.getHeight() / 3, "Novo Posto");
        novoVeiculo = criarBotao(botoesNovoCadastro, botoesNovoCadastro.getWidth() - 20,
                botoesNovoCadastro.getHeight() / 3, "Novo Veículo");
        botoesNovoCadastro.add(novoPosto);
        botoesNovoCadastro.add(novoVeiculo);

        JPanel campoImagem = criarPainel(FlowLayout.LEFT, (header.getWidth() / 2) - 1, header.getHeight() - 5, 0, 0,
                false);
        ImageIcon img = new ImageIcon("logo.png");
        img = new ImageIcon(img.getImage().getScaledInstance(campoImagem.getWidth() - 7, campoImagem.getHeight() - 7,
                Image.SCALE_FAST));
        JLabel imagem = new JLabel(img);
        header.add(botoesNovoCadastro);
        campoImagem.add(imagem);
        header.add(campoImagem);

        registros = criarPainel(FlowLayout.LEFT, p1.getWidth() - WIDTH / 100, p1.getHeight() / 100 * 68, 0, 0, true);
        registroHeader = criarPainel(FlowLayout.CENTER, registros.getWidth() - 3, registros.getHeight() / 10, 0,
                FlowLayout.CENTER, true);
        registroHeader.setBackground(Color.gray);
        registrarAbastecimento = new JLabel("Registrar Abastecimento");
        registroHeader.add(registrarAbastecimento);
        registroLeft = criarPainel(FlowLayout.CENTER, registros.getWidth() / 2, registros.getHeight() / 100 * 33, 5, 15,
                false);
        registroRight = criarPainel(FlowLayout.CENTER, registros.getWidth() / 2 - WIDTH / 150,
                registros.getHeight() / 100 * 35, 5, 10, false);

        selecionarVeiculo = new JComboBox<>();
        selecionarPosto = new JComboBox<>();
        selecionarVeiculo.addItem("Selecionar Veículo");
        selecionarPosto.addItem("Selecionar Posto");

        try {
            ConectaMySQL conexao = new ConectaMySQL();
            Connection cn = conexao.openDB();
            Statement st = cn.createStatement();
            ResultSet rsVeiculo = st.executeQuery("SELECT * FROM VEICULO");
            while (rsVeiculo.next()) {
                String placa = rsVeiculo.getString("Placa");
                selecionarVeiculo.addItem(placa);
            }
            ResultSet rsPostos = st.executeQuery("SELECT * FROM POSTO");
            while (rsPostos.next()) {
                String nome = rsPostos.getString("Nome");
                String localizacao = rsPostos.getString("Localizacao");
                selecionarPosto.addItem(nome + " : " + localizacao);
            }
            // conexao.closeDB();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao realizar a operação.");
        }

        selecionarPosto.setPreferredSize(new Dimension(registroLeft.getWidth() / 100 * 80, 25));
        selecionarVeiculo.setPreferredSize(new Dimension(registroLeft.getWidth() / 100 * 80, 25));

        registroLeft.add(selecionarPosto);
        registroLeft.add(selecionarVeiculo);

        tipoAbastecimento = new JLabel("Tipo de abastecimento");
        registroRight.add(tipoAbastecimento);
        alcool = new JRadioButton("Álcool");
        alcool.setPreferredSize(new Dimension(registroRight.getWidth() / 100 * 95, registroRight.getHeight() / 4));
        alcool.setOpaque(false);
        gasolina = new JRadioButton("Gasolina");
        gasolina.setPreferredSize(new Dimension(registroRight.getWidth() / 100 * 95, registroRight.getHeight() / 4));
        gasolina.setOpaque(false);
        bg.add(alcool);
        bg.add(gasolina);
        registroRight.add(alcool);
        registroRight.add(gasolina);

        camposCont = criarPainel(FlowLayout.RIGHT, registros.getWidth() - 5, registros.getHeight() / 100 * 65, 2, 10,
                false);

        kmAtual = novoTextFiel(camposCont, "KM Atual");
        qtd = novoTextFiel(camposCont, "quantidade");
        preco = novoTextFiel(camposCont, "preço / L");
        data = novoTextFiel(camposCont, "Data");

        gravarRegistro = criarBotao(camposCont, 150, 25, "Gravar Registro");
        camposCont.add(gravarRegistro);

        registros.add(registroHeader);
        registros.add(registroLeft);
        registros.add(registroRight);
        registros.add(camposCont);

        filtros = criarPainel(FlowLayout.CENTER, p2.getWidth() / 100 * 98, p2.getHeight() / 10, 2, 0, false);

        filtrarPor = new JLabel("Filtrar por:");
        filtroDropdown = new JComboBox(new String[] { "Veículo", "Data" });
        filtroText = new JTextField();
        filtroText.setPreferredSize(new Dimension(filtros.getWidth() / 3, filtros.getHeight() / 2));
        filtrar = criarBotao(filtros, 80, filtros.getHeight() / 2, "Filtrar");

        filtros.add(filtrarPor);
        filtros.add(filtroDropdown);
        filtros.add(filtroText);
        filtros.add(filtrar);

        table = new JTable();

        table.setRowSorter(sorter);

        for (String col : colunasTabela)
            model.addColumn(col);
        table.setModel(model);

        tablePane = new JScrollPane(table);
        tablePane.setPreferredSize(new Dimension(p2.getWidth() / 100 * 98, p2.getHeight() / 100 * 85));

        model = atualizarTabela(model);

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
                        selecionarPosto.addItem(nome + " : " + localizacao);
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
                        selecionarVeiculo.addItem(novoVeiculo.getPlaca());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos!");
                }
            }
        } else if (e.getSource() == gravarRegistro) {

            String posto = selecionarPosto.getSelectedItem().toString();
            String veiculo = selecionarVeiculo.getSelectedItem().toString();
            int km = Integer.parseInt(kmAtual.getText());
            double ultimaKM = calcularUltimaKM(veiculo);
            System.out.println(ultimaKM);
            if(km<ultimaKM){
                System.out.println("km menor que ultimakm");
                return;
            }
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
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO ABASTECIMENTO (KT_Atual, Data, PlacaVeiculo, Tipo, qtdAbastecida, precoLitro, localizacaoPosto,KM_Litros) VALUES (?, ?,?,?,?,?,?,?)");
            ps.setInt(1, km);
            ps.setString(2, dt);
            ps.setString(3, veiculo);
            ps.setString(4, tipo);
            ps.setInt(5, qtdAbastecida);
            ps.setDouble(6, preco);
            ps.setString(7, posto);

            double ultimaKM = calcularUltimaKM(veiculo);
            if (ultimaKM > 0) {
                double mediaConsumo = (km-ultimaKM) / qtdAbastecida;
                ps.setDouble(8, mediaConsumo);
            }else{
                ps.setDouble(8, 0);
            }

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

    public JButton criarBotao(JPanel painel, int largura, int altura, String texto) {
        JButton novoBotao = new JButton(texto);
        novoBotao.addActionListener(this);
        painel.add(novoBotao);
        novoBotao.setPreferredSize(new Dimension(largura, altura));
        return novoBotao;
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
                double kml = rs.getDouble("KM_Litros");
                SimpleDateFormat dataConvertida = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dataRecebida = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    java.util.Date date = dataRecebida.parse(data);
                    data = dataConvertida.format(date);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                model.addRow(new Object[] { data, veiculo, "R$" + (valor * qt), kml});
            }

            conexao.closeDB();
        } catch (SQLException e) {
            System.out.println("Falha ao realizar a operação.");
            e.printStackTrace();
        }

        return model;
    }

    private JFrame criarFrame(int w, int h) {
        JFrame novoFrame = new JFrame();
        novoFrame.setSize(new Dimension(w, h));
        novoFrame.setResizable(false);
        novoFrame.getContentPane().setBackground(new Color(255, 255, 255));
        novoFrame.setLocationRelativeTo(null);
        novoFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        novoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        novoFrame.setVisible(true);
        return novoFrame;
    }

    private JPanel criarPainel(int posicao, int w, int h, int hgap, int vgap, boolean borda) {
        JPanel novoPainel = new JPanel();
        novoPainel.setPreferredSize(new Dimension(w, h));
        novoPainel.setSize(w, h);
        novoPainel.setLayout(new FlowLayout(posicao, hgap, vgap));
        novoPainel.setBackground(Color.WHITE);
        if (borda) {
            novoPainel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        }
        return novoPainel;
    }

    private JTextField novoTextFiel(JPanel painel, String texto) {
        JPanel novoPainel = criarPainel(FlowLayout.LEFT, painel.getWidth() - 7, 30, 10, 0, false);

        JTextField novo = new JTextField();
        JLabel nome = new JLabel(texto);
        nome.setPreferredSize(new Dimension(80, 25));
        novo.setPreferredSize(new Dimension(300, 25));
        novoPainel.add(nome);
        novoPainel.add(novo);
        painel.add(novoPainel);

        return novo;
    }

    private double calcularUltimaKM(String veiculo) {
        try {
            ConectaMySQL conexao = new ConectaMySQL();
            Connection cn = conexao.openDB();
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery("SELECT KT_Atual FROM ABASTECIMENTO WHERE PlacaVeiculo = '" + veiculo
                    + "' ORDER BY Data DESC LIMIT 1");

            if (rs.next()) {
                return rs.getDouble("KT_Atual");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Falha ao realizar a operação.");
            e.printStackTrace();
            return 0;
        }
    }

}