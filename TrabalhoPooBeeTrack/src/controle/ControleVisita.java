package controle;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ControleVisita {
    private List<Visita> listaVisitas = new ArrayList<>();

    // Adiciona uma nova visita à lista
    public void adicionar(Visita v) {
        if (v != null) {
            listaVisitas.add(v);
        }
    }

    // Retorna a lista completa de visitas
    public List<Visita> listar() {
        return new ArrayList<>(listaVisitas); // proteção contra modificações externas
    }

    // Remove uma visita pelo ID
    public boolean remover(int id) {
        return listaVisitas.removeIf(v -> v.getId() == id);
    }

    // Busca uma visita pelo ID
    public Visita buscarPorId(int id) {
        for (Visita v : listaVisitas) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    // Atualiza uma visita existente com base no ID
    public boolean atualizar(Visita novaVisita) {
        if (novaVisita == null) return false;

        for (int i = 0; i < listaVisitas.size(); i++) {
            if (listaVisitas.get(i).getId() == novaVisita.getId()) {
                listaVisitas.set(i, novaVisita);
                return true;
            }
        }
        return false;
    }

    // Retorna todas as visitas de um apiário específico
    public List<Visita> listarPorApiario(Apiario apiario) {
        List<Visita> resultado = new ArrayList<>();
        if (apiario == null) return resultado;

        for (Visita v : listaVisitas) {
            if (v.getApiario() != null && v.getApiario().getId() == apiario.getId()) {
                resultado.add(v);
            }
        }
        return resultado;
    }
}
