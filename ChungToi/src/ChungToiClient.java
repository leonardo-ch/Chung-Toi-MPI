
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;
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
            int id = 0;
            try {
                id = ct.registraJogador(nome);
            } catch (RemoteException ex) {
                Logger.getLogger(ChungToiClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            validaRegistro(id, nome);
            long startTime = System.currentTimeMillis(); //fetch starting time
            long waitTime = System.currentTimeMillis();
            while (ct.temPartida(id) <= 0) {
                if ((System.currentTimeMillis() - waitTime) >= 1000) {//1 segundo
                    System.out.println("Aguardando partida ...");
                    waitTime = System.currentTimeMillis();
                }
                if ((System.currentTimeMillis() - startTime) > 120000) {//120 segundos
                    System.out.println("Time out na espera de jogador");
                    ct.encerraPartida(id);
                    System.exit(1);
                }
            }
            boolean deslocamento = false;
            System.out.println("Jogo começou, você jogará com " + ct.obtemOponente(id));
            System.out.println("Iniciou a fase de colocação de peças");
            int posicao = -1, orientacao = -1, sentido = -1, numeroCasas = -1;
            posiciona:
            while (!(ct.ehMinhaVez(id) >= 2) && !deslocamento) {
                if (ct.isDeslocamento(id)) {
                    deslocamento = true;
                    System.out.println("Iniciou a fase de deslocamento de peças");
                    break posiciona;
                }
                startTime = System.currentTimeMillis(); //fetch starting time
                waitTime = System.currentTimeMillis();
                while (ct.ehMinhaVez(id) == 0) {
                    if ((System.currentTimeMillis() - waitTime) >= 1000) {//1 segundo
                        System.out.println("Aguardando jogada do oponente ...");
                        waitTime = System.currentTimeMillis();
                    }
                    if ((System.currentTimeMillis() - startTime) > 60000) {//60 segundos
                        System.out.println("Time out\nVocê ganhou por WO");
                        ct.encerraPartida(id);
                        System.exit(1);
                    }
                }
                imprimeTabuleiro(ct.obtemTabuleiro(id));
                if (ct.ehMinhaVez(id) >= 2) {
                    break posiciona;
                }
                try {
                    System.out.println("Indique a posição do tabuleiro onde a peça deve ser posicionada (de 0 até 8, inclusive) ");
                    posicao = in.nextInt();
                    System.out.println("Indique a orientação da peça (0 correspondendo à orientação perpendicular, e 1 correspondendo à orientação diagonal). ");
                    orientacao = in.nextInt();
                    validaPosicionaPeca(ct.posicionaPeca(id, posicao, orientacao), id, posicao, orientacao);
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida");
                    in = new Scanner(System.in);
                }
                deslocamento = true;
            }
            
            deslocamento:
            while ((!(ct.ehMinhaVez(id) >= 2)) && deslocamento) {
                startTime = System.currentTimeMillis(); //fetch starting time
                waitTime = System.currentTimeMillis();
                while (ct.ehMinhaVez(id) == 0) {
                    if ((System.currentTimeMillis() - waitTime) >= 1000) {//1 segundo
                        System.out.println("Aguardando jogada do oponente ...");
                        waitTime = System.currentTimeMillis();
                    }
                    if ((System.currentTimeMillis() - startTime) > 60000) {//60 segundos
                        System.out.println("Time out\nVocê ganhou por WO");
                        ct.encerraPartida(id);
                        System.exit(1);
                    }
                }
                imprimeTabuleiro(ct.obtemTabuleiro(id));
                if (ct.ehMinhaVez(id) >= 2) {
                    break deslocamento;
                }
                try {
                    System.out.println("Indique a posição do tabuleiro onde se encontra a peça que se deseja mover (de 0 até 8, inclusive)");
                    posicao = in.nextInt();
                    System.out.println("Indique a orientação da peça (0 correspondendo à orientação perpendicular, e 1 correspondendo à orientação diagonal)");
                    orientacao = in.nextInt();
                    System.out.println("Indique o sentido do deslocamento (0 a 8, inclusive)");
                    sentido = in.nextInt();
                    System.out.println("Indique o número de casas deslocadas (0, 1 ou 2)");
                    numeroCasas = in.nextInt();
                    validaMovePeca(ct.movePeca(id, posicao, sentido, numeroCasas, orientacao), id, posicao, sentido, numeroCasas, orientacao);
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida");
                }

            }
            switch (ct.ehMinhaVez(id)) {
                case 2:
                    System.out.println("Você venceu");
                    ct.encerraPartida(id);
                    break;
                case 3:
                    System.out.println("Você perdeu");
                    ct.encerraPartida(id);
                    break;
                case 4:
                    System.out.println("Empatou");
                    ct.encerraPartida(id);
                    break;
                case 5:
                    System.out.println("Você venceu por WO");
                    ct.encerraPartida(id);
                    break;
                case 6:
                    System.out.println("Você perdeu por WO");
                    ct.encerraPartida(id);
                    break;
                default:
                    break;
            }
        } catch (RemoteException | NotBoundException | MalformedURLException ex) {
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
                System.exit(0);
                break;
            case -1:
                System.out.println("Já existe um usuário cadastrado com esse nome");
                System.exit(0);
                break;
            default:
                System.out.println("Novo usuario " + nome + " registrado no sistema, com ID = " + res);
                break;
        }
    }

    private static void validaMovePeca(int movePeca, int id, int posicao, int sentido, int numeroCasas, int orientacao) {
        switch (movePeca) {
            case 2:
                System.out.println("Partida encerrada");
                System.exit(0);
                break;
            case 1:
                String orientacaoString = orientacao == 1 ? "diagonal " : "perpendicular ";
                System.out.println("Peça posicionada em " + posicao + ", com sentido " + sentido + ", com numero de casas igual a " + numeroCasas + " e orientacao " + orientacaoString);
                break;
            case 0:
                System.out.println("Posição inválida, devido a uma casa não ocupada ou fora do tabuleiro");
                break;
            case -1:
                System.out.println("Parametros invalidos");
                break;
            default:
                System.exit(0);
                break;
        }
    }

    private static void validaPosicionaPeca(int posicionaPeca, int id, int posicao, int orientacao) {
        switch (posicionaPeca) {
            case 2:
                System.out.println("Partida encerrada");
                System.exit(0);
                break;
            case 1:
                String orientacaoString = orientacao == 1 ? "diagonal " : "perpendicular ";
                System.out.println("Peça posicionada em " + posicao + " e com orientação de " + orientacaoString);
                break;
            case 0:
                System.out.println("Posição inválida, devido a uma casa já ocupada");
                break;
            case -1:
                System.out.println("Parametros invalidos");
                break;
            default:
                System.exit(0);
                break;
        }
    }

}
