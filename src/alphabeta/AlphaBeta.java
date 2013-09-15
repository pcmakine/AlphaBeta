/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alphabeta;

/**
 *
 * @author Pete
 */
import java.util.ArrayList;
public class AlphaBeta {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char[][] tilanne = 
                            {{'O', 'X', 'e'},
                            {'O', 'e', 'X'},
                            {'X', 'e', 'O'},};
        Solmu solmu = new Solmu(tilanne, true);
        
        System.out.println("Alphabeta arvo: " + alphaBetaArvo(solmu));
        
                char[][] tilanne2 = 
                            {{'e', 'X', 'e'},
                            {'e', 'O', 'O'},
                            {'e', 'X', 'e'},};
                
        Solmu solmu2 = new Solmu(tilanne2, true);
        
        System.out.println("Alphabeta arvo tyhjältä laudalta: " + alphaBetaArvo(solmu2));
        
    }
    
    private static int alphaBetaArvo(Solmu solmu){
        return maxArvo(solmu, -1, 1);
    }
    
    private static int maxArvo(Solmu solmu, int alpha, int beta){
        int v = Integer.MAX_VALUE * -1;
        if(solmu.isLopputila()){
           return solmu.getLopputilanArvo();
        }
        
        ArrayList<Solmu> lapset = solmu.laskeLapset();
        for (int i = 0; i < lapset.size(); i++) {
            v = Math.max(v, minArvo(lapset.get(i), alpha, beta));
            if (v >= beta) {
                return v;
            }
            alpha = Math.max(alpha, v);
        }
        return v;
    }
    
        private static int minArvo(Solmu solmu, int alpha, int beta){
        int v = Integer.MAX_VALUE;
        if(solmu.isLopputila()){
            return solmu.getLopputilanArvo();
        }
        
        ArrayList<Solmu> lapset = solmu.laskeLapset();
        for (int i = 0; i < lapset.size(); i++) {
            v = Math.min(v, maxArvo(lapset.get(i), alpha, beta));
            if (v <= alpha) {
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;
    }
}
