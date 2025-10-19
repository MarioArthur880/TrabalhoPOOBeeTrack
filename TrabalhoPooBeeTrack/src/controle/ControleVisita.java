package controle;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ControleVisita {
    private List<Visita> listaVisitas = new ArrayList<>();
    private int proximoId = 1;

    public boolean criarVisita(String data, Apiario apiario, int colheita, String tipoVisita) {
        Visita nova = Visita.criarVisita(proximoId, data, apiario, colheita, tipoVisita);
        if (nova != null) {
            listaVisitas.add(nova);
            proximoId++;
            return true;
        }
        return false;
    }

    public List<Visita> listar() {
        return new ArrayList<>(listaVisitas);
    }

    public boolean remover(int id) {
        return listaVisitas.removeIf(v -> v.getId() == id);
    }

    public Visita buscarPorId(int id) {
        for (Visita v : listaVisitas) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

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
