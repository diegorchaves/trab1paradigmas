
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
    public void imprimirTreinoExercicios(String query) throws ClassNotFoundException, SQLException{
        Statement s = c.createStatement();

        ResultSet rs = s.executeQuery(query);
        if(rs.next()){
            System.out.println("Detalhes do treino: ");
            do{

                Statement s2 = c.createStatement();
                ResultSet rs2 = s2.executeQuery("SELECT * FROM exercicios WHERE codigo = "+ rs.getInt("codexe"));
                System.out.println("Listando Exercicios do Treino");
                while(rs2.next()){
                    System.out.println("\nDetalhes do exercicio "+rs2.getString("nome"));
                }

                System.out.println("Series: " + rs.getInt("numero_series")+
                        "\nMin repeticoes: "+rs.getInt("min_repeticoes")+
                        "\nMax repeticoes: "+rs.getInt("max_repeticoes")+
                        "\nCarga: "+rs.getInt("carga")+ "Kg" +
                        "\nTempo de Descanso: "+rs.getInt("tempo_descanso") + " minutos"
                );
            }while (rs.next());
            System.out.println("\n");
        }else{
            System.out.println("Nenhum Treino encontrado\n");
        }
    }
    public int imprimirTreinos(String query) throws ClassNotFoundException, SQLException{
        int numeroTreinos = 0;
        Statement s = c.createStatement();
        System.out.println("Listando Treinos: ");
        ResultSet rs = s.executeQuery(query);
        while(rs.next()){
            numeroTreinos++;
            System.out.println(rs.getString("codigo")+" - "+rs.getString("nome"));
        }

        return numeroTreinos;

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
            imprimirExercicios("SELECT * FROM exercicios");
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
    public void buscarTreino() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);
        String cpfLocal =  "\'" + buscarAluno() + "\'";
        int codigoTreino = 0;

        if(imprimirTreinos("SELECT * FROM treinos WHERE alunocpf = " + cpfLocal) != 0){
            System.out.println("Digite o codigo do treino que deseja ver os detalhes: ");
            codigoTreino = entrada.nextInt();
            imprimirTreinoExercicios("SELECT * FROM treinoexercicios WHERE codtreino = " + codigoTreino);
        }else{
            System.out.println("Nenhum treino encontrado\n");
        }

    }
    public void excluirTreino() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);
        String cpfLocal =  "\'" + buscarAluno() + "\'";
        int codigoTreino = 0;

        if(imprimirTreinos("SELECT * FROM treinos WHERE alunocpf = " + cpfLocal) != 0){
            System.out.println("Digite o codigo do treino que deseja excluir: ");
            codigoTreino = entrada.nextInt();
            Statement s = c.createStatement();

            int linhasAfetadas = s.executeUpdate("DELETE FROM treinoexercicios WHERE codTreino = " + codigoTreino);
            if(linhasAfetadas > 0){
                System.out.println("Exercicios do treino excluídos com sucesso.");
            }else{
                System.out.println("Não foi possível excluir os exercicios do treino.");
            }

            linhasAfetadas = s.executeUpdate("DELETE FROM treinos WHERE codigo = " + codigoTreino);
            if(linhasAfetadas > 0){
                System.out.println("Treino excluído com sucesso.");
            }else{
                System.out.println("Não foi possível excluir o treino.");
            }

        }else{
            System.out.println("Nenhum treino encontrado");
        }
    }
    public void alterarTreino() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);
        String cpfLocal =  "\'" + buscarAluno() + "\'";
        int codigoTreino = 0;
        String novoNome = "";
        int opcao = 1;
        int codigoExe = 0;

        System.out.println("Voce deseja alterar o nome do treino(1) ou os exercicios(2)?");
        opcao = entrada.nextInt();

        if(imprimirTreinos("SELECT * FROM treinos WHERE alunocpf = " + cpfLocal) != 0){
            System.out.println("Digite o codigo do treino que deseja alterar: ");
            codigoTreino = entrada.nextInt();

            if(opcao == 1){
                System.out.println("Digite o novo nome do treino: ");
                entrada = new Scanner(System.in);
                novoNome = "\'" + entrada.nextLine() + "\'";

                Statement s = c.createStatement();
                int linhasAfetadas = s.executeUpdate("UPDATE treinos SET nome = " + novoNome + " WHERE codigo = " + codigoTreino);
                if(linhasAfetadas > 0){
                    System.out.println("Treino alterado com sucesso.");
                }else{
                    System.out.println("Não foi possível alterar o treino.");
                }
            }else{
                do{
                    Statement s = c.createStatement();
                    ResultSet rs = s.executeQuery("SELECT * FROM treinoexercicios WHERE codtreino = " + codigoTreino);
                    if(rs.next()){
                        do{
                            Statement s2 = c.createStatement();
                            ResultSet rs2 = s2.executeQuery("SELECT * FROM exercicios WHERE codigo = "+ rs.getInt("codexe"));
                            while(rs2.next()){
                                System.out.println(rs2.getInt("codigo")+" - "+rs2.getString("nome"));
                            }
                        }while (rs.next());
                    }else{
                        System.out.println("Nenhum Treino encontrado\n");
                    }

                    System.out.println("Digite o codigo do exercicio que deseja alterar ou 0 p/ sair: ");
                    codigoExe = entrada.nextInt();
                    if(codigoExe == 0) return;

                    int series;
                    int min_rep;
                    int max_rep;
                    double carga;
                    double tempo;

                    System.out.println("Digite o novo numero de series : ");
                    series = entrada.nextInt();
                    System.out.println("Digite o novo numero minimo de repeticoes : ");
                    min_rep = entrada.nextInt();
                    System.out.println("Digite o novo numero maximo de repeticoes : ");
                    max_rep = entrada.nextInt();
                    System.out.println("Digite a nova carga : ");
                    carga = entrada.nextDouble();
                    System.out.println("Digite o novo tempo de descanso : ");
                    tempo = entrada.nextDouble();

                    int linhasAfetadas = s.executeUpdate("UPDATE treinoexercicios SET numero_series = " + series + ", min_repeticoes =" + min_rep + ", max_repeticoes="+ max_rep + ", carga=" + carga + ", tempo_descanso =" + tempo + " WHERE codexe = " + codigoExe);
                    if(linhasAfetadas > 0){
                        System.out.println("Exercicio do treino alterado com sucesso.");
                    }else{
                        System.out.println("Não foi possível alterar o exercicio do treino.");
                    }

                } while (codigoExe != 0);
            }
        }else{
            System.out.println("Nenhum treino encontrado");
        }
    }
    public void excluirExerciciosTreino() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);
        String cpfLocal =  "\'" + buscarAluno() + "\'";
        int codigoTreino = 0;
        int codigoExe = 0;

        if(imprimirTreinos("SELECT * FROM treinos WHERE alunocpf = " + cpfLocal) != 0){
            System.out.println("Digite o codigo do treino que deseja excluir exercicios: ");
            codigoTreino = entrada.nextInt();

            do{

                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM treinoexercicios WHERE codtreino = " + codigoTreino);
                if(rs.next()){
                    do{

                        Statement s2 = c.createStatement();
                        ResultSet rs2 = s2.executeQuery("SELECT * FROM exercicios WHERE codigo = "+ rs.getInt("codexe"));
                        while(rs2.next()){
                            System.out.println(rs2.getInt("codigo")+" - "+rs2.getString("nome"));
                        }
                    }while (rs.next());
                    System.out.println("\n");
                }else{
                    System.out.println("Nenhum Treino encontrado\n");
                }


                System.out.println("Digite o codigo do exercicio que deseja excluir : ");
                codigoExe = entrada.nextInt();
                if(codigoExe == 0) return;

                int linhasAfetadas = s.executeUpdate("DELETE FROM treinoexercicios WHERE codexe = " + codigoExe);
                if(linhasAfetadas > 0){
                    System.out.println("Exercicio do treino excluído com sucesso.");
                }else{
                    System.out.println("Não foi possível excluir o exercicio do treino.");
                }

            } while (codigoExe != 0);

        }else{
            System.out.println("Nenhum treino encontrado");
        }
    }
    public void iniciarTreinamento() throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);
        String cpfLocal =  "\'" + buscarAluno() + "\'";
        int codigoTreino = 0;
        LocalDate dataAtual;

        if(imprimirTreinos("SELECT * FROM treinos WHERE alunocpf = " + cpfLocal) != 0){
            System.out.println("Digite o codigo do treino que deseja inciar: ");
            codigoTreino = entrada.nextInt();
            dataAtual = LocalDate.now();

            PreparedStatement p = c.prepareStatement(
                    "INSERT INTO treinosrealizados (codtreino, data) VALUES (?, ?)");
            p.setInt(1, codigoTreino);
            p.setObject(2, dataAtual);
            p.execute();
            treinando(codigoTreino, dataAtual);
            System.out.println("Treino Finalizado!");

        }else{
            System.out.println("Nenhum treino encontrado\n");
        }

    }
    public void treinando(int codigoTreino, LocalDate dataAtual) throws ClassNotFoundException, SQLException{
        Scanner entrada = new Scanner(System.in);

        String query = "SELECT * FROM treinoexercicios WHERE codtreino = ?";
        PreparedStatement s = c.prepareStatement(query);
        s.setInt(1, codigoTreino);

        String query2 = "SELECT * FROM exercicios WHERE codigo = ?";
        PreparedStatement s2 = c.prepareStatement(query2);

        String query3 = "UPDATE treinoexercicios SET carga = ? WHERE codexe = ?";
        PreparedStatement s3 = c.prepareStatement(query3);

        String query4 = "SELECT * FROM treinoexercicios WHERE codexe = ?";
        PreparedStatement s4 = c.prepareStatement(query4);

        ResultSet rs = s.executeQuery();
        if(rs.next()){
            do{

                s2.setInt(1, rs.getInt("codexe"));
                ResultSet rs2 = s2.executeQuery();
                while(rs2.next()){
                    System.out.println("Exercicio: "+rs2.getString("nome"));

                    System.out.println("Deseja alterar a carga? 1 (Sim) 0 (Não)");
                    int alterar = entrada.nextInt();

                    if(alterar == 1){
                        System.out.println("Digite a nova carga : ");
                        double carga = entrada.nextDouble();

                        s3.setDouble(1, carga);
                        s3.setInt(2, rs2.getInt("codigo"));
                        int linhasAfetadas = s3.executeUpdate();

                        if(linhasAfetadas > 0){
                            System.out.println("Carga do exercicio alterada com sucesso.");
                            PreparedStatement p = c.prepareStatement(
                                    "INSERT INTO historicoevolucao (codexe, carga, data) VALUES (?, ?, ?)");
                            p.setInt(1, codigoTreino);
                            p.setDouble(2, carga);
                            p.setObject(3, dataAtual);
                            p.executeUpdate();
                        }else{
                            System.out.println("Não foi possível alterar a carga do exercicio.");
                        }
                    }
                    else {
                        s4.setInt(1, rs.getInt("codexe"));
                        ResultSet rs4 = s4.executeQuery();
                        if(rs4.next()) {
                            double cargaLocal = rs4.getDouble("carga");
                            PreparedStatement p = c.prepareStatement(
                                    "INSERT INTO historicoevolucao (codexe, carga, data) VALUES (?, ?, ?)");
                            p.setInt(1, codigoTreino);
                            p.setDouble(2, cargaLocal);
                            p.setObject(3, dataAtual);
                            p.executeUpdate();
                        }
                    }
                }
            }while (rs.next());
        }else{
            System.out.println("Nenhum Treino encontrado\n");
        }
    }
}