package controle;

import java.util.List;

public interface IControleUsuario extends IControle<Pessoa> {
    boolean criarUsuario(String nome, String email, String senha, String tipoUsuario);
    Pessoa buscarPorEmail(String email);
    Pessoa buscarPorNome(String nome);
    boolean remover(String email);
    boolean removerPorId(int id);
    List<Pessoa> listarPorTipo(String tipoUsuario);
    int getTotalUsuarios();
    boolean existeEmail(String email);
}