
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    private final ChungToi[] partidas;

    public ChungToiImpl(int maxNumProcessos) throws RemoteException {
        super();
        partidas = new ChungToi[maxNumProcessos];
        for (int i = 0; i < maxNumProcessos; i++) {
            partidas[i] = new ChungToi();
        }
    }

    @Override
    public int registraJogador(String nome) throws RemoteException {
        return 1;
    }

    @Override
    public int encerraPartida(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int temPartida(int id) throws RemoteException {
        return 1;
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
    public int posicionaPeca(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int movePeca(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtemOponente(int id) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
