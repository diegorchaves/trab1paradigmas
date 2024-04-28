import java.util.Scanner;

public class TreinoExercicio {

    private int series;
    private int minRepeticoes;
    private int maxRepeticoes;
    private double carga;
    private double tempoDescanso;

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getMinRepeticoes() {
        return minRepeticoes;
    }

    public void setMinRepeticoes(int minRepeticoes) {
        this.minRepeticoes = minRepeticoes;
    }

    public int getMaxRepeticoes() {
        return maxRepeticoes;
    }

    public void setMaxRepeticoes(int maxRepeticoes) {
        this.maxRepeticoes = maxRepeticoes;
    }

    public double getCarga() {
        return carga;
    }

    public void setCarga(double carga) {
        this.carga = carga;
    }

    public double getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(double tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }

    public void setDadosScanner(){
        Scanner entrada = new Scanner(System.in);
        System.out.println("Informe a quantidade de series: ");
        this.setSeries(entrada.nextInt());

        System.out.println("Informe a quantidade de minima de repeticoes: ");
        this.setMinRepeticoes(entrada.nextInt());

        System.out.println("Informe a quantidade de maxima de repeticoes: ");
        this.setMaxRepeticoes(entrada.nextInt());

        System.out.println("Informe a carga (Kg): ");
        this.setCarga(entrada.nextDouble());

        System.out.println("Informe o tempo de descanso (Min): ");
        this.setTempoDescanso(entrada.nextDouble());


    }

}
