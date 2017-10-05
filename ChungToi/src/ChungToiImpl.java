
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leonardo
 */
public class ChungToiImpl extends UnicastRemoteObject implements ChungToiInterface {

    private static final long serialVersionUID = 541025304039970163L;
    private Dados[] partidas;
    private int countUsuarios;
    private int maxNumProcessos;
    HashMap<Integer, Usuario> usuarios;

    public ChungToiImpl(int maxNumProcessos) throws RemoteException {
        super();
        this.maxNumProcessos = maxNumProcessos;
        partidas = new Dados[maxNumProcessos];
        for (int i = 0; i < maxNumProcessos; i++) {
            partidas[i] = new Dados();
            partidas[i].setJogo(new ChungToi());
        }
        usuarios = new HashMap<>(maxNumProcessos);
    }

    @Override
    public synchronized int registraJogador(String nome) throws RemoteException {
        //Numero maximo de jogadores atingido
        if (countUsuarios == (maxNumProcessos * 2)) {
            return -2;
        }
        //Usuario com esse nome já cadastrado
        for (Integer id : usuarios.keySet()) {
            if (usuarios.get(id).getNome().equals(nome)) {
                return -1;
            }
        }
        //Cadastra o usuário na lista de usuários
        int aux = countUsuarios;
        usuarios.put(countUsuarios, new Usuario(nome, countUsuarios));
        System.out.println("Novo usuario " + nome + " registrado no sistema, com ID = " + countUsuarios);
        countUsuarios++;

        //Cadastra o usuário na primeira partida que tenha vaga aberta
        int res = 0;
        for (int i = 0; i < maxNumProcessos; i++) {
            //Se a primeira vaga está aberta então cadastra o usuário na primeira posição
            if (partidas[i].jogo.isLivre1()) {
                partidas[i].jogo.setNjogador1(usuarios.get(aux).getNome());
                partidas[i].jogo.setId1(aux);
                partidas[i].jogo.setVezJogador(aux);
                System.out.println("Usuário " + usuarios.get(aux).getNome() + "(" + aux + ")" + " cadastrado na partida " + i + " como jogador 1");
                if (!partidas[i].jogo.isLivre2()) {
                    res = 1;
                }
                partidas[i].jogo.setLivre1(false);
                break;
            } else if (partidas[i].jogo.isLivre2()) {
                partidas[i].jogo.setNjogador2(usuarios.get(aux).getNome());
                partidas[i].jogo.setId2(aux);
                System.out.println("Usuário  " + usuarios.get(aux).getNome() + "(" + aux + ")" + " cadastrado na partida " + i + " como jogador 2");
                if (!partidas[i].jogo.isLivre1()) {
                    res = 2;
                }
                partidas[i].jogo.setLivre2(false);
                break;
            }
        }
        return aux;
    }

