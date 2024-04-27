
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exercicio {
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDadosScanner(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe o nome do Exercicio: ");
        this.setNome(entrada.nextLine());

    }
}