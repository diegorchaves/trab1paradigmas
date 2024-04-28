
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        var entrada = new Scanner(System.in);
        int opcaoEscolhida;
        ConexaoBD conexaoLocal = new ConexaoBD();
        conexaoLocal.conexaoBD();

        do {
            System.out.println("Selecione a opcao desejada: ");
            System.out.println("0 - Encerrar programa.");
            System.out.println("1 - Cadastrar aluno.");
            System.out.println("2 - Cadastrar plano.");
            System.out.println("3 - Cadastrar exercicio.");
            System.out.println("4 - Assinar Plano.");
            System.out.println("5 - Buscar aluno.");
            System.out.println("6 - Cadastrar Treino.");
            System.out.println("7 - buscar Treino.");
            opcaoEscolhida = entrada.nextInt();

            switch(opcaoEscolhida) {
                case 0:
                    break;
                case 1:
                    Aluno novoAluno = new Aluno();
                    novoAluno.setDadosScanner();
                    conexaoLocal.adicionarAlunos(novoAluno);
                    break;
                case 2:
                    Plano novoPlano = new Plano();
                    novoPlano.setDadosScanner();
                    conexaoLocal.adicionarPlanos(novoPlano);
                    break;
                case 3:
                    Exercicio novoExercicio = new Exercicio();
                    novoExercicio.setDadosScanner();
                    conexaoLocal.adicionarExercicios(novoExercicio);
                    break;
                case 4:
                    conexaoLocal.assinarPlano();
                    break;
                case 5:
                    conexaoLocal.buscarAluno();
                    break;
                case 6:
                    conexaoLocal.adicionarTreino();
                    break;
                case 7:
                    conexaoLocal.buscarTreino();
                    break;
            }
        } while(opcaoEscolhida != 0);
    }
}