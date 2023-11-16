import java.sql.*;

public class ConectaMySQL {
	private final static String url = "jdbc:mysql://20.195.192.239:3306/projetopi";
	private final static String username = "aline-server";
	private final static String password = "aline12345";
	private Connection con;
	private Statement stmt;
	private ResultSet rs;
	// private String nome = null, telefone = null;

	public static void main(String args[]) {
		ConectaMySQL b = new ConectaMySQL();
		b.openDB();
		b.closeDB();
	}

	public Connection openDB() {
		try {
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			System.out.println("\nConexão estabelecida com sucesso!\n");			
		} catch (SQLException e) {
			System.out.println("\nNão foi possível estabelecer conexão " + e + "\n");
			System.exit(1);
		}
		return con;
	}

	public Connection closeDB() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("\nNão foi possível fechar conexão " + e + "\n");
			System.exit(1);
		}
		return con;
	}

	public void closePS(PreparedStatement ps, Connection cn) {
		try{
			if(con!=null)
				con.close();
			if(ps!=null)
				ps.close();
		}catch(SQLException e){
			System.out.println("\nNão foi possível fechar conexão " + e + "\n");
			System.exit(1);		
		}	
		System.out.println("Conexão encerrada.");  
	}

	public void closeST(Statement st, Connection cn) {
		try{
			if(con!=null)
				con.close();
			if(st!=null)
				st.close();
		}catch(SQLException e){
			System.out.println("\nNão foi possível fechar conexão " + e + "\n");
			System.exit(1);		
		}	
		System.out.println("Conexão encerrada.");  
	}


}
