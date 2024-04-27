
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

    public void imprimirPlanos(String query) throws ClassNotFoundException, SQLException{
        Statement s = c.createStatement();
        System.out.println("Listando Planos: ");
        ResultSet rs = s.executeQuery(query);
        while(rs.next())
            System.out.println(rs.getString("codigo")+" - "+rs.getString("nome") + " valor: " + rs.getString("valor") );
    }
    public void adicionarCartao(Cartao cartao, String cpfAluno) throws ClassNotFoundException, SQLException {
        PreparedStatement p = c.prepareStatement(
                "INSERT INTO cartoes (cpfaluno, numero, cvv, datavencimento) VALUES (?, ?, ?, ?)");
        p.setString(1, cpfAluno);
        p.setString(2, cartao.getNumero());
        p.setString(3, cartao.getCvv());
        p.setObject(4, cartao.getDataVencimento());
        p.execute();

    }
    public void adicionarAlunos(Aluno novoAluno) throws ClassNotFoundException, SQLException{
        Cartao cartao = new Cartao();

        cartao.setDadosScanner();

        PreparedStatement p = c.prepareStatement(
                "INSERT INTO alunos (cpf, nome, nascimento, numerocartao) VALUES (?, ?, ?, ?)");
        p.setString(1, novoAluno.getCpf());
        p.setString(2, novoAluno.getNome());
        p.setObject(3, novoAluno.getDataNascimento());
        p.setString(4, cartao.getNumero());
        p.execute();

        adicionarCartao(cartao, novoAluno.getCpf());
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

    public void assinarPlano() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);
        String cpfLocal;

        System.out.println("Digite seu cpf: ");
        cpfLocal = entrada.nextLine();

        Statement s = c.createStatement();
        cpfLocal = "\'" + cpfLocal + "\'";
        ResultSet rs = s.executeQuery("SELECT * FROM alunos WHERE cpf = " + cpfLocal);

        if(rs.next()){

            System.out.println("Escolha o o plano: ");
            imprimirPlanos("SELECT * FROM planos");
            String opcaoEscolhida = entrada.nextLine();

            rs = s.executeQuery("SELECT * FROM planos WHERE codigo = " + opcaoEscolhida);

            if(rs.next()){
                System.out.println("Assinando plano " + rs.getString("nome"));
                LocalDate dataInicio = LocalDate.now();
                LocalDate dataFim = dataInicio.plusDays(30);
                PreparedStatement p = c.prepareStatement(
                        "INSERT INTO planosativos (cpfaluno, plano, inicio, fim) VALUES (?, ?, ?, ?)");
                p.setString(1, cpfLocal );
                p.setString(2, rs.getString("nome"));
                p.setObject(3, dataInicio);
                p.setObject(4, dataFim);
                p.execute();
            } else{
                System.out.println("Plano nao existe.");
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }

    }
}