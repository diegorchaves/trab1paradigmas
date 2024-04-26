package trabalho1paradigmas;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Aluno {
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void setDataNascimento(String dataNascimento) {
		try {
            this.dataNascimento = LocalDate.parse(dataNascimento, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data de nascimento. Certifique-se de que está no formato dd/MM/yyyy.");
            e.printStackTrace();
        }
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public String toString() {
		return("Nome: "+nome+"\tCPF: "+cpf+"\tData de nascimento: "+dataNascimento);
	}
	
	public void cadastrarNovoAluno(ConexaoBD conexaoLocal) throws ClassNotFoundException, SQLException{
		Scanner entrada = new Scanner(System.in);
		String nome;
		String cpf;
		System.out.println("Informe o nome do aluno: ");
		nome = entrada.nextLine();

		System.out.println("Informe o CPF do aluno: ");
		cpf = entrada.nextLine();
		
		conexaoLocal.adicionarAlunos(cpf, nome);

		
		/*System.out.println("Informe o data de nascimento do aluno: ");
		variavel = entrada.nextLine();
		this.setDataNascimento(variavel);*/
		
	}
	
}
