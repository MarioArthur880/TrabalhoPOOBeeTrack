package repositorio;

import java.util.List;

/**
 * Interface base para repositorios
 * Define operacoes CRUD padrao
 */
public interface IRepositorio<T> {
    boolean adicionar(T entidade);
    List<T> listarTodos();
    T buscarPorId(int id);
    boolean remover(int id);
    boolean atualizar(T entidade);
    int getTotalRegistros();
    void limparTodos();
}
