import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import graphics.*;

public class Main {
	
	static public int WIDTH = 1000, HEIGHT = 600;
	static public String[] colunasTabela = {
		"Data", "Veículo", "Valor total"	
	};
	static public Color DEF = new Color(235,235,235);
	static ConectaMySQL conexao = new ConectaMySQL();
	
	public Janela j;
	
	
		public static void main(String[] args) {
			new Main();
		}
	
		public Main() {
			j = new Janela(this);
		}
	
	
	public ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = ((JButton)e.getSource()).getName();
			
			switch(name) {
			case "novoPosto":
				addNovo(name);
				break;
			case "novoVeiculo":
				addNovo(name);
				break;
			case "gravar":
				gravarNovoRegistro();
				break;
			}
		}
	};
		
		private String ask(String t) {return JOptionPane.showInputDialog(t);}
		
		private void addNovo(String id) {
			if(id.equals("novoPosto")) {
				Posto posto = new Posto(ask("Insira o nome do posto:"), 
										ask("Insira a localização do posto:"));
				p(posto.nome);
			} else if(id.equals("novoVeiculo")) {
				Veiculo veiculo = new Veiculo(ask("Insira o modelo do veículo:"),
											  ask("Insira a placa do veículo:"),
											  ask("Insira a cor do veículo:"));
				// ADICIONAR AO BANCO DE DADOS
			}
			
			update();
			
		}
		
		private void update() {
			// CONSULTAR BANCO DE DADOS 
			DefaultComboBoxModel<String> modelPosto = new DefaultComboBoxModel<>();
			//selecionarPosto.setModel(modelPosto);
			
			// CONSULTAR BANCO DE DADOS
			DefaultComboBoxModel<String> modelVeiculo = new DefaultComboBoxModel<>();
			//selecionarVeiculo.setModel(modelVeiculo);
		}
		
		public void gravarNovoRegistro() {
			if(j.kmAtual.getText().equals("") ||
			   j.preco.getText().equals("") ||
			   j.qtd.getText().equals("") ||
			   j.data.getText().equals("") ||
			   (!j.alcool.isSelected() &&
				!j.gasolina.isSelected()) ||
			   j.selecionarPosto.getSelectedItem().equals("Selecionar posto") ||
			   j.selecionarVeiculo.getSelectedItem().equals("Selecionar veículo")) {
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!");
			}
		}
		
		private <T> void p(T o) {System.out.println(o);}

}
