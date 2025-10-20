package controle;

import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class ControleUsuario {
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private int proximoId = 1;

    public boolean criarUsuario(String nome, String email, String senha, String tipoUsuario) {
    if (buscarPorEmail(email) != null) return false;

    // Se for o primeiro usuário, força o tipo para "Admin"
    if (listaUsuarios.isEmpty()) {
        tipoUsuario = "Admin";
    }

    Usuario novo = Usuario.criarUsuario(proximoId, nome, email, senha, tipoUsuario);
    if (novo != null) {
        listaUsuarios.add(novo);
        proximoId++;
        return true;
    }
    return false;
}


    public List<Usuario> listar() {
        return new ArrayList<>(listaUsuarios);
    }

    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;

        for (Usuario u : listaUsuarios) {
            if (u.getEmail().equalsIgnoreCase(email.trim())) {
                return u;
            }
        }
        return null;
    }

    public Usuario buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return null;

        for (Usuario u : listaUsuarios) {
            if (u.getNome().equalsIgnoreCase(nome.trim())) {
                return u;
            }
        }
        return null;
    }

    public boolean remover(String email) {
        Usuario u = buscarPorEmail(email);
        if (u != null) {
            return listaUsuarios.remove(u);
        }
        return false;
    }

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
