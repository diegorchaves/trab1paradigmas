
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Cartao {
    private String nome;
    private String numero;
    private LocalDate dataVencimento;
    private String cvv;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setDataVencimento(String dataVencimento) {
        try {
            this.dataVencimento = LocalDate.parse(dataVencimento, java.time.format.DateTimeFormatter.ofPattern("MM/yyyy"));
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data de vencimento. Certifique-se de que está no formato MM/yyyy.");
            e.printStackTrace();
        }
    }

    public String toString() {
        return("Nome: "+nome+"\tNumero: "+numero+"\tCVV: "+cvv+"\tData de vencimento: "+dataVencimento);
    }
}