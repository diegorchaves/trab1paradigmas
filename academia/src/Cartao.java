
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Cartao {
    private String numero;
    private LocalDate dataVencimento;
    private String cvv;


    public String getNumero() {
        return numero;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setDataVencimento(String dataVencimento) {
        try {
            dataVencimento = "01/" + dataVencimento;
            this.dataVencimento = LocalDate.parse(dataVencimento, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy"));
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter a data de vencimento. Certifique-se de que está no formato MM/yy.");
            e.printStackTrace();
        }
    }

    public String toString() {
        return("Numero: "+numero+"\tCVV: "+cvv+"\tData de vencimento: "+dataVencimento);
    }

    public void setDadosScanner(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Informe o numero do cartao: ");
        this.setNumero(entrada.nextLine());


        System.out.println("Informe o cvv do cartao: ");
        this.setCvv(entrada.nextLine());

        System.out.println("Informe a data de vencimento do cartao: ");
        this.setDataVencimento(entrada.nextLine());


    }

}