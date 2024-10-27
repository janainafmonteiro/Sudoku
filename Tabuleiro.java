
package sudoku;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Tabuleiro {
    public static void inicializarQuadrado(int[][] tabuleiro){
        //colocar os numeros de 1 ate 9 na ordem nos quadrados 3x3
        int numero = 1;
        for(int linha = 0; linha < tabuleiro.length; linha++){
            for(int coluna = 0; coluna < tabuleiro.length; coluna++){
                tabuleiro[linha][coluna]= numero;
                numero++;
            }
        }
    }
    public static void aleatorizarQuadrado(int[][] organizado) {
        //alatorizar os quadrados 3x3;
        inicializarQuadrado(organizado);
        Random aleatorio = new Random();
        for(int i=0;i<100;i++){
            int linha = aleatorio.nextInt(3);
            int coluna = aleatorio.nextInt(3);
            int linhaDois = aleatorio.nextInt(3);
            int colunaDois = aleatorio.nextInt(3);

            int aux = organizado[linha][coluna];
            organizado[linha][coluna] = organizado[linhaDois][colunaDois];
            organizado[linhaDois][colunaDois] = aux;
        }
    }
    public static void quadradosOrganizacao(int[][] tabuleiro, int indicesTabuleiro) {
        //colocar cada quadrado 3x3 no lugar certo do quadrado 9x9
        int[][] quadradoTabuleiro = new int[3][3];
        aleatorizarQuadrado(quadradoTabuleiro);
        for(int linha = 0, linhaPrincipal = indicesTabuleiro; linha < 3; linha++, linhaPrincipal++){
            for(int coluna = 0, colunaPrincipal = indicesTabuleiro; coluna < 3; coluna++, colunaPrincipal++){
                tabuleiro[linhaPrincipal][colunaPrincipal]= quadradoTabuleiro[linha][coluna];
            }
        }
    }
    public static void organizarQuadradosPrincipais(int [][] tabuleiro) {
        //preencher os quadrados que não interferem um no outro
        quadradosOrganizacao(tabuleiro, 0);
        quadradosOrganizacao(tabuleiro, 3);
        quadradosOrganizacao(tabuleiro, 6);


    }
    public static boolean resolverSudoku(int[][] tab) {
        Validar validar = new Validar();
        for(int lin = 0; lin < 9; lin++) {
            for(int col = 0; col < 9; col++) {
                if(tab[lin][col] == 0) {
                    for(int num = 1; num <=9; num++) {
                        if(validar.posicaoValida(tab, num, lin, col)) {
                            tab[lin][col] = num;
                            if (resolverSudoku(tab)) {
                                return true;
                            }else {
                                tab[lin][col]= 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    public static int nivel(int nivel){
        switch(nivel){
            case 1 -> {
                return 38;
            }
            case 2 -> { 
                return 33;
            }
            case 3, 4 -> {
                return 28;
            }
            default -> {
                return 0;
            }
                
        }
    }
    public static void mostrarNumero(int[][] tab, int[][] solucao, int nivel){
        int numerosMostrados = nivel(nivel);
        Random ran = new Random();
        int contador=0;
        do{
            int linha = ran.nextInt(9);
            int coluna = ran.nextInt(9);
            if(tab[linha][coluna]==0){
                tab[linha][coluna]=solucao[linha][coluna];
                contador++;
            }
        }while (contador<=numerosMostrados);

    }
    public static void getDicas(int[][] tabuleiro, int[][] tabuleiroSolucao){
        ArrayList<Integer> colunaDisponivel = new ArrayList<>();
        int lin, tamanho, indice;
        Random aleatorio = new Random();
        do{
            lin = aleatorio.nextInt(9);       
            for(int i = 0; i < 9; i++){
                if(tabuleiro[lin][i]==0){
                   colunaDisponivel.add(i);
                }
            }
         tamanho= colunaDisponivel.size();
        }while(tamanho<1);
        indice = aleatorio.nextInt(0, tamanho);     
        int coluna = colunaDisponivel.get(indice);
        tabuleiro[lin][coluna] = tabuleiroSolucao[lin][coluna];
    }    
    public static boolean concluido(int[][] tab, int[][] solucao){
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(tab[i][j]!=solucao[i][j]){
                    return false;
                }
            }
        }
        return true;
    }
    public static void imprimirTabuleiro(int[][] tab) {
    		for(int lin = 0; lin < tab.length; lin++){
                if(lin !=0 && lin %3==0){
                    System.out.println();
                }
                for(int col = 0; col < tab[0].length; col++){
                    if(col !=0 && col %3==0){
                        System.out.print(" ");
                    }
                    System.out.print(tab[lin][col] + "\t");

                }
                System.out.println("\n");

            }
    	}
    public static int pontuacao(int nivel, int acertos, int pontuacao){
        switch(nivel){
            case 1 ->{
                pontuacao= pontuacao+200+(acertos*10);
                return pontuacao;
            }
            case 2 ->{
                pontuacao+= 250+(acertos*13);
                return pontuacao;
                
            }
            case 3 ->{
               pontuacao+= 300+(acertos*17);
                return pontuacao;
            }
            case 4 ->{
               pontuacao+= 350+(acertos*20);
                return pontuacao;
            }
            default->{
                return 0;
            }
        }
    }

    public static void main(String[] args) {
            int[][] tabResolvido = new int[9][9];
             
        do{
            organizarQuadradosPrincipais(tabResolvido);
        }while(!resolverSudoku(tabResolvido));
             int[][] tabuleiro = new int[9][9];
             Scanner input = new Scanner(System.in);
             int nivel = 0;
             System.out.println("ESCOLHA O NÍVEL - 1. FÁCIL | 2. MÉDIO | 3. DIFÍCIL | 4. MESTRE");
            do{
                nivel = input.nextInt();          
            }while(nivel<=0 || nivel>4);
            int pontuacao = 0;
             mostrarNumero(tabuleiro, tabResolvido, nivel);
             imprimirTabuleiro(tabuleiro);
             int linha, coluna, numero, erros=0, acertos=0, dica;
        do{
 
            System.out.println("Gostaria de uma dica? 1.SIM 2.NÃO");
            dica = input.nextInt();
            if(dica==1){
                getDicas(tabuleiro, tabResolvido);
                acertos=0;
                imprimirTabuleiro(tabuleiro);
            }

            do{
                do{
                    System.out.println("Escreva uma linha: ");
                    linha = input.nextInt();
                }while(linha<0 || linha>8);

                do{
                    System.out.println("Escreva uma coluna: ");
                    coluna = input.nextInt();
                }while(coluna<0 || coluna >8);

                do{
                    System.out.println("Escreva um número: ");
                    numero = input.nextInt();                   
                }while(numero<1 || numero>9);
                if(tabuleiro[linha][coluna]!=0){
                    System.out.println("Posição inválida");
                }
            }while(tabuleiro[linha][coluna]!=0);
               
            if(numero==tabResolvido[linha][coluna]){
                tabuleiro[linha][coluna] = numero;
                pontuacao=pontuacao(nivel, acertos, pontuacao);
                acertos++;               
            }else{
                erros++;
                acertos=0;
            }           
            System.out.println("Pontuação: "+ pontuacao);
            System.out.println("Erros: "+ erros);
            imprimirTabuleiro(tabuleiro);
            if(concluido(tabuleiro, tabResolvido)){
                System.out.println("Jogo concluído, digite -1 para sair");
                numero = input.nextInt();
            }
        }while(numero!=-1);
        
        }
}
