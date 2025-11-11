package controle;

public class MelDeTerceiros {
    private int id;
    private String produtor;
    private double pesoAntes;
    private double pesoDepois;
    private String data;

    // Construtor padrão
    public MelDeTerceiros() {
    }

    // Construtor com parâmetros
    public MelDeTerceiros(int id, String produtor, double pesoAntes, double pesoDepois, String data) {
        this.id = id;
        this.produtor = produtor;
        this.pesoAntes = pesoAntes;
        this.pesoDepois = pesoDepois;
        this.data = data;
    }

    // Construtor de cópia
    public MelDeTerceiros(MelDeTerceiros outro) {
        this.id = outro.id;
        this.produtor = outro.produtor;
        this.pesoAntes = outro.pesoAntes;
        this.pesoDepois = outro.pesoDepois;
        this.data = outro.data;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProdutor() {
        return produtor;
    }

    public void setProdutor(String produtor) {
        this.produtor = produtor;
    }

    public double getPesoAntes() {
        return pesoAntes;
    }

    public void setPesoAntes(double pesoAntes) {
        this.pesoAntes = pesoAntes;
    }

    public double getPesoDepois() {
        return pesoDepois;
    }

    public void setPesoDepois(double pesoDepois) {
        this.pesoDepois = pesoDepois;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // Método auxiliar (opcional): calcular diferença de peso
    public double getDiferencaPeso() {
        return pesoAntes - pesoDepois;
    }

    // toString (opcional, mas útil para debug)
    @Override
    public String toString() {
        return "MelDeTerceiros{" +
                "id=" + id +
                ", produtor='" + produtor + '\'' +
                ", pesoAntes=" + pesoAntes +
                ", pesoDepois=" + pesoDepois +
                ", data='" + data + '\'' +
                '}';
    }
}
