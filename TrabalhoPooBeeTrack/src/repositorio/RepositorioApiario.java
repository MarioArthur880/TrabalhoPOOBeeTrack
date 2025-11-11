package repositorio;

import controle.Apiario;
import java.util.ArrayList;
import java.util.List;

public class RepositorioApiario {
    private List<Apiario> listaApiarios;
    private int proximoId;

    public RepositorioApiario() {
        this.listaApiarios = new ArrayList<>();
        this.proximoId = 1;
    }

    /**
     * Adiciona um novo apiário
     * @param apiario O objeto Apiario a ser adicionado
     * @return true se adicionado com sucesso, false caso contrário
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
     * Lista todos os apiários
     * @return Lista com cópias de todos os apiários
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
     * @param id O ID do apiário a ser buscado
     * @return Uma cópia do apiário encontrado ou null se não existir
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
     * Remove um apiário por ID
     * @param id O ID do apiário a ser removido
     * @return true se removido com sucesso, false caso contrário
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
     * @param apiarioAtualizado O objeto com os dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizar(Apiario apiarioAtualizado) {
        if (apiarioAtualizado == null) {
            return false;
        }

        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == apiarioAtualizado.getId()) {
                // Substitui pelo objeto atualizado (mantendo o ID)
                Apiario copia = new Apiario(apiarioAtualizado);
                listaApiarios.set(i, copia);
                return true;
            }
        }
        return false;
    }

    /**
     * Lista todos os apiários de um local específico
     * @param local O local a ser buscado
     * @return Lista com cópias dos apiários do local
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
     * @param raca A raça a ser buscada
     * @return Lista com cópias dos apiários da raça especificada
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
     * Busca apiário por nome
     * @param nome O nome do apiário
     * @return Uma cópia do apiário encontrado ou null se não existir
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
     * Calcula o total de caixas de todos os apiários
     * @return O número total de caixas
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
     * @param local O local a ser calculado
     * @return O número total de caixas no local
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
     * @return O número de apiários no repositório
     */
    public int getTotalApiarios() {
        return listaApiarios.size();
    }

    /**
     * Verifica se existe algum apiário em um local específico
     * @param local O nome do local
     * @return true se existe pelo menos um apiário, false caso contrário
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