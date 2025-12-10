package repositorio;

import controle.Pessoa;
import java.util.List;

public interface IRepositorioUsuario extends IRepositorio<Pessoa> {
    Pessoa buscarPorEmail(String email);
    Pessoa buscarPorNome(String nome);
    boolean removerPorEmail(String email);
    List<Pessoa> listarPorTipo(String tipoUsuario);
    int getTotalUsuarios();
    boolean isEmpty();
}