    @Override
    public synchronized int encerraPartida(int id) throws RemoteException {
        try {

            //Remove os usuarios partida
            usuarios.remove(partidas[getIDJogo(id)].jogo.getId1());
            usuarios.remove(partidas[getIDJogo(id)].jogo.getId2());
            System.out.println("Usuário " + partidas[getIDJogo(id)].jogo.getId1() + " foi removido da lista de jogadores");
            System.out.println("Usuário " + partidas[getIDJogo(id)].jogo.getId2() + " foi removido da lista de jogadores");

            //Remove os jogadores da partidad e limpa o tabuleiroda
            partidas[getIDJogo(id)].completo = false;
            partidas[getIDJogo(id)].jogo.setId1(-1);
            partidas[getIDJogo(id)].jogo.setId2(-1);
            partidas[getIDJogo(id)].jogo.setNjogador1(null);
            partidas[getIDJogo(id)].jogo.setNjogador2(null);
            partidas[getIDJogo(id)].jogo.inicializaVariveis();
            System.out.println("Partida " + getIDJogo(id) + " encerrada");

            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int temPartida(int id) throws RemoteException {
        try {
            //Se o jogador é o jogador 1 e a segunda posição do tabuleiro está ocupada, logo o jogo inicia
            if (partidas[getIDJogo(id)].jogo.getId1() == id && !partidas[getIDJogo(id)].jogo.isLivre1() && !partidas[getIDJogo(id)].jogo.isLivre2()) {
                System.out.println("Usuário "
                        + partidas[getIDJogo(id)].jogo.getNjogador1()
                        + "(" + id + ")"
                        + " tem o oponente " + partidas[getIDJogo(id)].jogo.getNjogador2()
                        + " na partida " + getIDJogo(id));
                return 1;
                //Se o jogador é o jogador 2 e a primeira posição do tabuleiro está ocupada, logo o jogo incia
            } else if (partidas[getIDJogo(id)].jogo.getId2() == id && !partidas[getIDJogo(id)].jogo.isLivre2() && !partidas[getIDJogo(id)].jogo.isLivre1()) {
                System.out.println("Usuário "
                        + partidas[getIDJogo(id)].jogo.getNjogador2()
                        + "(" + id + ")"
                        + " tem o oponente " + partidas[getIDJogo(id)].jogo.getNjogador1()
                        + " na partida " + getIDJogo(id));
                return 2;
            }
            //Ainda não há partida
            return 0;
        } catch (Exception e) {
            //Erro
            return -1;
        }
    }

    @Override
    public synchronized int ehMinhaVez(int id) throws RemoteException {
        try {
            //Se o id é do jogador 1
            if (partidas[getIDJogo(id)].jogo.getId1() == id) {
                //Verifica se o id1 ganhou o jogo
                if (partidas[getIDJogo(id)].jogo.isVencedor(id)) {
                    System.out.println("Jogador " + partidas[getIDJogo(id)].jogo.getNjogador1()
                            + "(" + id + ")"
                            + " vençeu a partida " + getIDJogo(id)
                            + " contra o jogador " + partidas[getIDJogo(id)].jogo.getNjogador2());
                    partidas[getIDJogo(id)].setCompleto(true);
                    return 2;
                } //Verifica se o id2 ganhou o jogo
                else if (partidas[getIDJogo(id)].jogo.isVencedor(partidas[getIDJogo(id)].jogo.getId2())) {
                    System.out.println("Jogador " + partidas[getIDJogo(id)].jogo.getNjogador2()
                            + "(" + partidas[getIDJogo(id)].jogo.getId2() + ")"
                            + " vençeu a partida " + getIDJogo(id)
                            + " contra o jogador " + partidas[getIDJogo(id)].jogo.getNjogador1());
                    partidas[getIDJogo(id)].setCompleto(true);
                    return 3;
                }
                //Se o id é o id2
            } else if (partidas[getIDJogo(id)].jogo.getId2() == id) {
                //Verifica se o id2 ganhou o jogo
                if (partidas[getIDJogo(id)].jogo.isVencedor(id)) {
                    System.out.println("Jogador " + partidas[getIDJogo(id)].jogo.getNjogador2()
                            + "(" + id + ")"
                            + " vençeu a partida " + getIDJogo(id)
                            + " contra o jogador " + partidas[getIDJogo(id)].jogo.getNjogador1());
                    partidas[getIDJogo(id)].setCompleto(true);
                    return 2;
                }//Verifica se o id1 ganhou o jogo
                else if (partidas[getIDJogo(id)].jogo.isVencedor(partidas[getIDJogo(id)].jogo.getId1())) {
                    System.out.println("Jogador " + partidas[getIDJogo(id)].jogo.getNjogador1()
                            + "(" + partidas[getIDJogo(id)].jogo.getId1() + ")"
                            + " vençeu a partida " + getIDJogo(id)
                            + " contra o jogador " + partidas[getIDJogo(id)].jogo.getNjogador2());
                    partidas[getIDJogo(id)].setCompleto(true);
                    return 3;
                }
            }
            //Empatou
            if (partidas[getIDJogo(id)].jogo.isFimJogo()) {
                System.out.println("Ocorreu um empate entre os jogadores: "
                        + partidas[getIDJogo(id)].jogo.getNjogador1() + " e " + partidas[getIDJogo(id)].jogo.getNjogador2()
                        + " da partida " + getIDJogo(id));
                partidas[getIDJogo(id)].setCompleto(true);
                return 4;
            }
            //Caso contrario, retorna de quem é a vez
            return partidas[getIDJogo(id)].jogo.getVezJogador() == id ? 1 : 0;
        } catch (Exception e) {
            Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, e);
        }
        return -1;
    }

    @Override
    public synchronized String obtemTabuleiro(int id) throws RemoteException {
        System.out.println("Usuario " + id + " obteve o tabuleiro da partida " + getIDJogo(id));
        //Obtem o tabuleiro da partida especificada
        return partidas[getIDJogo(id)].jogo.getTabuleiroString();
    }

    @Override
    public synchronized int posicionaPeca(int id, int posicao, int orientacao) throws RemoteException {
        try {
            //Se foi digitado dados de paramentros incorretos
            if (posicao > 8 || posicao < 0 || orientacao > 1 || orientacao < 0) {
                System.out.println("Usuario " + id + " digitou parametros inválidos");
                return -1;
            }
            //Se a partida já foi concluida por WO ou por ganho ou empate
            if (partidas[getIDJogo(id)].completo) {
                System.out.println("A partida " + getIDJogo(id) + " acabou");
                return 2;
            }
            //Tenta realizar o posicionamento da peça
            return partidas[getIDJogo(id)].jogo.addPeca(id, getPosicao(posicao).getX(), getPosicao(posicao).getY(), orientacao);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int movePeca(int id, int posicao, int sentido, int numero_casas, int orientacao) throws RemoteException {
        try {
            //Se foi digitado dados de paramentros incorretos
            if (posicao > 8 || posicao < 0 || sentido > 8 || sentido < 0 || numero_casas > 2 || numero_casas < 0 || orientacao > 1 || orientacao < 0) {
                System.out.println("Usuario " + id + " digitou parametros inválidos");
                return -1;
            }
            //Se a partida já foi concluida por WO ou por ganho ou empate
            if (partidas[getIDJogo(id)].completo) {
                System.out.println("A partida " + getIDJogo(id) + " acabou");
                return 2;
            }
            //Tenta realizar o movimento da peça
            return partidas[getIDJogo(id)].jogo.movePeca(id, getPosicao(posicao).getX(), getPosicao(posicao).getY(), sentido, numero_casas, orientacao);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized String obtemOponente(int id) throws RemoteException {
        String res = "";
        //Se o id é o id1
        if (partidas[getIDJogo(id)].jogo.getId1() == id) {
            //O seu oponente é o jogador 2
            res = partidas[getIDJogo(id)].jogo.getNjogador2();
        }//Se o id é o id1 
        else if (partidas[getIDJogo(id)].jogo.getId2() == id) {
            //O seu oponente é o jogador 2
            res = partidas[getIDJogo(id)].jogo.getNjogador1();
        }
        System.out.println("Partida " + getIDJogo(id) + ": "
                + partidas[getIDJogo(id)].jogo.getNjogador1()
                + " X "
                + partidas[getIDJogo(id)].jogo.getNjogador2());
        return res;
    }

    @Override
    public boolean isDeslocamento(int id) throws RemoteException {
        System.out.println("A partida " + getIDJogo(id) + " iniciou sua fase de deslocamento");
        //Indica quando irá ocorrer a fase de deslocamento
        return partidas[getIDJogo(id)].jogo.isDeslocamento();
    }

    /**
     * Este método retorna o ID da partida em que o jogador está jogando.
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return ­1 (erro) ou ID da partida
     *
     */
    private synchronized int getIDJogo(int id) {
        int res = -1;
        for (int i = 0; i < maxNumProcessos; i++) {
            if (partidas[i].jogo.getId1() == id || partidas[i].jogo.getId2() == id) {
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * Este método retorna um vetor contendo o X e Y correspondedentes a posição
     * do tabuleiro.
     *
     * @param posicao - posicao do tabuleiro
     * @return Vertex contendo o X e Y
     *
     */
    private synchronized static Vertex getPosicao(int posicao) {
        int x = -1, y = -1;
        int aux = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                aux++;
                if (aux == posicao) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        return new Vertex(x, y);
    }
}
