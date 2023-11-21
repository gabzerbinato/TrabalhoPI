import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Veiculo {
    private String placa;
    private String modelo;
    private String cor;
    int kml;

    public String getPlaca() {
        return placa;
    }

    public Veiculo(String placa, String modelo, String cor) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
    }

    public void cadastrarVeiculo() {
        try {
            ConectaMySQL conexao = new ConectaMySQL();
            Connection cn = conexao.openDB();
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO VEICULO (Placa, Modelo, Cor) VALUES (?, ?, ?)");
            ps.setString(1, placa);
            ps.setString(2, modelo);
            ps.setString(3, cor);

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Novo veículo cadastrado com sucesso!");
                conexao.closePS(ps, cn);
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar o novo veículo.");
            }
        } catch (
        SQLException e) {
            JOptionPane.showMessageDialog(null, "Falha ao realizar a operação.");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Confira se os dados estão corretos: \n" + "placa=" + placa + "\n modelo=" + modelo + "\n cor=" + cor;
    }

    

}
