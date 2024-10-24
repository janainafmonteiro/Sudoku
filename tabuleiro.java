
import java.util.Scanner;
import java.util.Random;

public class tabuleiro {
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
    public static void mostrarNumero(int[][] tab, int[][] solucao, int nivel){
        if(nivel==1){
            nivel = 38;
        }else if(nivel==2){
            nivel = 33;
        }else if(nivel==3 || nivel==4){
            nivel = 28;
        }


        Random ran = new Random();
        int contador=0;
        do{
            int linha = ran.nextInt(9);
            int coluna = ran.nextInt(9);
            if(tab[linha][coluna]==0){
                tab[linha][coluna]=solucao[linha][coluna];
                contador++;
            }
        }while (contador<=nivel);

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
    
    public static int pontuacaoTabuleiro(int pontuacao){
        return pontuacao -= 10;
    }
    public static int pontuacaoTotal(int pontuacao){
        return pontuacao += pontuacao;
    }
    public static int pontuacao( int nivel){
        if(nivel==1 || nivel==38){
            return 233;
        }else if(nivel==2 || nivel==33){
            return 244;
        }else if(nivel==3 || nivel==4 || nivel==28){
            return 255;
        }else{
            return 0;
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
            int pontuacao = pontuacao(nivel);
             mostrarNumero(tabuleiro, tabResolvido, nivel);
             imprimirTabuleiro(tabuleiro);

        do{
            System.out.println("Escreva uma linha: ");
        int linha = input.nextInt();

        System.out.println("Escreva uma coluna: ");
        int coluna = input.nextInt();

        System.out.println("Escreva um número: ");
        int numero = input.nextInt();
        tabuleiro[linha][coluna] = numero;
        if(tabuleiro[linha][coluna]!=tabResolvido[linha][coluna]){
            tabuleiro[linha][coluna] = 0;
            pontuacao = pontuacaoTabuleiro(pontuacao);
        }else{
           pontuacao = pontuacaoTotal(pontuacao);
        }
        System.out.println("Pontuação: "+ pontuacao);
        imprimirTabuleiro(tabuleiro);
        }while(!concluido(tabuleiro, tabResolvido));
        }
}
