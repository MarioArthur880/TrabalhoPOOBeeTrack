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

    public boolean adicionar(Apiario apiario) {
        if (apiario == null) {
            return false;
        }
        Apiario copia = new Apiario(apiario);
        copia.setId(proximoId);
        
        boolean adicionado = listaApiarios.add(copia);
        if (adicionado) {
            proximoId++;
        }
        
        return adicionado;
    }

    public List<Apiario> listarTodos() {
        List<Apiario> copias = new ArrayList<>();
        for (Apiario apiario : listaApiarios) {
            copias.add(new Apiario(apiario));
        }
        return copias;
    }

    public Apiario buscarPorId(int id) {
        for (Apiario apiario : listaApiarios) {
            if (apiario.getId() == id) {
                return new Apiario(apiario);
            }
        }
        return null;
    }

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

    public boolean existeNome(String nome) {
        return buscarPorNome(nome) != null;
    }

    public boolean remover(int id) {
        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == id) {
                listaApiarios.remove(i);
                return true;
            }
        }
        return false;
    }

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

    public int calcularTotalCaixas() {
        int total = 0;
        for (Apiario apiario : listaApiarios) {
            total += apiario.getQntCaixas();
        }
        return total;
    }

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

    public int getTotalApiarios() {
        return listaApiarios.size();
    }

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

    public void limparTodos() {
        listaApiarios.clear();
        proximoId = 1;
    }
}