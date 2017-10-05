
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leona
 */
public class Dados implements Serializable {

    private static final long serialVersionUID = 1L;
    private int idJogo;
    public ChungToi jogo;
    public boolean completo = false;

    public Dados() {
    }

    public void setJogo(ChungToi jogo) {
        this.jogo = jogo;
    }

    public void setCompleto(boolean completo) {
        this.completo = completo;
    }

    // Recebe ID do Jogo
    public int getIdJogo() {
        return idJogo;
    }

    // Seta ID do Jogo
    public void setIdJogo(int jogo) {
        this.idJogo = jogo;
    }

    // Verifica se jogo está completo
    public boolean getCompleto() {
        return this.completo;
    }

    // Recebe dados do Jogo
    public ChungToi getJogo() {
        return this.jogo;
    }
}
