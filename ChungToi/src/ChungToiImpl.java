
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private int countUsuarios, countPartidas, maxNumProcessos;
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
        if (countUsuarios == (maxNumProcessos * 2)) {
            return -2;
        }
        for (Integer id : usuarios.keySet()) {
            if (usuarios.get(id).getNome().equals(nome)) {
                return -1;
            }
        }
        int aux = countUsuarios;
        usuarios.put(countUsuarios, new Usuario(nome, countUsuarios));
        System.out.println("Novo usuario " + nome + " registrado no sistema, com ID = " + countUsuarios);
        countUsuarios++;

        int res = 0;
        for (int i = 0; i < maxNumProcessos; i++) {
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
            List<Dados> list = new ArrayList<>(Arrays.asList(partidas));
            list.remove(getIDJogo(id));
            partidas = list.toArray(partidas);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int temPartida(int id) throws RemoteException {
        try {
            if (partidas[getIDJogo(id)].jogo.getId1() == id && !partidas[getIDJogo(id)].jogo.isLivre1() && !partidas[getIDJogo(id)].jogo.isLivre2()) {
                return 1;
            }
            else if (partidas[getIDJogo(id)].jogo.getId2() == id && !partidas[getIDJogo(id)].jogo.isLivre2() && !partidas[getIDJogo(id)].jogo.isLivre1()) {
                return 2;
            }
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int ehMinhaVez(int id) throws RemoteException {
        try {
            if (partidas[getIDJogo(id)].jogo.getId1() == id) {
                if (partidas[getIDJogo(id)].jogo.isVencedor(id)) {
                    return 2;
                }
                if (partidas[getIDJogo(id)].jogo.isVencedor(partidas[getIDJogo(id)].jogo.getId2())) {
                    return 3;
                }
            } else if (partidas[getIDJogo(id)].jogo.getId2() == id) {
                if (partidas[getIDJogo(id)].jogo.isVencedor(id)) {
                    return 2;
                }
                if (partidas[getIDJogo(id)].jogo.isVencedor(partidas[getIDJogo(id)].jogo.getId1())) {
                    return 3;
                }
            } else if (partidas[getIDJogo(id)].jogo.isFimJogo()) {
                return 4;
            } else {
                return partidas[getIDJogo(id)].jogo.getVezJogador() == id ? 1 : 0;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    @Override
    public synchronized String obtemTabuleiro(int id) throws RemoteException {
        return partidas[getIDJogo(id)].jogo.getTabuleiroString();
    }

    @Override
    public synchronized int posicionaPeca(int id, int posicao, int orientacao) throws RemoteException {
        try {
            if(posicao > 8 || posicao < 0)
                return -1;
            return partidas[getIDJogo(id)].jogo.addPeca(id, getPosicao(posicao).getX(), getPosicao(posicao).getY(), orientacao);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int movePeca(int id, int posicao, int sentido, int numero_casas, int orientacao) throws RemoteException {
        try {
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized String obtemOponente(int id) throws RemoteException {
        String res = "";
        if (partidas[getIDJogo(id)].jogo.getId1() == id) {
            res = partidas[getIDJogo(id)].jogo.getNjogador2();
        } else if (partidas[getIDJogo(id)].jogo.getId2() == id) {
            res = partidas[getIDJogo(id)].jogo.getNjogador2();
        }
        return res;
    }

    public synchronized int getIDJogo(int id) {
        int res = -1;
        for (int i = 0; i < maxNumProcessos; i++) {
            if (partidas[i].jogo.getId1() == id || partidas[i].jogo.getId2() == id) {
                res = i;
                break;
            }
        }
        return res;
    }

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

    @Override
    public boolean isDeslocamento(int id) throws RemoteException {
        return partidas[getIDJogo(id)].jogo.isDeslocamento();
    }

}
