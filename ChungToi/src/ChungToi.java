
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leonardo
 */
public class ChungToi {

    private Usuario jogador1, jogador2;
    private boolean livre1, livre2;
    private char[][] tabuleiro;

    public ChungToi() {
        tabuleiro = new char[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                tabuleiro[i][j] = '.';
            }
        }
    }

    public Usuario getJogador1() {
        return jogador1;
    }

    public void setJogador1(Usuario jogador1) {
        this.jogador1 = jogador1;
    }

    public Usuario getJogador2() {
        return jogador2;
    }

    public void setJogador2(Usuario jogador2) {
        this.jogador2 = jogador2;
    }

    public void setTabuleiro(char[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }

    public boolean isLivre1() {
        return livre1;
    }

    public void setLivre1(boolean livre1) {
        this.livre1 = livre1;
    }

    public boolean isLivre2() {
        return livre2;
    }

    public void setLivre2(boolean livre2) {
        this.livre2 = livre2;
    }
    
    public String getTabuleiro(){
        String res = "";
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                res += tabuleiro[i][j];
            }
        }
        return res;
    }
}
