import java.sql.*;
import javax.swing.JOptionPane;

public class Posto {
    private Localizacao localizacao;
    private String nome;

    public Posto(Localizacao localizacao, String nome) {
        this.localizacao = localizacao;
        this.nome = nome;
    }

    public void cadastrarPosto() {
        try {
            ConectaMySQL conexao = new ConectaMySQL();
            Connection cn = conexao.openDB();
            // abro uma conexão para inserir novos dados em minha tabela Alunos no banco
            PreparedStatement ps = cn.prepareStatement(
                    "INSERT INTO POSTO (Nome, Localizacao) VALUES (?, ?)");
            ps.setString(1, nome);
            ps.setString(2, localizacao.toString());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Novo posto cadastrado com sucesso!");
                conexao.closePS(ps, cn);
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao cadastrar o novo posto.");
            }
        } catch (

        SQLException status) {
            JOptionPane.showMessageDialog(null, "Falha ao realizar a operação.");
            status.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Confira se os dados estão corretos: \n nome=" + nome + "\n Endereço=" + localizacao;
    }
}
