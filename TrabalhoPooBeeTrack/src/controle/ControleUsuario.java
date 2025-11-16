package controle;

import repositorio.RepositorioUsuario;
import java.util.List;

public class ControleUsuario {
    private RepositorioUsuario repositorio;

    public ControleUsuario(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
    }

    public boolean criarUsuario(String nome, String email, String senha, String tipoUsuario) {
        if (repositorio.buscarPorEmail(email) != null) {
            return false;
        }

        //primeiro usuário, força o tipo para "Admin"
        if (repositorio.isEmpty()) {
            tipoUsuario = "Admin";
        }

        Pessoa novo = Pessoa.criarUsuario(repositorio.proximoId(), nome, email, senha, tipoUsuario);
        
        if (novo != null) {
            return repositorio.adicionar(novo);
        }
        return false;
    }

    public List<Pessoa> listar() {
        return repositorio.listarTodos();
    }

    public Pessoa buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorEmail(email);
    }

    public Pessoa buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorNome(nome);
    }

    public Pessoa buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return repositorio.buscarPorId(id);
    }

    public boolean remover(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return repositorio.removerPorEmail(email);
    }

    public boolean removerPorId(int id) {
        if (id <= 0) {
            return false;
        }
        return repositorio.remover(id);
    }

    public boolean atualizar(Pessoa usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            return false;
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            return false;
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            return false;
        }

        return repositorio.atualizar(usuario);
    }

    public List<Pessoa> listarPorTipo(String tipoUsuario) {
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            return List.of();
        }
        return repositorio.listarPorTipo(tipoUsuario);
    }

    public Pessoa validarLogin(String email, String senha) {
        Pessoa usuario = repositorio.buscarPorEmail(email);
        
        if (usuario != null && usuario.validarSenha(senha)) {
            return usuario;
        }
        return null;
    }

    public boolean isPrimeiroUsuario() {
        return repositorio.isEmpty();
    }

    public int getTotalUsuarios() {
        return repositorio.getTotalUsuarios();
    }

    public boolean emailJaCadastrado(String email) {
        return repositorio.buscarPorEmail(email) != null;
    }
}