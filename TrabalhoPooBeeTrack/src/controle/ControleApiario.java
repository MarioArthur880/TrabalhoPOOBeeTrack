package controle;

import modelo.Apiario;
import java.util.ArrayList;
import java.util.List;

public class ControleApiario {
    private List<Apiario> listaApiarios = new ArrayList<>();

    // Adiciona um novo apiário à lista
    public void adicionar(Apiario a) {
        if (a != null) {
            listaApiarios.add(a);
        }
    }

    // Retorna a lista completa de apiários
    public List<Apiario> listar() {
        return new ArrayList<>(listaApiarios); // proteção contra modificações externas
    }

    // Busca um apiário pelo ID
    public Apiario buscarPorId(int id) {
        for (Apiario a : listaApiarios) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // Remove um apiário pelo ID
    public boolean remover(int id) {
        Apiario a = buscarPorId(id);
        if (a != null) {
            return listaApiarios.remove(a);
        }
        return false;
    }

    // Atualiza um apiário existente com base no ID
    public boolean atualizar(Apiario novoApiario) {
        if (novoApiario == null) return false;

        for (int i = 0; i < listaApiarios.size(); i++) {
            if (listaApiarios.get(i).getId() == novoApiario.getId()) {
                listaApiarios.set(i, novoApiario);
                return true;
            }
        }
        return false;
    }

    // Retorna todos os apiários de um determinado local
    public List<Apiario> listarPorLocal(String local) {
        List<Apiario> resultado = new ArrayList<>();
        if (local == null || local.trim().isEmpty()) return resultado;

        for (Apiario a : listaApiarios) {
            if (a.getLocal() != null && a.getLocal().equalsIgnoreCase(local.trim())) {
                resultado.add(a);
            }
        }
        return resultado;
    }
}
