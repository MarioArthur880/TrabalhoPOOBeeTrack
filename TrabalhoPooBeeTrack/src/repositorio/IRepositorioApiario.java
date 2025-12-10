package repositorio;

import controle.Apiario;
import java.util.List;

public interface IRepositorioApiario extends IRepositorio<Apiario> {
    Apiario buscarPorNome(String nome);
    List<Apiario> buscarTodosPorNome(String nome);
    boolean existeNome(String nome);
    boolean existeNomeEmOutroApiario(String nome, int idExcluir);
    List<Apiario> listarPorLocal(String local);
    List<Apiario> listarPorRaca(String raca);
    int calcularTotalCaixas();
    int calcularTotalCaixasPorLocal(String local);
    boolean existeLocal(String local);
    int getTotalApiarios();
}
