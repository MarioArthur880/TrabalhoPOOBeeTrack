package controle;

import modelo.*;
import java.util.ArrayList;
import java.util.List;

public class ControleVisita {
    private List<Visita> listaVisitas = new ArrayList<>();

    public void adicionar(Visita v) {
        listaVisitas.add(v);
    }

    public List<Visita> listar() {
        return listaVisitas;
    }

    public boolean remover(int id) {
        return listaVisitas.removeIf(v -> v.getId() == id);// criar getId() na visita
    }
}