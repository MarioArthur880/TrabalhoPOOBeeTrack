package controle;

import repositorio.RepositorioApiario;
import java.util.List;

/**
 * Classe de controle para gerenciamento de apiários com validações
 */
public class ControleApiario {
    private RepositorioApiario repositorio;
    private String ultimaMensagemErro;

    public ControleApiario(RepositorioApiario repositorio) {
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
     * Cria um novo apiário com validações detalhadas
     */
    public boolean criarApiario(String nome, String raca, String local, int qntCaixas) {
        // Validações com mensagens específicas
        if (nome == null || nome.trim().isEmpty()) {
            ultimaMensagemErro = "Nome não pode ser vazio.";
            return false;
        }
        
        if (raca == null || raca.trim().isEmpty()) {
            ultimaMensagemErro = "Raça não pode ser vazia.";
            return false;
        }
        
        if (local == null || local.trim().isEmpty()) {
            ultimaMensagemErro = "Local não pode ser vazio.";
            return false;
        }
        
        if (qntCaixas <= 0) {
            ultimaMensagemErro = "Quantidade de caixas deve ser maior que zero.";
            return false;
        }

        // Verifica duplicidade de nome
        if (repositorio.existeNome(nome)) {
            ultimaMensagemErro = "Já existe um apiário com este nome.";
            return false;
        }

        // Cria o apiário usando o construtor sem ID
        Apiario novoApiario = new Apiario(nome.trim(), raca.trim(), local.trim(), qntCaixas);
        
        boolean adicionado = repositorio.adicionar(novoApiario);
        
        if (adicionado) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao adicionar apiário ao repositório.";
            return false;
        }
    }

    /**
     * Lista todos os apiários
     */
    public List<Apiario> listar() {
        return repositorio.listarTodos();
    }

    /**
     * Busca um apiário por ID
     */
    public Apiario buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return repositorio.buscarPorId(id);
    }

    /**
     * Busca um apiário por nome (retorna o primeiro)
     */
    public Apiario buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorNome(nome);
    }

    /**
     * Busca todos os apiários com um determinado nome
     */
    public List<Apiario> buscarTodosPorNome(String nome) {
        return repositorio.buscarTodosPorNome(nome);
    }

    /**
     * Remove um apiário por ID
     */
    public boolean remover(int id) {
        if (id <= 0) {
            ultimaMensagemErro = "ID inválido.";
            return false;
        }
        
        // Verifica se o apiário existe
        Apiario apiario = repositorio.buscarPorId(id);
        if (apiario == null) {
            ultimaMensagemErro = "Apiário não encontrado.";
            return false;
        }
        
        boolean removido = repositorio.remover(id);
        
        if (removido) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao remover apiário.";
            return false;
        }
    }

    /**
     * Atualiza um apiário existente
     */
    public boolean atualizar(Apiario apiario) {
        if (apiario == null) {
            ultimaMensagemErro = "Apiário não pode ser nulo.";
            return false;
        }
        
        // Validações
        if (apiario.getNome() == null || apiario.getNome().trim().isEmpty()) {
            ultimaMensagemErro = "Nome não pode ser vazio.";
            return false;
        }
        
        if (apiario.getRaca() == null || apiario.getRaca().trim().isEmpty()) {
            ultimaMensagemErro = "Raça não pode ser vazia.";
            return false;
        }
        
        if (apiario.getLocal() == null || apiario.getLocal().trim().isEmpty()) {
            ultimaMensagemErro = "Local não pode ser vazio.";
            return false;
        }
        
        if (apiario.getQntCaixas() <= 0) {
            ultimaMensagemErro = "Quantidade de caixas deve ser maior que zero.";
            return false;
        }

        // Verifica se o apiário existe
        Apiario existente = repositorio.buscarPorId(apiario.getId());
        if (existente == null) {
            ultimaMensagemErro = "Apiário não encontrado.";
            return false;
        }

        // Verifica se o novo nome já existe em outro apiário
        if (repositorio.existeNomeEmOutroApiario(apiario.getNome(), apiario.getId())) {
            ultimaMensagemErro = "Já existe outro apiário com este nome.";
            return false;
        }

        boolean atualizado = repositorio.atualizar(apiario);
        
        if (atualizado) {
            ultimaMensagemErro = "";
            return true;
        } else {
            ultimaMensagemErro = "Erro ao atualizar apiário.";
            return false;
        }
    }

    /**
     * Lista apiários de um local específico
     */
    public List<Apiario> listarPorLocal(String local) {
        return repositorio.listarPorLocal(local);
    }

    /**
     * Lista apiários de uma raça específica
     */
    public List<Apiario> listarPorRaca(String raca) {
        return repositorio.listarPorRaca(raca);
    }

    /**
     * Obtém o total de caixas de todos os apiários
     */
    public int getTotalCaixas() {
        return repositorio.calcularTotalCaixas();
    }

    /**
     * Obtém o total de caixas de um local específico
     */
    public int getTotalCaixasPorLocal(String local) {
        return repositorio.calcularTotalCaixasPorLocal(local);
    }

    /**
     * Obtém o total de apiários cadastrados
     */
    public int getTotalApiarios() {
        return repositorio.getTotalApiarios();
    }

    /**
     * Verifica se existe um apiário com o nome especificado
     */
    public boolean existeNome(String nome) {
        return repositorio.existeNome(nome);
    }
}