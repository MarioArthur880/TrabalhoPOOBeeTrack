package controle;

import java.util.List;

public interface IControleVisita extends IControle<Visita> {
    boolean criarVisita(String data, Apiario apiario, int colheita, String tipoVisita);
    List<Visita> listarPorApiario(Apiario apiario);
}