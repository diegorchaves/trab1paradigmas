
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
        System.out.println("Listando Alunos: ");
        ResultSet rs = s.executeQuery(query);
        while(rs.next())
            System.out.println(rs.getString("cpf")+" - "+rs.getString("nome"));
    }
    public void imprimirMusculos(String query) throws ClassNotFoundException, SQLException{
        Statement s = c.createStatement();
        System.out.println("Listando Musculos: ");
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
    public void imprimirExercicios(String query) throws ClassNotFoundException, SQLException{
        Statement s = c.createStatement();
        System.out.println("Listando Exercicos: ");
        ResultSet rs = s.executeQuery(query);
        while(rs.next())
            System.out.println(rs.getString("codigo")+" - "+rs.getString("nome"));

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
                "INSERT INTO exercicios (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS  );
        p.setString(1, novoExercicio.getNome());
        p.execute();

        ResultSet rs = p.getGeneratedKeys();
        int codigoExercicio = 0;
        if (rs.next()) {
            codigoExercicio = rs.getInt(1);
        }

        adicionarMusculosAtivados(codigoExercicio);
    }
    public void adicionarMusculosAtivados(int codigoExercicio) throws ClassNotFoundException, SQLException{
        var entrada = new Scanner(System.in);
        int opcaoEscolhida;

        if(codigoExercicio == 0){
            System.out.println("Exercicio nao encontrado");
            return;
        }

        do{
            System.out.println("0 - Sair");
            imprimirMusculos("SELECT * FROM musculos");
            opcaoEscolhida = buscarMusculo();
            if(opcaoEscolhida != 0 ){
                PreparedStatement p = c.prepareStatement(
                        "INSERT INTO exerciciomusculos (codexe, codmusc) VALUES (?, ?)");
                p.setInt(1, codigoExercicio );
                p.setInt(2, opcaoEscolhida );
                p.execute();
            }
        } while (opcaoEscolhida != 0);
    }
    public void adicionarExerciciosTreino(int codigoTreino) throws ClassNotFoundException, SQLException{
        var entrada = new Scanner(System.in);
        int opcaoEscolhida;

        if(codigoTreino == 0){
            System.out.println("Treino nao encontrado");
            return;
        }

        do{
            System.out.println("0 - Sair");
            imprimirMusculos("SELECT * FROM exercicios");
            opcaoEscolhida = buscarExercicio();
            if(opcaoEscolhida != 0 ){
                TreinoExercicio treinoExercicio = new TreinoExercicio();
                treinoExercicio.setDadosScanner();

                PreparedStatement p = c.prepareStatement(
                        "INSERT INTO treinoexercicios (codtreino, codexe, numero_series, min_repeticoes, max_repeticoes, carga, tempo_descanso) VALUES (?, ?, ?, ?, ?, ?, ?)");
                p.setInt(1, codigoTreino );
                p.setInt(2, opcaoEscolhida );
                p.setInt(3, treinoExercicio.getSeries() );
                p.setInt(4, treinoExercicio.getMinRepeticoes() );
                p.setInt(5, treinoExercicio.getMaxRepeticoes() );
                p.setDouble(6, treinoExercicio.getCarga());
                p.setDouble(7, treinoExercicio.getTempoDescanso() );
                p.execute();
            }
        } while (opcaoEscolhida != 0);
    }
    public void adicionarTreino() throws ClassNotFoundException, SQLException{

        String cpfLocal = buscarAluno();

        if(cpfLocal != null){
            Scanner entrada = new Scanner(System.in);
            System.out.println("Digite o nome do treino: ");
            String nomeLocal = entrada.nextLine();
            PreparedStatement p = c.prepareStatement(
                    "INSERT INTO treinos (alunocpf, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS );
            p.setString(1, cpfLocal);
            p.setString(2, nomeLocal);
            p.execute();

            ResultSet rs = p.getGeneratedKeys();
            int codigoTreino = 0;
            if (rs.next()) {
                codigoTreino = rs.getInt(1);
            }

            adicionarExerciciosTreino(codigoTreino);
        }

    }
    public void assinarPlano() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);

        String cpfLocal = buscarAluno();


        if(cpfLocal != null){

            System.out.println("Escolha o plano: ");
            imprimirPlanos("SELECT * FROM planos");
            String opcaoEscolhida = entrada.nextLine();

            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            ResultSet rs = s.executeQuery("SELECT * FROM planos WHERE codigo = " + opcaoEscolhida);

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
        }

    }
    public String buscarAluno() throws SQLException, ClassNotFoundException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Deseja buscar o aluno por nome (1) ou cpf (2)?");
        int opcao = entrada.nextInt();
        entrada.nextLine();
        if (opcao == 1){
            System.out.println("Digite o nome do aluno: ");
            String nomeLocal = entrada.nextLine();
            nomeLocal = "\'" + nomeLocal + "\'";
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ResultSet rs = s.executeQuery("SELECT * FROM alunos WHERE nome = " + nomeLocal);
            if (rs.next()){
                System.out.println("Aluno "+rs.getString("nome")+" encontrado.");
                return rs.getString("cpf");
            }
            else {
                System.out.println("Aluno nao encontrado.");
                return null;
            }
        } else if (opcao == 2) {
            System.out.println("Digite o cpf do aluno: ");
            String cpfLocal = entrada.nextLine();
            cpfLocal = "\'" + cpfLocal + "\'";
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ResultSet rs = s.executeQuery("SELECT * FROM alunos WHERE cpf = " + cpfLocal);
            if (rs.next()){
                System.out.println("Aluno "+rs.getString("nome")+" encontrado.");
                return rs.getString("cpf");
            }
            else {
                System.out.println("Aluno nao encontrado.");
                return null;
            }
        } else {
            System.out.println("Opcao invalida.");
            return null;
        }
    }
    public int buscarMusculo() throws ClassNotFoundException, SQLException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o codigo do musculo");
        int opcao = entrada.nextInt();
        entrada.nextLine();

        if(opcao != 0){
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ResultSet rs = s.executeQuery("SELECT * FROM musculos WHERE codigo = " + opcao);
            if (rs.next()) {
                System.out.println("Musculo " + rs.getString("nome") + " encontrado.");
                return rs.getInt("codigo");
            } else {
                System.out.println("Musculo nao encontrado.");
                return 0;
            }
        }

        return 0;
    }
    public int buscarExercicio() throws ClassNotFoundException, SQLException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Digite o codigo do exercicio");
        int opcao = entrada.nextInt();
        entrada.nextLine();

        if(opcao != 0){
            Statement s = null;
            try {
                s = c.createStatement();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ResultSet rs = s.executeQuery("SELECT * FROM exercicios WHERE codigo = " + opcao);
            if (rs.next()) {
                System.out.println("Exercicio " + rs.getString("nome") + " encontrado.");
                return rs.getInt("codigo");
            } else {
                System.out.println("Exercicio nao encontrado.");
                return 0;
            }
        }

        return 0;
    }
}