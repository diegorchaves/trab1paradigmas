import java.util.ArrayList;
import java.util.List;
public class ListaPlanosDisponiveis {

    public List<Plano> listaPlanos;

    public ListaPlanosDisponiveis() {
        this.listaPlanos = new ArrayList<>();

        Plano simples = new Plano();
        simples.setNome("Simples");
        simples.setCodigo(1);
        simples.setValorMensal(79.90);

        listaPlanos.add(simples);
    }

    public void adicionarPlano(Plano plano) {
        listaPlanos.add(plano);
    }
}
