
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
    private ChungToi[] partidas;
    private int countUsuarios, countPartidas, maxNumProcessos;
    HashMap<Integer, Usuario> usuarios;

    public ChungToiImpl(int maxNumProcessos) throws RemoteException {
        super();
        this.maxNumProcessos = maxNumProcessos;
        partidas = new ChungToi[maxNumProcessos];
        for (int i = 0; i < maxNumProcessos; i++) {
            partidas[i] = new ChungToi();
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
        return aux;
    }

    @Override
    public synchronized int encerraPartida(int id) throws RemoteException {
        try {
            List<ChungToi> list = new ArrayList<>(Arrays.asList(partidas));
            //list.remove(procuraPartida(id));
            partidas = list.toArray(partidas);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int temPartida(int id) throws RemoteException {
        try {
            int res = 0;
            for (int i = 0; i < maxNumProcessos; i++) {
                if (partidas[countPartidas].isLivre1()) {
                    partidas[countPartidas].setNjogador1(usuarios.get(id).getNome());
                    partidas[countPartidas].setId1(id);
                    System.out.println("Usuário " + usuarios.get(id).getNome() + "(" + id + ")" + " cadastrado na partida " + countPartidas + " como jogador 1");
                    res = 1;
                    break;
                } else if (partidas[countPartidas].isLivre2()) {
                    partidas[countPartidas].setNjogador2(usuarios.get(id).getNome());
                    partidas[countPartidas].setId2(id);
                    System.out.println("Usuário  " + usuarios.get(id).getNome() + "(" + id + ")" + " cadastrado na partida " + countPartidas + " como jogador 2");
                    res = 2;
                    break;
                }
            }
            return res;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int ehMinhaVez(int id) throws RemoteException {
        try {
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized String obtemTabuleiro(int id) throws RemoteException {
        return partidas[1].getTabuleiroString();
    }

    @Override
    public synchronized int posicionaPeca(int id, int x, int y, int orientacao) throws RemoteException {
        try {
            return partidas[0].addPeca(id, x, y, orientacao);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized int movePeca(int id, int x, int y, int sentido, int numero_casas, int orientacao) throws RemoteException {
        try {
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public synchronized String obtemOponente(int id) throws RemoteException {
        String res = "";
        return res;
    }

    @Override
    public synchronized int getIDJogo(int id) {
        int res = 0;
        for (int i = 0; i < maxNumProcessos; i++) {
            if (partidas[countPartidas].isLivre1()) {
                partidas[countPartidas].setNjogador1(usuarios.get(id).getNome());
                partidas[countPartidas].setId1(id);
                System.out.println("Usuário " + usuarios.get(id).getNome() + "(" + id + ")" + " cadastrado na partida " + countPartidas + " como jogador 1");
                res = countPartidas;
                break;
            } else if (partidas[countPartidas].isLivre2()) {
                partidas[countPartidas].setNjogador2(usuarios.get(id).getNome());
                partidas[countPartidas].setId2(id);
                System.out.println("Usuário  " + usuarios.get(id).getNome() + "(" + id + ")" + " cadastrado na partida " + countPartidas + " como jogador 2");
                res = countPartidas;
                break;
            }
        }
        return res;
    }

}
