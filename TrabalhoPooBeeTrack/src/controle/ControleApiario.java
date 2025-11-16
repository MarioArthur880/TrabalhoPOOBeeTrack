package controle;

import repositorio.RepositorioApiario;
import java.util.List;


public class ControleApiario {
    private RepositorioApiario repositorio;

    public ControleApiario(RepositorioApiario repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Cria um novo apiário com validações
     */
    public boolean criarApiario(String nome, String raca, String local, int qntCaixas) {
        // Validações
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }
        if (raca == null || raca.trim().isEmpty()) {
            return false;
        }
        if (local == null || local.trim().isEmpty()) {
            return false;
        }
        if (qntCaixas < 0) {
            return false;
        }

        Apiario novoApiario = new Apiario(0, nome, raca, local, qntCaixas);
        return repositorio.adicionar(novoApiario);
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
     * Busca um apiário por nome
     */
    public Apiario buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }
        return repositorio.buscarPorNome(nome);
    }

    /**
     * Remove um apiário por ID
     */
    public boolean remover(int id) {
        if (id <= 0) {
            return false;
        }
        return repositorio.remover(id);
    }

    /**
     * Atualiza um apiário existente
     */
    public boolean atualizar(Apiario apiario) {
        if (apiario == null) {
            return false;
        }
        
        // Validações
        if (apiario.getNome() == null || apiario.getNome().trim().isEmpty()) {
            return false;
        }
        if (apiario.getRaca() == null || apiario.getRaca().trim().isEmpty()) {
            return false;
        }
        if (apiario.getLocal() == null || apiario.getLocal().trim().isEmpty()) {
            return false;
        }
        if (apiario.getQntCaixas() < 0) {
            return false;
        }

        return repositorio.atualizar(apiario);
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
}