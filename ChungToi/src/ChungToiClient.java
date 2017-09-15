
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leonardo
 */
public class ChungToiClient {

    public static void main(String[] args) {

        try {
            ChungToiIInterface ct = (ChungToiIInterface) Naming.lookup("//localhost:1099/ChungToi");
            int res;

            int id1 = ct.registraJogador("j1");
            System.out.println("registraJogador: " + id1);

            int id2 = ct.registraJogador("j2");
            System.out.println("registraJogador: " + id2);

            System.out.println(ct.temPartida(id1));
            System.out.println(ct.temPartida(id2));

        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.out.println("NotasClient failed.");
        }
    }
}
