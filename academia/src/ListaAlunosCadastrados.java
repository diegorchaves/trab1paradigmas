import java.util.ArrayList;
import java.util.List;

public class ListaAlunosCadastrados {

    public List<Aluno> listaAlunos;

    public ListaAlunosCadastrados() {
        this.listaAlunos = new ArrayList<>();

    }

    public void adicionarAluno(Aluno aluno) {
        listaAlunos.add(aluno);
    }
}
