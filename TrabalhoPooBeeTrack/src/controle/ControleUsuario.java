package controle;

import repositorio.RepositorioUsuario;
import java.util.List;

/**
 * Classe de controle para gerenciamento de usuarios com validacoes
 */
public class ControleUsuario {
    private RepositorioUsuario repositorio;
    private String ultimaMensagemErro;

    public ControleUsuario(RepositorioUsuario repositorio) {
        if (repositorio == null) {
            throw new IllegalArgumentException("Repositorio nao pode ser nulo");
        }
        this.repositorio = repositorio;
        this.ultimaMensagemErro = "";
    }

    /**
     * Retorna a ultima mensagem de erro registrada
     */
    public String getUltimaMensagemErro() {
        return ultimaMensagemErro;
    }

    /**
     * Cria um novo usuario com validacoes detalhadas
     */
    public boolean criarUsuario(String nome, String email, String senha, String tipoUsuario) {
        try {
            // Validacoes com mensagens especificas
            if (nome == null || nome.trim().isEmpty()) {
                ultimaMensagemErro = "Nome nao pode ser vazio.";
                return false;
            }
            
            if (email == null || email.trim().isEmpty()) {
                ultimaMensagemErro = "Email nao pode ser vazio.";
                return false;
            }
            
            if (!validarEmail(email)) {
                ultimaMensagemErro = "Email invalido.";
                return false;
            }
            
            if (senha == null || senha.trim().isEmpty()) {
                ultimaMensagemErro = "Senha nao pode ser vazia.";
                return false;
            }
            
            if (senha.length() < 4) {
                ultimaMensagemErro = "Senha deve ter pelo menos 4 caracteres.";
                return false;
            }
            
            if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
                ultimaMensagemErro = "Tipo de usuario nao pode ser vazio.";
                return false;
            }

            // Verifica duplicidade de email
            if (repositorio.buscarPorEmail(email) != null) {
                ultimaMensagemErro = "Ja existe um usuario com este email.";
                return false;
            }

            // Cria o usuario usando o factory method
            Pessoa novoUsuario = Pessoa.criarUsuario(0, nome.trim(), email.trim(), senha, tipoUsuario.trim());
            
            if (novoUsuario == null) {
                ultimaMensagemErro = "Erro ao criar usuario. Tipo invalido.";
                return false;
            }
            
            boolean adicionado = repositorio.adicionar(novoUsuario);
            
            if (adicionado) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao adicionar usuario ao repositorio.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao criar usuario: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao criar usuario.";
            return false;
        }
    }

    /**
     * Lista todos os usuarios
     */
    public List<Pessoa> listar() {
        try {
            return repositorio.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar usuarios: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Busca um usuario por ID
     */
    public Pessoa buscarPorId(int id) {
        try {
            if (id <= 0) {
                return null;
            }
            return repositorio.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuario por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um usuario por email
     */
    public Pessoa buscarPorEmail(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return null;
            }
            return repositorio.buscarPorEmail(email);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuario por email: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um usuario por nome
     */
    public Pessoa buscarPorNome(String nome) {
        try {
            if (nome == null || nome.trim().isEmpty()) {
                return null;
            }
            return repositorio.buscarPorNome(nome);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuario por nome: " + e.getMessage());
            return null;
        }
    }

    /**
     * Remove um usuario por email
     */
    public boolean remover(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                ultimaMensagemErro = "Email nao pode ser vazio.";
                return false;
            }
            
            // Verifica se o usuario existe
            Pessoa usuario = repositorio.buscarPorEmail(email);
            if (usuario == null) {
                ultimaMensagemErro = "Usuario nao encontrado.";
                return false;
            }
            
            boolean removido = repositorio.removerPorEmail(email);
            
            if (removido) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao remover usuario.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao remover usuario: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao remover usuario.";
            return false;
        }
    }

    /**
     * Remove um usuario por ID
     */
    public boolean removerPorId(int id) {
        try {
            if (id <= 0) {
                ultimaMensagemErro = "ID invalido.";
                return false;
            }
            
            // Verifica se o usuario existe
            Pessoa usuario = repositorio.buscarPorId(id);
            if (usuario == null) {
                ultimaMensagemErro = "Usuario nao encontrado.";
                return false;
            }
            
            boolean removido = repositorio.remover(id);
            
            if (removido) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao remover usuario.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao remover usuario por ID: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao remover usuario.";
            return false;
        }
    }

    /**
     * Atualiza um usuario existente
     */
    public boolean atualizar(Pessoa usuario) {
        try {
            if (usuario == null) {
                ultimaMensagemErro = "Usuario nao pode ser nulo.";
                return false;
            }
            
            // Validacoes
            if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
                ultimaMensagemErro = "Nome nao pode ser vazio.";
                return false;
            }
            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                ultimaMensagemErro = "Email nao pode ser vazio.";
                return false;
            }
            
            if (!validarEmail(usuario.getEmail())) {
                ultimaMensagemErro = "Email invalido.";
                return false;
            }
            
            if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
                ultimaMensagemErro = "Senha nao pode ser vazia.";
                return false;
            }
            
            if (usuario.getSenha().length() < 4) {
                ultimaMensagemErro = "Senha deve ter pelo menos 4 caracteres.";
                return false;
            }

            // Verifica se o usuario existe
            Pessoa existente = repositorio.buscarPorId(usuario.getId());
            if (existente == null) {
                ultimaMensagemErro = "Usuario nao encontrado.";
                return false;
            }

            // Verifica se o novo email ja existe em outro usuario
            Pessoa outroComEmail = repositorio.buscarPorEmail(usuario.getEmail());
            if (outroComEmail != null && outroComEmail.getId() != usuario.getId()) {
                ultimaMensagemErro = "Ja existe outro usuario com este email.";
                return false;
            }

            boolean atualizado = repositorio.atualizar(usuario);
            
            if (atualizado) {
                ultimaMensagemErro = "";
                return true;
            } else {
                ultimaMensagemErro = "Erro ao atualizar usuario.";
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar usuario: " + e.getMessage());
            ultimaMensagemErro = "Erro inesperado ao atualizar usuario.";
            return false;
        }
    }

    /**
     * Lista usuarios por tipo
     */
    public List<Pessoa> listarPorTipo(String tipoUsuario) {
        try {
            return repositorio.listarPorTipo(tipoUsuario);
        } catch (Exception e) {
            System.err.println("Erro ao listar usuarios por tipo: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Obtem o total de usuarios cadastrados
     */
    public int getTotalUsuarios() {
        try {
            return repositorio.getTotalUsuarios();
        } catch (Exception e) {
            System.err.println("Erro ao obter total de usuarios: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Verifica se existe um usuario com o email especificado
     */
    public boolean existeEmail(String email) {
        try {
            return repositorio.buscarPorEmail(email) != null;
        } catch (Exception e) {
            System.err.println("Erro ao verificar existencia de email: " + e.getMessage());
            return false;
        }
    }

    /**
     * Valida formato de email
     */
    private boolean validarEmail(String email) {
        try {
            if (email == null) return false;
            String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return email.matches(regex);
        } catch (Exception e) {
            return false;
        }
    }
}