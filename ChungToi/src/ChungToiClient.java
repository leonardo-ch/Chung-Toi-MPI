
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
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
public class ChungToiClient {

    public static void main(String[] args) {
        try {
            ChungToiInterface ct = (ChungToiInterface) Naming.lookup("//localhost:1099/ChungToi");

            Scanner in = new Scanner(System.in);
            System.out.println("\t\tChung Toi");
            System.out.println("Digite o nome do jogador para cadastro: ");
            String nome = in.nextLine();
            int res = 0;
            int id_jogo = 0;
            try {
                res = ct.registraJogador(nome);
            } catch (RemoteException ex) {
                Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            //int id_jogo = ct.getIdJogo(res);
            validaRegistro(res, nome);
            long startTime = System.currentTimeMillis(); //fetch starting time
            while (ct.temPartida(res) <= 0) {
                System.out.println("Aguardando partida ...");
                Thread.sleep(1);
                if ((System.currentTimeMillis() - startTime) < 120000) {//120 segundos
                    System.out.println("Time out");
                    System.exit(1);
                }
            }
            imprimeTabuleiro(ct.obtemTabuleiro(res));
//            outterloop:
//            try {
//                //ct.temPartida(res);
//                while (ct.temPartida(res) == 0) {
//                    Thread.sleep(1);
//                }
//            } catch (RemoteException ex) {
//                Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                while (ct.ehMinhaVez(res) == 1) {
//                    while (ct.ehMinhaVez(res) == 0) {
//                        Thread.sleep(1);
//                    }
//                }
//            } catch (RemoteException ex) {
//                Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
//            }
        } catch (NotBoundException ex) {
            Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private synchronized static void imprimeTabuleiro(String tabuleiro) {
        System.out.println(
                  tabuleiro.charAt(0) + " | "
                + tabuleiro.charAt(1) + " | "
                + tabuleiro.charAt(2)
                + "\n- + - + -\n"
                + tabuleiro.charAt(3) + " | "
                + tabuleiro.charAt(4) + " | "
                + tabuleiro.charAt(5)
                + "\n- + - + -\n"
                + tabuleiro.charAt(6) + " | "
                + tabuleiro.charAt(7) + " | "
                + tabuleiro.charAt(8));
    }

    private synchronized static void validaRegistro(int res, String nome) {
        switch (res) {
            case -2:
                System.out.println("O numero maximo de jogadores foi atingido");
                break;
            case -1:
                System.out.println("Já existe um usuário cadastrado com esse nome");
                break;
            default:
                System.out.println("Novo usuario " + nome + " registrado no sistema, com ID = " + res);
                break;
        }
    }
    
}
