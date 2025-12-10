package controle;

import java.util.List;

public interface IControleMelDeTerceiros extends IControle<MelDeTerceiros> {
    boolean criarMelDeTerceiros(String produtor, double pesoAntes, double pesoDepois, String data);
    List<MelDeTerceiros> listarPorProdutor(String produtor);
}