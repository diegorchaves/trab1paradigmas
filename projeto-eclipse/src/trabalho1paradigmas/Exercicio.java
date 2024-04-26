package trabalho1paradigmas;

import java.util.ArrayList;
import java.util.List;

public class Exercicio {
	private Integer numero;
	private String nome;
	private List<Musculo> listaMusculosAtivados = new ArrayList<Musculo>();
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void adicionarMusculoAtivado(Musculo musculo) {
		listaMusculosAtivados.add(musculo);
	}
	
	public void imprimirExercicio() {
		System.out.print(numero+" - "+nome+" - Musculos ativados: ");
		for (Musculo i : listaMusculosAtivados) {
			System.out.print(i.getNome()+" ");
		}
	}
	
}
