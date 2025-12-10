package repositorio;

import controle.MelDeTerceiros;
import java.util.List;

public interface IRepositorioMelDeTerceiros extends IRepositorio<MelDeTerceiros> {
    List<MelDeTerceiros> listarPorProdutor(String produtor);
    List<MelDeTerceiros> listarPorData(String data);
    double calcularPesoTotalProcessado();
    double calcularPesoProcessadoPorProdutor(String produtor);
    boolean existeProdutor(String produtor);
}