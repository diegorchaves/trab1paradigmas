
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;

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

    public void imprimirMusculos(String query) throws ClassNotFoundException, SQLException{
        Statement s = c.createStatement();
        System.out.println("Listando: ");
        ResultSet rs = s.executeQuery(query);
        while(rs.next())
            System.out.println(rs.getString("codigo")+" - "+rs.getString("nome"));
    }
    public void adicionarAlunos(Aluno novoAluno) throws ClassNotFoundException, SQLException{
        PreparedStatement p = c.prepareStatement(
                "INSERT INTO alunos (cpf, nome, nascimento) VALUES (?, ?, ?)");
        p.setString(1, novoAluno.getCpf());
        p.setString(2, novoAluno.getNome());
        p.setObject(3, novoAluno.getDataNascimento());
        p.execute();
    }
    public void adicionarPlanos(Plano novoPlano) throws ClassNotFoundException, SQLException{
        PreparedStatement p = c.prepareStatement(
                "INSERT INTO planos (nome, valor) VALUES (?, ?)");
        p.setString(1, novoPlano.getNome());
        p.setDouble(2, novoPlano.getValorMensal());
        p.execute();
    }

    public void adicionarExercicios(Exercicio novoExercicio) throws ClassNotFoundException, SQLException{
        PreparedStatement p = c.prepareStatement(
                "INSERT INTO exercicios (nome) VALUES (?)");
        p.setString(1, novoExercicio.getNome());
        p.execute();

        adicionarMusculosAtivados(novoExercicio);
    }

    public void adicionarMusculosAtivados(Exercicio exercicio) throws ClassNotFoundException, SQLException{
        var entrada = new Scanner(System.in);
        int opcaoEscolhida;
        int codigoExercicio = 0;

        Statement s = c.createStatement();
        String nome = "\'" + exercicio.getNome() + "\'";
        ResultSet rs = s.executeQuery("SELECT * FROM exercicios WHERE nome = " + nome);
        while(rs.next()) {
             codigoExercicio = rs.getInt("codigo");
        }

        if(codigoExercicio == 0){
            System.out.println("Exercicio nao encontrado");
            return;
        }

        do{
            System.out.println("Escolha o musculo ativado: \n0 - Sair");
            imprimirMusculos("SELECT * FROM musculos");
            opcaoEscolhida = entrada.nextInt();
            if(opcaoEscolhida != 0 ){
                PreparedStatement p = c.prepareStatement(
                        "INSERT INTO exerciciomusculos (codex, codmusc) VALUES (?, ?)");
                p.setInt(1, codigoExercicio );
                p.setInt(2, opcaoEscolhida );
                p.execute();
            }
        } while (opcaoEscolhida != 0);
    }
}