import java.util.List;

public class Treino {
    Integer codigo;
    Boolean iniciado;
    List<ExercicioTreino> exercicios;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Boolean getIniciado() {
        return iniciado;
    }

    public void setIniciado(Boolean iniciado) {
        this.iniciado = iniciado;
    }

    public List<ExercicioTreino> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<ExercicioTreino> exercicios) {
        this.exercicios = exercicios;
    }
}
