package controle;

import java.util.ArrayList;
import java.util.List;

public class ControleUsuario {
    private List<Pessoa> listaUsuarios = new ArrayList<>();
    private int proximoId = 1;

    public boolean criarUsuario(String nome, String email, String senha, String tipoUsuario) {
    if (buscarPorEmail(email) != null) return false;

    if (listaUsuarios.isEmpty()) {
        tipoUsuario = "Admin";
    }

    Pessoa novo = Pessoa.criarUsuario(proximoId, nome, email, senha, tipoUsuario); //criar função
    if (novo != null) {
        listaUsuarios.add(novo);
        proximoId++;
        return true;
    }
    return false;
}


    public List<Pessoa> listar() {
        return new ArrayList<>(listaUsuarios);
    }

    public Pessoa buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;

        for (Pessoa u : listaUsuarios) {
            if (u.getEmail().equalsIgnoreCase(email.trim())) { // criar função
                return u;
            }
        }
        return null;
    }

    public Pessoa buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) return null;

        for (Pessoa u : listaUsuarios) {
            if (u.getNome().equalsIgnoreCase(nome.trim())) {
                return u;
            }
        }
        return null;
    }

    public boolean remover(String email) {
        Pessoa u = buscarPorEmail(email);
        if (u != null) {
            return listaUsuarios.remove(u);
        }
        return false;
    }

    public boolean atualizar(Pessoa novoUsuario) {
        if (novoUsuario == null || novoUsuario.getEmail() == null) return false;

        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getEmail().equalsIgnoreCase(novoUsuario.getEmail())) {
                listaUsuarios.set(i, novoUsuario);
                return true;
            }
        }
        return false;
    }

    public List<Pessoa> listarPorTipo(String tipoUsuario) {
        List<Pessoa> resultado = new ArrayList<>();
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) return resultado;

        for (Pessoa u : listaUsuarios) {
            if (u.getTipoUsuario().equalsIgnoreCase(tipoUsuario.trim())) {
                resultado.add(u);
            }
        }
        return resultado;
    }
}
