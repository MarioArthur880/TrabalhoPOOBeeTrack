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

    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

}