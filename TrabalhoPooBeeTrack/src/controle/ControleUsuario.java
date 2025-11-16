package controle;

import repositorio.RepositorioUsuario;
import java.util.List;

/**
 * Classe de controle para gerenciamento de usuários com validações
 */
public class ControleUsuario {
    private RepositorioUsuario repositorio;
    private String ultimaMensagemErro;

    public ControleUsuario(RepositorioUsuario repositorio) {
        this.repositorio = repositorio;
        this.ultimaMensagemErro = "";
    }

    /**
     * Retorna a última mensagem de erro registrada
     */
    public String getUltimaMensagemErro() {
        return ultimaMensagemErro;
    }

    /**
     * Cria um novo usuário com validações detalhadas
     */
    public boolean criarUsuario(String nome, String email, String senha, String tipoUsuario) {
        // Validações com mensagens específicas
        if (nome == null || nome.trim().isEmpty()) {
            ultimaMensagemErro = "Nome não pode ser vazio.";
            return false;
        }
        
        if (email == null || email.trim().isEmpty()) {
            ultimaMensagemErro = "Email não pode ser vazio.";
            return false;
        }
        
        if (!validarEmail(email)) {
            ultimaMensagemErro = "Email inválido.";
            return false;
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            ultimaMensagemErro = "Senha não pode ser vazia.";
            return false;
        }
        
        if (senha.length() < 4) {
            ultimaMensagemErro = "Senha deve ter pelo menos 4 caracteres.";
            return false;
        }
        
        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            ultimaMensagemErro = "Tipo de usuário não pode ser vazio.";
            return false;
        }

        // Verifica duplicidade de email
        if (repositorio.buscarPorEmail(email) != null) {
            ultimaMensagemErro = "Já existe um usuário com este email.";
            return false;
        }

        // Cria o usuário usando o factory method
        Pessoa novoUsuario = Pessoa.criarUsuario(0, nome.trim(), email.trim(), senha, tipoUsuario.trim());
        
        if (novoUsuario == null) {
            ultimaMensagemErro = "Erro ao criar usuário. Tipo inválido.";
            return false;
        }
        
        boolean adicionado = repositorio.adicionar(novoUsuario);
        
        if (adicionado) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao adicionar usuário ao repositório.";
            return false;
        }
    }

    /**
     * Lista todos os usuários
     */
    public List<Pessoa> listar() {
        return repositorio.listarTodos();
    }

    /**
     * Busca um usuário por ID
     */
    public Pessoa buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return repositorio.buscarPorId(id);
    }

    /**
     * Busca um usuário por email
     */
    public Pessoa buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorEmail(email);
    }

    /**
     * Busca um usuário por nome
     */
    public Pessoa buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorNome(nome);
    }

    /**
     * Remove um usuário por email
     */
    public boolean remover(String email) {
        if (email == null || email.trim().isEmpty()) {
            ultimaMensagemErro = "Email não pode ser vazio.";
            return false;
        }
        
        // Verifica se o usuário existe
        Pessoa usuario = repositorio.buscarPorEmail(email);
        if (usuario == null) {
            ultimaMensagemErro = "Usuário não encontrado.";
            return false;
        }
        
        boolean removido = repositorio.removerPorEmail(email);
        
        if (removido) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao remover usuário.";
            return false;
        }
    }

    /**
     * Remove um usuário por ID
     */
    public boolean removerPorId(int id) {
        if (id <= 0) {
            ultimaMensagemErro = "ID inválido.";
            return false;
        }
        
        // Verifica se o usuário existe
        Pessoa usuario = repositorio.buscarPorId(id);
        if (usuario == null) {
            ultimaMensagemErro = "Usuário não encontrado.";
            return false;
        }
        
        boolean removido = repositorio.remover(id);
        
        if (removido) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao remover usuário.";
            return false;
        }
    }

    /**
     * Atualiza um usuário existente
     */
    public boolean atualizar(Pessoa usuario) {
        if (usuario == null) {
            ultimaMensagemErro = "Usuário não pode ser nulo.";
            return false;
        }
        
        // Validações
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            ultimaMensagemErro = "Nome não pode ser vazio.";
            return false;
        }
        
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            ultimaMensagemErro = "Email não pode ser vazio.";
            return false;
        }
        
        if (!validarEmail(usuario.getEmail())) {
            ultimaMensagemErro = "Email inválido.";
            return false;
        }
        
        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            ultimaMensagemErro = "Senha não pode ser vazia.";
            return false;
        }
        
        if (usuario.getSenha().length() < 4) {
            ultimaMensagemErro = "Senha deve ter pelo menos 4 caracteres.";
            return false;
        }

        // Verifica se o usuário existe
        Pessoa existente = repositorio.buscarPorId(usuario.getId());
        if (existente == null) {
            ultimaMensagemErro = "Usuário não encontrado.";
            return false;
        }

        // Verifica se o novo email já existe em outro usuário
        Pessoa outroComEmail = repositorio.buscarPorEmail(usuario.getEmail());
        if (outroComEmail != null && outroComEmail.getId() != usuario.getId()) {
            ultimaMensagemErro = "Já existe outro usuário com este email.";
            return false;
        }

        boolean atualizado = repositorio.atualizar(usuario);
        
        if (atualizado) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao atualizar usuário.";
            return false;
        }
    }

    /**
     * Lista usuários por tipo
     */
    public List<Pessoa> listarPorTipo(String tipoUsuario) {
        return repositorio.listarPorTipo(tipoUsuario);
    }

    /**
     * Obtém o total de usuários cadastrados
     */
    public int getTotalUsuarios() {
        return repositorio.getTotalUsuarios();
    }

    /**
     * Verifica se existe um usuário com o email especificado
     */
    public boolean existeEmail(String email) {
        return repositorio.buscarPorEmail(email) != null;
    }

    /**
     * Valida formato de email
     */
    private boolean validarEmail(String email) {
        if (email == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }
}