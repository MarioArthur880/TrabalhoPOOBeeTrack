package modelo;

public class Visita {
    private int id;
    private String data;
    private Apiario apiario;
    private int colheita;
    private String tipoVisita;

    public Visita(int id, String data, Apiario apiario, int colheita, String tipoVisita) {
        this.id = id;
        this.data = data;
        this.apiario = apiario;
        this.colheita = colheita;
        this.tipoVisita = tipoVisita;
    }

    public Visita(Visita outra) {
        this.id = outra.id;
        this.data = outra.data;
        this.apiario = outra.apiario;
        this.colheita = outra.colheita;
        this.tipoVisita = outra.tipoVisita;
    }

    public static Visita criarVisita(int id, String data, Apiario apiario, int colheita, String tipoVisita) {
        if (id <= 0 || data == null || data.trim().isEmpty() ||
            apiario == null || colheita < 0 ||
            tipoVisita == null || tipoVisita.trim().isEmpty()) {
            return null;
        }

        return new Visita(id, data, apiario, colheita, tipoVisita);
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public Apiario getApiario() {
        return apiario;
    }

    public int getColheita() {
        return colheita;
    }

    public String getTipoVisita() {
        return tipoVisita;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setApiario(Apiario apiario) {
        this.apiario = apiario;
    }

    public void setColheita(int colheita) {
        this.colheita = colheita;
    }

    public void setTipoVisita(String tipoVisita) {
        this.tipoVisita = tipoVisita;
    }
}
