package repositorio;

import controle.Apiario;
import controle.Visita;
import java.util.List;

public interface IRepositorioVisita extends IRepositorio<Visita> {
    List<Visita> listarPorApiario(Apiario apiario);
}
