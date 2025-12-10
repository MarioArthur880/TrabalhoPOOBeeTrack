package controle;

import java.util.List;

public interface IControle<T> {
    List<T> listar();
    T buscarPorId(int id);
    boolean remover(int id);
    boolean atualizar(T entidade);
    String getUltimaMensagemErro();
}