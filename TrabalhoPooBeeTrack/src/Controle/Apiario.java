package Controle;

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

    public Apiario(Apiario outro) {
        this.id = outro.id;
        this.nome = outro.nome;
        this.raca = outro.raca;
        this.local = outro.local;
        this.qntCaixas = outro.qntCaixas;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public String getLocal() {
        return local;
    }

    public int getQntCaixas() {
        return qntCaixas;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setQntCaixas(int qntCaixas) {
        this.qntCaixas = qntCaixas;
    }
}