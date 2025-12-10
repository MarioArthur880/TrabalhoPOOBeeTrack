package controle;

import java.util.List;

public interface IControleApiario extends IControle<Apiario> {
    boolean criarApiario(String nome, String raca, String local, int qntCaixas);
    Apiario buscarPorNome(String nome);
    List<Apiario> buscarTodosPorNome(String nome);
    List<Apiario> listarPorLocal(String local);
    List<Apiario> listarPorRaca(String raca);
    int getTotalCaixas();
    int getTotalCaixasPorLocal(String local);
    int getTotalApiarios();
    boolean existeNome(String nome);
}