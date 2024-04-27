
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
            opcaoEscolhida = entrada.nextInt();

            switch(opcaoEscolhida) {
                case 0:
                    break;
                case 1:
                    Aluno novoAluno = new Aluno();
                    novoAluno.cadastrarNovoAluno(conexaoLocal);
                case 2:
                    Plano novoPlano = new Plano();
                    novoPlano.cadastrarNovoPlano(conexaoLocal);

            }
        } while(opcaoEscolhida != 0);


        conexaoLocal.imprimirAlunos("SELECT * FROM alunos");


    }

}