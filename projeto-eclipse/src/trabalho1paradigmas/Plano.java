package trabalho1paradigmas;

public class Plano {
	private Integer codigo;
	private String nome;
	private double valorMensal;
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setValorMensal(Double valorMensal) {
		this.valorMensal = valorMensal;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public double getValorMensal() {
		return valorMensal;
	}
	
	public String toString() {
		return("Codigo: "+codigo+"\tNome: "+nome+"\tValor mensal: R$ "+valorMensal);
	}
}
