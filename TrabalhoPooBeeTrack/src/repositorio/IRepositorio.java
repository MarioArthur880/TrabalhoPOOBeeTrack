package repositorio;

import java.util.List;

public interface IRepositorio<T> {
    boolean adicionar(T entidade);
    List<T> listarTodos();
    T buscarPorId(int id);
    boolean remover(int id);
    boolean atualizar(T entidade);
    int getTotalRegistros();
    void limparTodos();
}
