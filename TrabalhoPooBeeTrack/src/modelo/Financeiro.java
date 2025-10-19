package modelo;

public class Financeiro {
    private String observacao;
    private int fluxo; // se eh entrada =1 ou saida=0
    private String data;
    private double valor;

    public Financeiro(String observacao, int fluxo, String data, double valor) {
        this.observacao = observacao;
        this.fluxo = fluxo;
        this.data = data;
        this.valor = valor;
    }

}