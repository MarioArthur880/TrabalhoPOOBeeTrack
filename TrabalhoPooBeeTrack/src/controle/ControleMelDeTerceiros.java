package controle;

import java.util.List;
import repositorio.RepositorioMelDeTerceiros;

public class ControleMelDeTerceiros {
    private RepositorioMelDeTerceiros repositorio;

    public ControleMelDeTerceiros(RepositorioMelDeTerceiros repositorio) {
        this.repositorio = repositorio;
    }

    public boolean criarMelDeTerceiros(String produtor, double pesoAntes, double pesoDepois, String data) {
        // Validações
        if (produtor == null || produtor.trim().isEmpty()) {
            return false;
        }
        if (pesoAntes < 0) {
            return false;
        }
        if (pesoDepois < 0) {
            return false;
        }
        if (data == null || data.trim().isEmpty()) {
            return false;
        }

        MelDeTerceiros novoMel = new MelDeTerceiros(0, produtor, pesoAntes, pesoDepois, data);
        return repositorio.adicionar(novoMel);
    }

    public List<MelDeTerceiros> listar() {
        return repositorio.listarTodos();
    }

    public MelDeTerceiros buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return repositorio.buscarPorId(id);
    }

    public boolean remover(int id) {
        if (id <= 0) {
            return false;
        }
        return repositorio.remover(id);
    }

    public boolean atualizar(MelDeTerceiros mel) {
        if (mel == null) {
            return false;
        }
        
        // Validações
        if (mel.getProdutor() == null || mel.getProdutor().trim().isEmpty()) {
            return false;
        }
        if (mel.getPesoAntes() < 0) {
            return false;
        }
        if (mel.getPesoDepois() < 0) {
            return false;
        }
        if (mel.getData() == null || mel.getData().trim().isEmpty()) {
            return false;
        }

        return repositorio.atualizar(mel);
    }

    public List<MelDeTerceiros> listarPorProdutor(String produtor) {
        return repositorio.listarPorProdutor(produtor);
    }
}