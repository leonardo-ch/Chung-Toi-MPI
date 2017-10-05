
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

    private int id1, id2;
    private String njogador1, njogador2;
    private boolean livre1, livre2;
    private char[][] tabuleiro;

    private int jogo;
    private int vezJogador;

    private int[] statusJogo;

    public ChungToi() {
        tabuleiro = new char[3][3];
        this.statusJogo = new int[3];
        this.jogo = 0;
        for (int i = 0; i < 3; i++) {
            this.statusJogo[i] = 0;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = '.';
            }
        }
        livre1 = true;
        livre2 = true;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getNjogador1() {
        return njogador1;
    }

    public void setNjogador1(String njogador1) {
        this.njogador1 = njogador1;
    }

    public String getNjogador2() {
        return njogador2;
    }

    public void setNjogador2(String njogador2) {
        this.njogador2 = njogador2;
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

    public int getJogo() {
        return jogo;
    }

    public void setJogo(int jogo) {
        this.jogo = jogo;
    }

    public int getVezJogador() {
        return vezJogador;
    }

    public void setVezJogador(int vezJogador) {
        this.vezJogador = vezJogador;
    }

    public int getStatusJogo() {
        this.setStatusJogo();
        return this.statusJogo[this.jogo];
    }

    public void setStatusJogo(int[] statusJogo) {
        this.statusJogo = statusJogo;
    }

    public char[][] getTabuleiro() {
        return tabuleiro;
    }

    public String getTabuleiroString() {
        String res = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res += tabuleiro[i][j];
            }
        }
        return res;
    }

    public int addPeca(int id, int x, int y, int orientacao) {
        int res = -1;
        //Não é a vez do jogador
        if (this.vezJogador != id) {
            return -3;
        }
        if (tabuleiro[x][y] != '.') {
            return 0;
        }
        // Atribui jogada na célula
        if (id == id1) {
            if (orientacao == 0) {
                tabuleiro[x][y] = 'C';
                res = 1;
            } else if (orientacao == 1) {
                tabuleiro[x][y] = 'c';
                res = 1;
            }
        } else if (id == id2) {
            if (orientacao == 0) {
                tabuleiro[x][y] = 'E';
                res = 1;
            } else if (orientacao == 1) {
                tabuleiro[x][y] = 'e';
                res = 1;
            }
        }
        this.vezJogador = this.getVezJogador() == this.id1 ? this.id2 : this.id1;
        return res;
    }

    public char getCelula(int linha, int coluna) {
        return this.tabuleiro[linha - 1][coluna - 1];
    }

    public boolean isVencedor(int idJogador) {
        int linha = 0;

        //define o marcador
        char marcadorPerpendicular = idJogador == this.id1 ? 'C' : 'E';
        char marcadorDiagonal = idJogador == this.id1 ? 'c' : 'e';

        //percorre as  horizontais
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.tabuleiro[i][j] == marcadorPerpendicular
                        || this.tabuleiro[i][j] == marcadorDiagonal) {
                    linha++;
                }
            }

            if (linha == 3) {
                return true;
            } else {
                linha = 0;
            }
        }

        //percorre as verticais
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.tabuleiro[j][i] == marcadorPerpendicular
                        || this.tabuleiro[i][j] == marcadorDiagonal) {
                    linha++;
                }
            }

            if (linha == 3) {
                return true;
            } else {
                linha = 0;
            }
        }

        //percorre a diagonal esquerda
        for (int i = 0; i < 3; i++) {
            if (this.tabuleiro[i][i] == marcadorPerpendicular
                    || this.tabuleiro[i][i] == marcadorDiagonal) {
                linha++;
            }
        }

        if (linha == 3) {
            return true;
        } else {
            linha = 0;
        }

        //percorre a diagonal direita
        int aux = 0;
        for (int i = 2; i >= 0; i--) {
            if (this.tabuleiro[aux][i] == marcadorPerpendicular
                    || this.tabuleiro[aux][i] == marcadorDiagonal) {
                linha++;
            }
            aux++;
        }

        if (linha == 3) {
            return true;
        }

        return false;
    }

    public boolean isFimJogo() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.tabuleiro[i][j] == '.') {
                    return false;
                }
            }
        }

        return true;
    }

    public void setStatusJogo() {
        if (this.isVencedor(this.id1)) {
            this.statusJogo[this.jogo] = this.id1;
        } else if (this.isVencedor(this.id2)) {
            this.statusJogo[this.jogo] = this.id2;
        } else if (isFimJogo()) {
            this.statusJogo[this.jogo] = -1;
        } else {
            this.statusJogo[this.jogo] = 0;
        }
    }

    public boolean isDeslocamento() {
        int aux = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == '.') {
                    aux++;
                }
            }
        }
        if (aux <= 3) {
            return true;
        }
        return false;
    }

    public int movePeca(int id, int x, int y, int sentido, int numero_casas, int orientacao) {
        //ARUUUUUUUUUUMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        //ARUUUUUUUUUUMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        //ARUUUUUUUUUUMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        //ARUUUUUUUUUUMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        //ARUUUUUUUUUUMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        //ARUUUUUUUUUUMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
        int res = -1;
        //Não é a vez do jogador
        if (this.vezJogador != id) {
            return -3;
        }
        if (tabuleiro[x][y] != '.') {
            return 0;
        }
        // Atribui jogada na célula
        if (id == id1) {
            if (orientacao == 0) {
                tabuleiro[x][y] = 'C';
                res = 1;
            } else if (orientacao == 1) {
                tabuleiro[x][y] = 'c';
                res = 1;
            }
        } else if (id == id2) {
            if (orientacao == 0) {
                tabuleiro[x][y] = 'E';
                res = 1;
            } else if (orientacao == 1) {
                tabuleiro[x][y] = 'e';
                res = 1;
            }
        }
        this.vezJogador = this.getVezJogador() == this.id1 ? this.id2 : this.id1;
        return res;
    }

}
