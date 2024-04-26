package trabalho1paradigmas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {
		static Connection c;
	public static void conexaoBD() throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection(
				"jdbc:postgresql://localhost/academia",
				"postgres",
				"123456");
		
	}
	public void imprimirAlunos(String query) throws ClassNotFoundException, SQLException{
		Statement s = c.createStatement();
		System.out.println("Listando: ");
		ResultSet rs = s.executeQuery(query);
		while(rs.next())
			System.out.println(rs.getString("cpf")+" - "+rs.getString("nome"));
	}
	public void adicionarAlunos(String cpf, String nome) throws ClassNotFoundException, SQLException{
		PreparedStatement p = c.prepareStatement(
				"INSERT INTO alunos (cpf, nome) VALUES (?, ?)");
		p.setString(1, cpf);
		p.setString(2, nome);
		p.execute();
	}
	
}
