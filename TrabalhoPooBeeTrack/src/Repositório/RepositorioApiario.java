package Repositório;

import controle.Apiario;
import java.util.ArrayList;
import java.util.List;

public class RepositorioApiario {
    private List<Apiario> listaApiarios = new ArrayList<>();
    private int proximoId = 1;

    public boolean adicionar(Apiario apiario) {
        if (apiario == null) {
            return false;
        }
        apiario.setId(proximoId);
        listaApiarios.add(apiario);
        proximoId++;
        return true;
    }

    public List<Apiario> listarTodos() {
        return new ArrayList<>(listaApiarios);
    }

    public Apiario buscarPorId(int id) {
        for (Apiario a : listaApiarios) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        Apiario a = buscarPorId(id);
        if (a != null) {
            return listaApiarios.remove(a);
        }
        return false;
    }

    public boolean atualizar(Apiario apiarioAtualizado) {
        if (apiarioAtualizado == null) {
            return false;
        }

        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == apiarioAtualizado.getId()) {
                listaApiarios.set(i, apiarioAtualizado);
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

        for (Apiario a : listaApiarios) {
            if (a.getLocal() != null && a.getLocal().equalsIgnoreCase(local.trim())) {
                resultado.add(a);
            }
        }
        return resultado;
    }
}