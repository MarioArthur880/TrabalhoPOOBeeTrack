package controle;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ControleUsuario {
    private List<Usuario> listaUsuarios = new ArrayList<>();

    // Adiciona um novo usuário à lista
    public void adicionar(Usuario u) {
        if (u != null) {
            listaUsuarios.add(u);
        }
    }

    // Retorna a lista completa de usuários
    public List<Usuario> listar() {
        return new ArrayList<>(listaUsuarios);
    }

    // Busca um usuário pelo e-mail
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;

        for (Usuario u : listaUsuarios) {
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                return u;
            }
        }
        return null;
    }

    // Busca um usuário pelo nome
    public Usuario buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return null;

        for (Usuario u : listaUsuarios) {
            if (u.getNome().equalsIgnoreCase(nome.trim())) {
                return u;
            }
        }
        return null;
    }

    // Remove um usuário pelo e-mail
    public boolean remover(String email) {
        Usuario u = buscarPorEmail(email);
        if (u != null) {
            return listaUsuarios.remove(u);
        }
        return false;
    }

    // Atualiza um usuário existente com base no e-mail
    public boolean atualizar(Usuario novoUsuario) {
        if (novoUsuario == null || novoUsuario.getEmail() == null) return false;

        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getEmail().equalsIgnoreCase(novoUsuario.getEmail())) {
                listaUsuarios.set(i, novoUsuario);
                return true;
            }
        }
        return false;
    }

    // Retorna todos os usuários de um determinado tipo
    public List<Usuario> listarPorTipo(String tipoUsuario) {
        List<Usuario> resultado = new ArrayList<>();
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) return resultado;

        for (Usuario u : listaUsuarios) {
            if (u.getTipoUsuario().equalsIgnoreCase(tipoUsuario.trim())) {
                resultado.add(u);
            }
        }
        return resultado;
    }
}
