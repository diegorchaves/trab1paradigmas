import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

//        ListaMusculosDisponiveis musculosDisponiveis = new ListaMusculosDisponiveis();
//
//        for (Musculo musculo : musculosDisponiveis.listaMusculos) {
//
//            System.out.println(musculo.getCodigo()+" - "+musculo.getNome());
//        }
//
//        Exercicio supino = new Exercicio();
//        supino.adicionarMusculo(musculosDisponiveis.listaMusculos);
//
//        supino.imprimirMusculos ();

//        ListaPlanosDisponiveis planosDisponiveis = new ListaPlanosDisponiveis();
//
//        Plano newPlano = new Plano();
//        newPlano.setNome("Premium");
//        newPlano.setCodigo(planosDisponiveis.listaPlanos.size() + 1);
//        newPlano.setValorMensal(99.9);
//
//        planosDisponiveis.adicionarPlano(newPlano);
//
//        for (Plano plano : planosDisponiveis.listaPlanos) {
//
//            System.out.println(plano.getCodigo()+" - "+plano.getNome() + " - " + plano.getValorMensal());
//        }

        ListaAlunosCadastrados alunosCadastrados = new ListaAlunosCadastrados();

        Aluno newAluno = new Aluno();
        newAluno.setNome("Joao");
        newAluno.setCPF("112233445-56");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2005, 3, 19);

        newAluno.setData_nascimento(calendar.getTime());

        alunosCadastrados.adicionarAluno(newAluno);
        for(Aluno aluno : alunosCadastrados.listaAlunos){
            String dateFormat = new SimpleDateFormat("dd/MM/yyyy").format(aluno.getData_nascimento());
            System.out.println(aluno.getNome() + " - " + aluno.getCPF() + " - " + dateFormat );
        }
    }
}
