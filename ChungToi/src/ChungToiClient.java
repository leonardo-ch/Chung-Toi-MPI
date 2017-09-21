
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

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

            Scanner in = new Scanner(System.in);
            System.out.println("Digite o nome do jogador para cadastro: ");
            String nome = in.nextLine();
            int res = ct.registraJogador(nome);
            validaRegistro(res, nome);

        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.out.println("NotasClient failed.");
        }
    }

    private static void validaRegistro(int res, String nome) {
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
