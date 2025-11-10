package controle;

public class Apicultor extends Pessoa {
    private String especialidade;
    private int anosExperiencia;

    
    public Apicultor(int id, String nome, String email, String senha, String especialidade, int anosExperiencia) {
        super(id, nome, email, senha);
        this.especialidade = especialidade;
        this.anosExperiencia = anosExperiencia;
    }
} 