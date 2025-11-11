package repositorio;

import controle.Apiario;
import controle.Visita;
import java.util.ArrayList;
import java.util.List;

public class RepositorioVisita {
    private List<Visita> listaVisitas = new ArrayList<>();
    private int proximoId = 1;

    public boolean adicionar(Visita visita) {
        if (visita == null) return false;
        visita.setId(proximoId++);
        listaVisitas.add(visita);
        return true;
    }

    public List<Visita> listarTodos() {
        return new ArrayList<>(listaVisitas);
    }

    public Visita buscarPorId(int id) {
        for (Visita v : listaVisitas) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public boolean remover(int id) {
        return listaVisitas.removeIf(v -> v.getId() == id);
    }

    public boolean atualizar(Visita visitaAtualizada) {
        if (visitaAtualizada == null) return false;
        for (int i = 0; i < listaVisitas.size(); i++) {
            if (listaVisitas.get(i).getId() == visitaAtualizada.getId()) {
                listaVisitas.set(i, visitaAtualizada);
                return true;
            }
        }
        return false;
    }

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
