
import java.rmi.Remote;
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
public interface ChungToiIInterface extends Remote {

    /**
     * Este método registra o jogador no jogo
     *
     * @param nome string com o nome do usuário/jogador
     * @return
     * id (valor inteiro) do usuário (que corresponde a um número de identificação único para
     * este usuário durante uma partida); ­1 se este usuário já está cadastrado;
     * ­2 se o número máximo de jogadores tiver sido atingido.
     * @throws RemoteException
     */
    int registraJogador(String nome) throws RemoteException;

    /**
     * Este método encerra a partida
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return ­1 (erro);  0 (ok)
     * @throws RemoteException
     */
    int encerraPartida(int id) throws RemoteException;

    /**
     * Este método indica se existe partida
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return  ­2 (tempo de espera esgotado);  ­1 (erro); 
     * 0 (ainda não há partida);  1 (sim, há partida e o
     * jogador inicia jogando com as peças claras, identificadas, por exemplo, com letras de “C” para
     * deslocamento perpendicular ou “c” para deslocamento diagonal); 2 (sim, há
     * partida e o
     * jogador é o segundo a jogar, com os as peças escuras, identificadas, por exemplo, a letra “E”
     * para deslocamento perpendicular ou “e” para deslocamento diagonal)
     * @throws RemoteException
     */
    int temPartida(int id) throws RemoteException;

    /**
     * Este método indica que o jogador está na sua vez
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return
     * ­2 (erro: ainda não há 2 jogadores registrados na partida), ­1 (erro), 0 (não), 1 (sim), 2
     * (é o vencedor), 3 (é o perdedor), 4 (houve empate), 5 (vencedor por WO), 6 (perdedor por WO)
     *
     * @throws RemoteException
     */
    int ehMinhaVez(int id) throws RemoteException;

    /**
     * Este método retorna o tabuleiro em uma string
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return string vazio em caso de erro ou string representando o tabuleiro de jogo
     * O tabuleiro pode, por exemplo, ser representado por 9 caracteres indicando respectivamente o
     * estado de cada casa (de 0 até 8) do tabuleiro: 'C' (peça clara no sentido perpendicular), 'c' (peça
     * clara no sentido diagonal), 'E' (peça escura no sentido perpendicular), 'e' (peça escura no sentido
     * diagonal), '.' (casa não ocupada). 
     * @throws RemoteException
     */
    String obtemTabuleiro(int id) throws RemoteException;

    /**
     * Este método posiciona a peça do jogador no tabuleiro
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @param x posição do tabuleiro onde a
     * peça deve ser posicionada (de 0 até 8, inclusive)
     * @param y posição do tabuleiro onde a
     * peça deve ser posicionada (de 0 até 8, inclusive)
     * @param orientacao orientação da peça (0 correspondendo à
     * orientação perpendicular, e 1 correspondendo à orientação diagonal)
     * @return
     * 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
     * jogada e ocorra o  time­out 
     * de 60 segundos para envio de jogadas), 1 (tudo certo), 0 (posição
     * inválida, por exemplo, devido a uma casa já ocupada), ­1 (parâmetros inválidos), ­2 (partida não
     * iniciada: ainda não há dois jogadores registrados na partida), ­3 (não é a vez do jogador).
     * @throws RemoteException
     */
    int posicionaPeca(int id, int x, int y, int orientacao) throws RemoteException;

    /**
     * Este método move a peça do jogador no tabuleiro
     *
     * @param id do usuário (obtido através da chamada registraJogador) 
     * @param x posição do tabuleiro onde se
     * encontra a peça que se deseja mover (de 0 até 8, inclusive)
     * @param y posição do tabuleiro onde se
     * encontra a peça que se deseja mover (de 0 até 8, inclusive)
     * @param sentido sentido do deslocamento (0 a 8, inclusive). Para o
     * sentido do deslocamento deve­se usar a seguinte convenção: 0 = diagonal esquerda­superior; 1 =
     * para cima; 2 = diagonal direita­superior; 3 = esquerda; 4 = sem movimento; 5 = direita; 6 =
     * diagonal esquerda­inferior; 7 = para baixo; 8 = diagonal direita­inferior.
     * @param numero_casas número de casas deslocadas (0, 1 ou 2)
     * @param orientacao orientação da peça depois da jogada (0
     * correspondendo a orientação perpendicular, e 1 correspondendo à orientação diagonal)
     * @return
     * 2 (partida encerrada, o que ocorrerá caso o jogador demore muito para enviar a sua
     * jogada e ocorra o time­out de 60 segundos para envio de jogadas), 1 (tudo certo), 0 (movimento
     * inválido, por exemplo, em um sentido e deslocamento que resulta em uma posição ocupada ou
     * fora   do   tabuleiro),   ­1   (parâmetros   inválidos),   ­2   (partida
     *   não   iniciada:   ainda   não   há   dois
     * jogadores registrados na partida), ­3 (não é a vez do jogador).
     * @throws RemoteException
     */
    int movePeca(int id, int x, int y, int sentido, int numero_casas, int orientacao) throws RemoteException;

    /**
     * Este método obtem o nome do oponente
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return string vazio para erro ou string com o nome do oponente
     * @throws RemoteException
     */
    String obtemOponente(int id) throws RemoteException;
    
    /**
     * Este método obtem o id do jogo do jogador
     *
     * @param id do usuário (obtido através da chamada registraJogador)
     * @return string vazio para erro ou string com o nome do oponente
     * @throws RemoteException
     */
    int getIdJogo(int id) throws RemoteException;
}
