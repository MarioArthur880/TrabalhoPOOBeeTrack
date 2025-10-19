package modelo;

public class Apiario {
    private int id;
    private String nome;
    private String raca;
    private String local;
    private int qntCaixas;

    public Apiario(int id, String nome, String raca, String local, int qntCaixas) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.local = local;
        this.qntCaixas = qntCaixas;
    }

    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

}