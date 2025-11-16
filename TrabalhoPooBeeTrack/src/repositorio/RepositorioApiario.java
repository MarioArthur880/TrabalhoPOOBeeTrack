package repositorio;

import controle.Apiario;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável por gerenciar a persistência de apiários
 */
public class RepositorioApiario {
    private List<Apiario> listaApiarios;
    private int proximoId;

    public RepositorioApiario() {
        this.listaApiarios = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Adiciona um novo apiário ao repositório
     */
    public boolean adicionar(Apiario apiario) {
        if (apiario == null) {
            return false;
        }
        
        // Cria uma cópia para evitar modificações externas
        Apiario copia = new Apiario(apiario);
        copia.setId(proximoId);
        
        boolean adicionado = listaApiarios.add(copia);
        if (adicionado) {
            proximoId++;
        }
        
        return adicionado;
    }

    /**
     * Lista todos os apiários cadastrados
     */
    public List<Apiario> listarTodos() {
        List<Apiario> copias = new ArrayList<>();
        for (Apiario apiario : listaApiarios) {
            copias.add(new Apiario(apiario));
        }
        return copias;
    }

    /**
     * Busca um apiário por ID
     */
    public Apiario buscarPorId(int id) {
        for (Apiario apiario : listaApiarios) {
            if (apiario.getId() == id) {
                return new Apiario(apiario);
            }
        }
        return null;
    }

    /**
     * Busca apiário por nome (retorna o primeiro encontrado)
     */
    public Apiario buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getNome().equalsIgnoreCase(nome.trim())) {
                return new Apiario(apiario);
            }
        }
        return null;
    }

    /**
     * Busca todos os apiários com um determinado nome
     */
    public List<Apiario> buscarTodosPorNome(String nome) {
        List<Apiario> resultado = new ArrayList<>();
        
        if (nome == null || nome.trim().isEmpty()) {
            return resultado;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getNome().equalsIgnoreCase(nome.trim())) {
                resultado.add(new Apiario(apiario));
            }
        }
        
        return resultado;
    }

    /**
     * Verifica se existe um apiário com o nome especificado
     */
    public boolean existeNome(String nome) {
        return buscarPorNome(nome) != null;
    }

    /**
     * Remove um apiário por ID
     */
    public boolean remover(int id) {
        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == id) {
                listaApiarios.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Atualiza um apiário existente
     */
    public boolean atualizar(Apiario apiarioAtualizado) {
        if (apiarioAtualizado == null) {
            return false;
        }

        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == apiarioAtualizado.getId()) {
                Apiario copia = new Apiario(apiarioAtualizado);
                listaApiarios.set(i, copia);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se existe outro apiário com o mesmo nome (excluindo o ID fornecido)
     */
    public boolean existeNomeEmOutroApiario(String nome, int idExcluir) {
        if (nome == null || nome.trim().isEmpty()) {
            return false;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getId() != idExcluir && 
                apiario.getNome().equalsIgnoreCase(nome.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lista apiários por local
     */
    public List<Apiario> listarPorLocal(String local) {
        List<Apiario> resultado = new ArrayList<>();
        
        if (local == null || local.trim().isEmpty()) {
            return resultado;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getLocal().equalsIgnoreCase(local.trim())) {
                resultado.add(new Apiario(apiario));
            }
        }
        
        return resultado;
    }

    /**
     * Lista apiários por raça de abelha
     */
    public List<Apiario> listarPorRaca(String raca) {
        List<Apiario> resultado = new ArrayList<>();
        
        if (raca == null || raca.trim().isEmpty()) {
            return resultado;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getRaca().equalsIgnoreCase(raca.trim())) {
                resultado.add(new Apiario(apiario));
            }
        }
        
        return resultado;
    }

    /**
     * Calcula o total de caixas de todos os apiários
     */
    public int calcularTotalCaixas() {
        int total = 0;
        for (Apiario apiario : listaApiarios) {
            total += apiario.getQntCaixas();
        }
        return total;
    }

    /**
     * Calcula o total de caixas por local
     */
    public int calcularTotalCaixasPorLocal(String local) {
        int total = 0;
        
        if (local == null || local.trim().isEmpty()) {
            return total;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getLocal().equalsIgnoreCase(local.trim())) {
                total += apiario.getQntCaixas();
            }
        }
        
        return total;
    }

    /**
     * Retorna a quantidade total de apiários
     */
    public int getTotalApiarios() {
        return listaApiarios.size();
    }

    /**
     * Verifica se existe algum apiário em um local específico
     */
    public boolean existeLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            return false;
        }

        for (Apiario apiario : listaApiarios) {
            if (apiario.getLocal().equalsIgnoreCase(local.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Limpa todos os apiários do repositório (útil para testes)
     */
    public void limparTodos() {
        listaApiarios.clear();
        proximoId = 1;
    }
}