import java.util.ArrayList;
import java.util.List;

public class ListaMusculosDisponiveis {
    public List<Musculo> listaMusculos;

    public ListaMusculosDisponiveis() {
        this.listaMusculos = new ArrayList<>();

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

        listaMusculos.add(deltoide);
        listaMusculos.add(peitoral);
        listaMusculos.add(biceps);
        listaMusculos.add(triceps);
        listaMusculos.add(obliquos);
        listaMusculos.add(abdominal);
        listaMusculos.add(quadriceps);
        listaMusculos.add(trapezio);
        listaMusculos.add(dorsal);
        listaMusculos.add(gluteo);
        listaMusculos.add(isquiotibial);
        listaMusculos.add(gemeos);
    }

    public void adicionarMusculo(Musculo musculo) {
        listaMusculos.add(musculo);
    }
}
