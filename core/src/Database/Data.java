package Database;

import Game.main.Piece;

/**
 * La classe sert à enregistrer toutes les donnees liees a l'avancement d'une partie
 */
public class Data {
    public int mode; // 1 pour vs ai; 0 pour 1v1
    public  Piece[] piece;
    public int[][] indice_piece_sur_case_de_plateau = new int[4][4];
    // -1 -> case vide ; i de 0 à 15 -> la piece dans cette case est de l'indice de i
    public int compteur;
    public String[] liste_joueur;

    public Data(int mode){
        this.mode = mode;
        piece = new Piece[16];
        liste_joueur= new String[2];
        if(mode == 1) {
            liste_joueur[0] = "Ordinateur";
            liste_joueur[1] = "Joueur";
        }
        else{
            liste_joueur[0] = "Joueur 1";
            liste_joueur[1] = "Joueur 2";
        }
        compteur = (int)(Math.random()*2.0);
        int indice_piece = 0;
        for (int blanche = 0; blanche < 2; blanche++) {
            for (int ronde = 0; ronde < 2; ronde++) {
                for (int grande = 0; grande < 2; grande++) {
                    for (int pleine = 0; pleine < 2; pleine++) {
                        piece[indice_piece] = new Piece(blanche, ronde, grande, pleine, 1, 0);
                        indice_piece++;
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                indice_piece_sur_case_de_plateau[i][j] = -1;
            }
        }
    }
    public Data(Data data){
        this.mode = data.mode;
        this.piece=new Piece[16];
        for(int i=0;i<16;i++) this.piece[i] = new Piece(data.piece[i]);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                indice_piece_sur_case_de_plateau[i][j] = data.indice_piece_sur_case_de_plateau[i][j];
            }
        }
        this.compteur = data.compteur;
        this.liste_joueur = new String[]{data.liste_joueur[0],data.liste_joueur[1]};
    }

    /**
     * C'est une methode qui verifie la ligne indiquee
     * @param ligne
     * @return
     * @see Piece
     */
    private boolean isLineDone(int ligne) {
        boolean k1 = true;
        boolean k2 = true;
        boolean k3 = true;
        boolean k4 = true;
        int t1 = indice_piece_sur_case_de_plateau[ligne][0];
        if (t1 == -1) return false;
        Piece p = piece[t1];
        for (int colonne = 1; colonne < 4; colonne++) {
            int t2 = indice_piece_sur_case_de_plateau[ligne][colonne];
            if (t2 == -1) return false;
            Piece q = piece[t2];
            k1 = k1 && (p.est_blanche == q.est_blanche);
            k2 = k2 && (p.est_ronde == q.est_ronde);
            k3 = k3 && (p.est_grande == q.est_grande);
            k4 = k4 && (p.est_pleine == q.est_pleine);
        }
        return k1 || k2 || k3 || k4;
    }

    /**
     * C'est une methode qui verifie la colonne indiquee
     * @param colonne
     * @return
     * @see Piece
     */
    private boolean isColumnDone(int colonne) {
        boolean k1 = true;
        boolean k2 = true;
        boolean k3 = true;
        boolean k4 = true;
        int t1 = indice_piece_sur_case_de_plateau[0][colonne];
        if (t1 == -1) return false;
        Piece p = piece[t1];
        for (int ligne = 1; ligne < 4; ligne++) {
            int t2 = indice_piece_sur_case_de_plateau[ligne][colonne];
            if (t2 == -1) return false;
            Piece q = piece[t2];
            k1 = k1 && (p.est_blanche == q.est_blanche);
            k2 = k2 && (p.est_ronde == q.est_ronde);
            k3 = k3 && (p.est_grande == q.est_grande);
            k4 = k4 && (p.est_pleine == q.est_pleine);
        }
        return k1 || k2 || k3 || k4;
    }

