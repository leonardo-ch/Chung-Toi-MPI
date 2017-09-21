
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leonardo
 */
public class ChungToiImpl extends UnicastRemoteObject implements ChungToiIInterface {

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
    public int registraJogador(String nome) throws RemoteException {
        if (countUsuarios == (maxNumProcessos * 2)) {
            return -2;
        }
        for(Integer id: usuarios.keySet()){
            if(usuarios.get(id).getNome().equals(nome)){
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
    public int encerraPartida(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int temPartida(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int ehMinhaVez(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtemTabuleiro(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int posicionaPeca(int id, int x, int y, int orientacao) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int movePeca(int id, int x, int y, int sentido, int numero_casas, int orientacao) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtemOponente(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
