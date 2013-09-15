/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alphabeta;

import java.util.ArrayList;

/**
 *
 * @author Pete
 */
public class Solmu {

    char[][] tilanne;
    boolean max;            //onko tässä solmussa max pelaajan vuoro vai ei

    public Solmu(char[][] tilanne, boolean max) {
        this.tilanne = tilanne;
        this.max = max;
    }

    private ArrayList teeLapset() {
        ArrayList<Solmu> kids = new ArrayList();
        if (max) {
            kids = mahdollisetSiirrot('X');
        } else {
            kids = mahdollisetSiirrot('O');
        }
        return kids;
    }
    
    public ArrayList laskeLapset(){
        return teeLapset();
    }

    private ArrayList mahdollisetSiirrot(char vuorossa) {
        ArrayList<Solmu> kids = new ArrayList();
        
        
        for (int i = 0; i < this.tilanne.length; i++) {
            for (int j = 0; j < this.tilanne[0].length; j++) {      //käydään läpi rivi i
                if (tilanne[i][j] == 'e') {
                    char[][] uusiTilanne = copyArray(tilanne);
                    uusiTilanne[i][j] = vuorossa;
                    Solmu solmu = new Solmu(uusiTilanne, !max);
                    kids.add(solmu);
                }
            }
        }
        return kids;
    }
    
    private char[][] copyArray(char[][] array){
        char[][] copy = new char[array.length][array[0].length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                copy[i][j] = array[i][j];
            }
        }
        return copy;
    }

    public boolean isLopputila() {
        if (tarkistaVaaka()[0] != -1 || tarkistaPysty()[0] != -1 || tarkistaDiagonaaliVasenOikea()[0] != -1 || tarkistaDiagonaaliOikeaVasen()[0] != -1) {
            return true;
        }
        for (int i = 0; i < tilanne.length; i++) {
            for (int j = 0; j < tilanne[0].length; j++) {
                if (tilanne[i][j] == 'e') {
                    return false;
                }
            }
        }
        return true;
    }

    public int[] tarkistaVaaka() {        //palauttaa rivin jolla kolme samaa merkkiä taulukon ensimmäisenä ja toisena arvon jonka tämä antaa pelitilanteelle
        int arvo = 0;
        for (int i = 0; i < tilanne.length; i++) {
            char eka = tilanne[i][0];
            if (eka != 'e') {
                for (int j = 1; j < tilanne[0].length; j++) {
                    if (tilanne[i][j] != eka) {
                        break;
                    } else if (tilanne[i][j] == eka && j == tilanne[0].length - 1) {
                        arvo = arvo(eka);
                        return new int[]{i, arvo};
                    }
                }
            }
        }
        return new int[]{-1, arvo};
    }

    public int[] tarkistaPysty() {        //palauttaa sarakkeen jolla kolme samaa merkkiä
        int arvo = 0;
        for (int i = 0; i < tilanne[0].length; i++) {
            char eka = tilanne[0][i];
            if (eka != 'e') {
                for (int j = 1; j < tilanne.length; j++) {
                    if (tilanne[j][i] != eka) {
                        break;
                    } else if (tilanne[j][i] == eka && j == tilanne.length - 1) {
                        arvo = arvo(eka);
                        return new int[]{i, arvo};
                    }
                }
            }
        }
        return new int[]{-1, arvo};
    }

    //toimii vain 3x3 peleille
    public int[] tarkistaDiagonaaliOikeaVasen() {        //palauttaa 1 jos löytyy kolme samaa merkkiä oikeasta yläkulmasta vasempaan alakulmaan, muuten -1
        int arvo = 0;
        char eka = tilanne[0][0];
        if (eka != 'e') {
            for (int j = 1; j < tilanne.length; j++) {
                if (tilanne[j][j] != eka) {
                    break;
                } else if (tilanne[j][j] == eka && j == tilanne.length - 1) {
                    arvo = arvo(eka);
                    return new int[]{1, arvo};
                }
            }
        }
        return new int[]{-1, arvo};
    }

    //toimii vain 3x3 peleille
    public int[] tarkistaDiagonaaliVasenOikea() {        //palauttaa 1 jos löytyy kolme samaa merkkiä vasemmasta yläkulmasta oikeaan alakulmaan, muuten -1
        int arvo = 0;
        char eka = tilanne[0][tilanne[0].length - 1];
        if (eka != 'e') {
            for (int j = 1; j < tilanne.length; j++) {
                if (tilanne[j][tilanne[0].length - (j + 1)] != eka) {
                    break;
                } else if (tilanne[j][tilanne[0].length - (j + 1)] == eka && j == tilanne.length - 1) {
                    arvo = arvo(eka);
                    return new int[]{1, arvo};
                }
            }
        }
        return new int[]{-1, arvo};
    }

   public int getLopputilanArvo() {
        int[] vaaka = tarkistaVaaka();
        if (vaaka[0] != -1) {
            return vaaka[1];
        }

        int[] pysty = tarkistaPysty();
        if (pysty[0] != -1) {
            return pysty[1];
        }

        int[] diagonaalivas = tarkistaDiagonaaliVasenOikea();
        if (diagonaalivas[0] != -1) {
            return diagonaalivas[1];
        }

        int[] diagonaalioik = tarkistaDiagonaaliOikeaVasen();
        if (diagonaalioik[0] != -1) {
            return diagonaalioik[1];
        }
        return 0; 
    }

    private int arvo(char merkki) {
        if (merkki == 'X') {
            return 1;
        } else {
            return -1;
        }
    }
}
