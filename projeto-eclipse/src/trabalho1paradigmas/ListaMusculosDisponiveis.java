package trabalho1paradigmas;

import java.util.HashMap;

public class ListaMusculosDisponiveis {
    public HashMap<Integer, Musculo> listaMusculos;

    public HashMap<Integer, Musculo> getListaMusculos() {
		return listaMusculos;
	}

	public ListaMusculosDisponiveis() {
        this.listaMusculos = new HashMap<>();

        Musculo deltoide = new Musculo();
        deltoide.setNome("Deltoide");

        Musculo peitoral = new Musculo();
        peitoral.setNome("Peitoral");

        Musculo biceps = new Musculo();
        biceps.setNome("Biceps");

        Musculo triceps = new Musculo();
        triceps.setNome("Triceps");

        Musculo obliquos = new Musculo();
        obliquos.setNome("Obliquos");

        Musculo abdominal = new Musculo();
        abdominal.setNome("Abdominal");

        Musculo quadriceps = new Musculo();
        quadriceps.setNome("Quadriceps");

        Musculo trapezio = new Musculo();
        trapezio.setNome("Trapezio");

        Musculo dorsal = new Musculo();
        dorsal.setNome("Dorsal");

        Musculo gluteo = new Musculo();
        gluteo.setNome("Gluteo");

        Musculo isquiotibial = new Musculo();
        isquiotibial.setNome("Isquiotibial");

        Musculo gemeos = new Musculo();
        gemeos.setNome("Gemeos");

        listaMusculos.put(1, deltoide);
        listaMusculos.put(2, peitoral);
        listaMusculos.put(3, biceps);
        listaMusculos.put(4, triceps);
        listaMusculos.put(5, obliquos);
        listaMusculos.put(6, abdominal);
        listaMusculos.put(7, quadriceps);
        listaMusculos.put(8, trapezio);
        listaMusculos.put(9, dorsal);
        listaMusculos.put(10, gluteo);
        listaMusculos.put(11, isquiotibial);
        listaMusculos.put(12, gemeos);
    }

}
