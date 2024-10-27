
package sudoku;

public class Validar {
    public boolean posicaoValida(int[][] tab, int num, int lin, int col) {
        return !possuiNumLinha(tab, num, lin) && !possuiNumColuna(tab, num, col) && !possuiNumQuadrado(tab, num, lin, col);
    }
    public static boolean possuiNumLinha(int[][] tab, int num, int lin){
        for(int i = 0; i < 9; i++){
            if(tab[lin][i] == num){
                return true;
            }
        }
        return false;
    }
    public static boolean possuiNumColuna(int[][] tab, int num, int col){
        for(int i = 0; i < 9; i++){
            if(tab[i][col] == num){
                return true;
            }
        }
        return false;
    }
    public static boolean possuiNumQuadrado(int[][] tab, int num, int lin, int col) {
        int linQuadrado = lin - lin % 3;
        int colQuadrado = col - col % 3;
        for(int i = linQuadrado; i < linQuadrado + 3; i++){
            for(int j = colQuadrado; j < colQuadrado + 3; j++){
                if( tab[i][j] == num){
                    return true;

                }
            }
        }

    return false;
    }
}