    /**
     * C'est une methode qui verifie la premiere diagonale.
     * @return
     * @see Piece
     */
    private boolean isDiagonal1Done(){
        boolean k1 = true;
        boolean k2 = true;
        boolean k3 = true;
        boolean k4 = true;
        int t1 = indice_piece_sur_case_de_plateau[0][0];
        if (t1 == -1) return false;
        Piece p = piece[t1];
        for (int i = 1; i < 4; i++) {
            int t2 = indice_piece_sur_case_de_plateau[i][i];
            if (t2 == -1) return false;
            Piece q = piece[t2];
            k1 = k1 && (p.est_blanche == q.est_blanche);
            k2 = k2 && (p.est_ronde == q.est_ronde);
            k3 = k3 && (p.est_grande == q.est_grande);
            k4 = k4 && (p.est_pleine == q.est_pleine);
        }
        return k1 || k2 || k3 || k4;
    }

    /**
     * C'est une methode qui verifie la seconde diagonale.
     * @return
     * @see Piece
     */
    private boolean isDiagonal2Done(){
        boolean k1 = true;
        boolean k2 = true;
        boolean k3 = true;
        boolean k4 = true;
        int t1 = indice_piece_sur_case_de_plateau[0][3];
        if (t1 == -1) return false;
        Piece p = piece[t1];
        for (int i = 1; i < 4; i++) {
            int t2 = indice_piece_sur_case_de_plateau[i][3-i];
            if (t2 == -1) return false;
            Piece q = piece[t2];
            k1 = k1 && (p.est_blanche == q.est_blanche);
            k2 = k2 && (p.est_ronde == q.est_ronde);
            k3 = k3 && (p.est_grande == q.est_grande);
            k4 = k4 && (p.est_pleine == q.est_pleine);
        }
        return k1 || k2 || k3 || k4;
    }

    /**
     * C'est une methode qui verifie toutes les lignes.
     * @return
     * @see Data#isLineDone(int)
     */
    private boolean isLinesDone() {
        boolean res = false;
        for (int ligne = 0; ligne < 4; ligne++) {
            res = res || this.isLineDone(ligne);
        }
        return res;
    }

    /**
     * C'est une methode qui verifie toutes les colonnes.
     * @return
     * @see Data#isColumnDone(int)
     */
    private boolean isColumnsDone() {
        boolean res = false;
        for (int colonne = 0; colonne < 4; colonne++) {
            res = res || this.isColumnDone(colonne);
        }
        return res;
    }

    /**
     * C'est une methode qui verifie toutes les diagonales.
     * @return
     * @see Data#isDiagonal1Done()
     * @see Data#isDiagonal2Done()
     */
    private boolean isDiagonalsDone(){
        return this.isDiagonal1Done()||this.isDiagonal2Done();
    }


    /**
     * C'est une methode qui verifie le plateau.
     * @return
     * @see Data#isDiagonalsDone()
     * @see Data#isLinesDone()
     * @see Data#isColumnsDone()
     */
    private boolean isBoardDone(){
        return  this.isDiagonalsDone() ||
                this.isLinesDone() ||
                this.isColumnsDone();
    }

    /**
     * C'est une methode qui verifie s'il y a des cases vide sur la plateau.
     * @return
     */
    private boolean existe_la_case_vide(){
        for (int ligne=0;ligne<4;ligne++){
            for(int colonne=0;colonne<4;colonne++){
                if(indice_piece_sur_case_de_plateau[ligne][colonne] == -1) return true;
            }
        }
        return false;
    }

    /**
     * C'est une methode qui donne le statut de Jeu.
     * Il retourne 1 si un joueur a gagne, 0 si le jeu n'est pas encore fini, et -1 pour le cas de match nul.
     * @return
     * @see Data#isBoardDone()
     * @see Data#existe_la_case_vide()
     */
    public int getGameStatus(){
        if(this.isBoardDone()) return 1; //un joueur a gagne.
        if(this.existe_la_case_vide()) return 0;    //jeu pas fini.
        return -1;                                  // match nul.
    }

    /**
     * C'est une methode qui peut transformer les datas essentiels dans l'objet Data et retourner un objet de classe Backup.
     * @return
     * @see Backup
     * @see Backup#setCompteur(int)
     * @see Backup#setMode(int)
     * @see Backup#setPlayers(String, String)
     * @see Backup#setInfoPieces(Piece[])
     * @see Piece
     */
    public Backup toBackup(){
        Backup res = new Backup();
        res.setCompteur(compteur);
        res.setMode(mode);
        res.setPlayers(liste_joueur[0],liste_joueur[1] );
        res.setInfoPieces(piece);

        return res;
    }
}
