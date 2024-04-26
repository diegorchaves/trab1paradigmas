package trabalho1paradigmas;
import java.util.ArrayList;
import java.util.List;

public class ListaPlanosCadastrados {
	private List<Plano> planos;
	
	public ListaPlanosCadastrados() {
		this.planos = new ArrayList<>();
	}
	
	public void adicionarPlano(Plano plano) {
		planos.add(plano);
	}
	
	public void removerPlano(Plano plano) {
        planos.remove(plano);
    }

    public List<Plano> getPlanos() {
        return planos;
    }
    
    public void imprimirPlanos() {
    	for(Plano i : planos) {
    		System.out.println(i.toString());
    	}
    }
}
