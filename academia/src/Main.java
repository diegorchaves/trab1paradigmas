import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        List<Musculo> musculosDisponiveis = new ArrayList<Musculo>();
        adicionarMusculos(musculosDisponiveis);

        for (Musculo musculo : musculosDisponiveis) {

            System.out.println(musculo.getCodigo()+" - "+musculo.getNome());
        }

        adicionarMusculos(musculosDisponiveis);

        Exercicio supino = new Exercicio();
        supino.adicionarMusculo(musculosDisponiveis);

        supino.imprimirMusculos ();
    }

    public static void adicionarMusculos(List<Musculo>musculosDisponiveis) {
        Musculo deltoide = new Musculo();
        deltoide.setNome("Deltoide");
        deltoide.setCodigo(1);

        Musculo peitoral = new Musculo();
        peitoral.setNome("Peitoral");
        peitoral.setCodigo(2);

        Musculo biceps = new Musculo();
        biceps.setNome("Biceps");
        biceps.setCodigo(3);

        Musculo triceps = new Musculo();
        triceps.setNome("Triceps");
        triceps.setCodigo(4);

        Musculo obliquos = new Musculo();
        obliquos.setNome("Obliquos");
        obliquos.setCodigo(5);

        Musculo abdominal = new Musculo();
        abdominal.setNome("Abdominal");
        abdominal.setCodigo(6);

        Musculo quadriceps = new Musculo();
        quadriceps.setNome("Quadriceps");
        quadriceps.setCodigo(7);

        Musculo trapezio = new Musculo();
        trapezio.setNome("Trapezio");
        trapezio.setCodigo(8);

        Musculo dorsal = new Musculo();
        dorsal.setNome("Dorsal");
        dorsal.setCodigo(9);

        Musculo gluteo = new Musculo();
        gluteo.setNome("Gluteo");
        gluteo.setCodigo(10);

        Musculo isquiotibial = new Musculo();
        isquiotibial.setNome("Isquiotibial");
        isquiotibial.setCodigo(11);

        Musculo gemeos = new Musculo();
        gemeos.setNome("Gemeos");
        gemeos.setCodigo(12);

        musculosDisponiveis.add(deltoide);
        musculosDisponiveis.add(peitoral);
        musculosDisponiveis.add(biceps);
        musculosDisponiveis.add(triceps);
        musculosDisponiveis.add(obliquos);
        musculosDisponiveis.add(abdominal);
        musculosDisponiveis.add(quadriceps);
        musculosDisponiveis.add(trapezio);
        musculosDisponiveis.add(dorsal);
        musculosDisponiveis.add(gluteo);
        musculosDisponiveis.add(isquiotibial);
        musculosDisponiveis.add(gemeos);
    }
}
