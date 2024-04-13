import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Exercicio {
    private Integer codigo;
    private String nome;
    private List<Musculo> musculosTreinados = new ArrayList<Musculo>();
    Scanner entrada = new Scanner(System.in);


    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musculo> getMusculosTreinados() {
        return musculosTreinados;
    }

    public void adicionarMusculo (List<Musculo> musculosDisponiveis) {
        int codigo;

        do {
            System.out.println("Digite o codigo do musculo que deseja adicionar: ");
            codigo = entrada.nextInt();
            entrada.nextLine();

            if (codigo == 0) {
                break;
            }

            boolean encontrado = false;

            for (Musculo musculo : musculosDisponiveis) {
                if (musculo.getCodigo() == codigo) {
                    musculosTreinados.add(musculo);
                    System.out.println(musculo.getNome() + " foi adicionado com sucesso!");
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                System.out.println("Codigo invalido. Tente novamente!");
            }
        } while (true);

    }

    public void imprimirMusculos() {
        for (Musculo musculo : musculosTreinados) {
            System.out.println(musculo.getNome());
        }
    }
}
