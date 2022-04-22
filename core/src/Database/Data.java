package Database;

import Game.main.Piece;
import Game.oneVsAI.Coordinate;

import java.util.ArrayList;

/**
 * La classe sert à enregistrer toutes les donnees liees a l'avancement d'une partie
 * Des methodes sont aujoutees en 2022 pour amelioration de l'IA.
 */
public class Data {
    public int mode; // 1 pour vs ai; 0 pour 1v1
    public  Piece[] piece;
    public int[][] indice_piece_sur_case_de_plateau = new int[4][4];
    // -1 -> case vide ; i de 0 à 15 -> la piece dans cette case est de l'indice de i
    public int compteur;
    public String[] liste_joueur;

    public int difficulty;
    private int rd;
    private final int MAXSCORE = 10000;
    private final int MAXCALCUL = (int)10e6;

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
        rd = (int)(Math.random()*2.0);
        compteur = rd;
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

        this.difficulty = 0; //0 = eazy; 1 = moderate; 2 = difficult
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
        this.difficulty = data.difficulty;
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
        res.setDifficulty(difficulty);

        return res;
    }

    /**
     * Etant donne l'indice de ligne, obtenir le score de cette ligne
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param line
     * @return
     */
    private int getScoreLine(int line){
        int k_blanc = 1;
        int k_noir = 1;
        int k_rond = 1;
        int k_carre = 1;
        int k_grand =1;
        int k_petit = 1;
        int k_plein = 1;
        int k_creus = 1;

        int s_blanc = 0;
        int s_noir = 0;
        int s_rond = 0;
        int s_carre = 0;
        int s_grand = 0;
        int s_petit = 0;
        int s_plein = 0;
        int s_creus = 0;

        for(int col = 0; col < 4; col ++){
            int indexP = indice_piece_sur_case_de_plateau[line][col];
            if(indexP == -1)
                continue;
            Piece p = piece[indexP];
            //blanc ou noir
            if(p.est_blanche == 1){
                s_blanc++;
                k_noir = 0;
            }else {
                s_noir++;
                k_blanc =0;
            }
            //rond ou carre
            if(p.est_ronde == 1){
                s_rond++;
                k_carre = 0;
            }else {
                s_carre++;
                k_rond =0;
            }
            //grand ou petit
            if(p.est_grande == 1){
                s_grand++;
                k_petit = 0;
            }else {
                s_petit++;
                k_grand =0;
            }
            //plein ou creus
            if(p.est_pleine == 1){
                s_plein++;
                k_creus = 0;
            }else {
                s_creus++;
                k_plein =0;
            }
        }

        int res = 0;
        res += k_blanc*s_blanc + k_noir*s_noir;
        res += k_carre*s_carre + k_rond*s_rond;
        res += k_grand*s_grand + k_petit*s_petit;
        res += k_plein*s_plein + k_creus*s_creus;

        return res;
    }

    /**
     * obtenir le score de toutes les lignes.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getScoreLins(){
        int res = 0;
        for(int line = 0; line < 4; line ++){
            res += getScoreLine(line);
        }
        return res;
    }

    /**
     * Etant donne l'indice de colonne, obtenir le score de cette colonne
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param col
     * @return
     */
    private int getScoreCol(int col){
        int k_blanc = 1;
        int k_noir = 1;
        int k_rond = 1;
        int k_carre = 1;
        int k_grand =1;
        int k_petit = 1;
        int k_plein = 1;
        int k_creus = 1;

        int s_blanc = 0;
        int s_noir = 0;
        int s_rond = 0;
        int s_carre = 0;
        int s_grand = 0;
        int s_petit = 0;
        int s_plein = 0;
        int s_creus = 0;

        for(int line = 0; line < 4; line ++){
            int indexP = indice_piece_sur_case_de_plateau[line][col];
            if(indexP == -1)
                continue;
            Piece p = piece[indexP];
            //blanc ou noir
            if(p.est_blanche == 1){
                s_blanc++;
                k_noir = 0;
            }else {
                s_noir++;
                k_blanc =0;
            }
            //rond ou carre
            if(p.est_ronde == 1){
                s_rond++;
                k_carre = 0;
            }else {
                s_carre++;
                k_rond =0;
            }
            //grand ou petit
            if(p.est_grande == 1){
                s_grand++;
                k_petit = 0;
            }else {
                s_petit++;
                k_grand =0;
            }
            //plein ou creus
            if(p.est_pleine == 1){
                s_plein++;
                k_creus = 0;
            }else {
                s_creus++;
                k_plein =0;
            }
        }

        int res = 0;
        res += k_blanc*s_blanc + k_noir*s_noir;
        res += k_carre*s_carre + k_rond*s_rond;
        res += k_grand*s_grand + k_petit*s_petit;
        res += k_plein*s_plein + k_creus*s_creus;

        return res;
    }

    /**
     * obtenir le score de toutes les colonnes.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getScoreCols(){
        int res = 0;
        for(int col = 0; col < 4; col++){
            res += getScoreCol(col);
        }
        return res;
    }

    /**
     * obtenir le score de diagonal inferieur
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getScoreDiagInf(){
        int k_blanc = 1;
        int k_noir = 1;
        int k_rond = 1;
        int k_carre = 1;
        int k_grand =1;
        int k_petit = 1;
        int k_plein = 1;
        int k_creus = 1;

        int s_blanc = 0;
        int s_noir = 0;
        int s_rond = 0;
        int s_carre = 0;
        int s_grand = 0;
        int s_petit = 0;
        int s_plein = 0;
        int s_creus = 0;

        for(int index = 0; index < 4; index ++){
            int indexP = indice_piece_sur_case_de_plateau[index][index];
            if(indexP == -1)
                continue;
            Piece p = piece[indexP];
            //blanc ou noir
            if(p.est_blanche == 1){
                s_blanc++;
                k_noir = 0;
            }else {
                s_noir++;
                k_blanc =0;
            }
            //rond ou carre
            if(p.est_ronde == 1){
                s_rond++;
                k_carre = 0;
            }else {
                s_carre++;
                k_rond =0;
            }
            //grand ou petit
            if(p.est_grande == 1){
                s_grand++;
                k_petit = 0;
            }else {
                s_petit++;
                k_grand =0;
            }
            //plein ou creus
            if(p.est_pleine == 1){
                s_plein++;
                k_creus = 0;
            }else {
                s_creus++;
                k_plein =0;
            }
        }

        int res = 0;
        res += k_blanc*s_blanc + k_noir*s_noir;
        res += k_carre*s_carre + k_rond*s_rond;
        res += k_grand*s_grand + k_petit*s_petit;
        res += k_plein*s_plein + k_creus*s_creus;

        return res;
    }

    /**
     * obtenir le score de diagonal superieur
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getScoreDiagSup(){
        int k_blanc = 1;
        int k_noir = 1;
        int k_rond = 1;
        int k_carre = 1;
        int k_grand =1;
        int k_petit = 1;
        int k_plein = 1;
        int k_creus = 1;

        int s_blanc = 0;
        int s_noir = 0;
        int s_rond = 0;
        int s_carre = 0;
        int s_grand = 0;
        int s_petit = 0;
        int s_plein = 0;
        int s_creus = 0;

        for(int index = 0; index < 4; index ++){
            int indexP = indice_piece_sur_case_de_plateau[index][3-index];
            if(indexP == -1)
                continue;
            Piece p = piece[indexP];
            //blanc ou noir
            if(p.est_blanche == 1){
                s_blanc++;
                k_noir = 0;
            }else {
                s_noir++;
                k_blanc =0;
            }
            //rond ou carre
            if(p.est_ronde == 1){
                s_rond++;
                k_carre = 0;
            }else {
                s_carre++;
                k_rond =0;
            }
            //grand ou petit
            if(p.est_grande == 1){
                s_grand++;
                k_petit = 0;
            }else {
                s_petit++;
                k_grand =0;
            }
            //plein ou creus
            if(p.est_pleine == 1){
                s_plein++;
                k_creus = 0;
            }else {
                s_creus++;
                k_plein =0;
            }
        }

        int res = 0;
        res += k_blanc*s_blanc + k_noir*s_noir;
        res += k_carre*s_carre + k_rond*s_rond;
        res += k_grand*s_grand + k_petit*s_petit;
        res += k_plein*s_plein + k_creus*s_creus;

        return res;
    }

    /**
     * obtenir le score de toutes les lignes, colonnes et diagonaux.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public int getScore(){
        if(getGameStatus()==1)
            return MAXSCORE;
        int res = 0;
        res += getScoreLins();
        res += getScoreCols();
        res += getScoreDiagInf();
        res += getScoreDiagSup();

        return res;
    }

    /**
     * obtenir les positions où il n'y a pas de piece.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public ArrayList<Coordinate> getPostionsDisponibles(){
        ArrayList<Coordinate> res = new ArrayList<>();
        for(int line = 0; line < 4; line ++){
            for(int col = 0; col < 4; col ++){
                if(indice_piece_sur_case_de_plateau[line][col]==-1){
                    Coordinate pos = new Coordinate(line,col);
                    res.add(pos);
                }
            }
        }
        return res;
    }

    /**
     * obtenir les pieces disponibles
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public ArrayList<Integer> getPiecesDisponibles(){
        ArrayList<Integer> res = new ArrayList<>();
        for(int i = 0; i< 16; i++){
            if(piece[i].est_disponible==1)
                res.add(i);
        }
        return res;
    }

    /**
     * selectionner une piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     */
    public void select(int p){
        piece[p].select();
    }

    /**
     * poser une piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     * @param pos
     */
    public void put(int p, Coordinate pos){
        indice_piece_sur_case_de_plateau[pos.getX()][pos.getY()] = p;
        piece[p].setOnBoard(pos.getX(),pos.getY());
    }

    /**
     * deposer une piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param pos
     */
    public void unPut(Coordinate pos){
        int p = indice_piece_sur_case_de_plateau[pos.getX()][pos.getY()];
        indice_piece_sur_case_de_plateau[pos.getX()][pos.getY()] = -1;
        select(p);
        piece[p].est_disponible=1;
    }


    /**
     * deselectionner une piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     */
    public void unSelect(int p){
        piece[p].est_selectionnee = 0;
    }

    /**
     * obtenir la piece selectionnee
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public int getPieceSelectionne(){
        int res = 0;
        for (Piece p:piece
             ) {
            if(p.est_selectionnee == 1)
                return res;
            res++;
        }
        return  -1;
    }

    /**
     * Obtenir le profondeur dynamique selon le nombre des positions disponibles.
     * author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public int dynamicDepth(){
        int n = 16 - compteur + rd; //C'est le nombre des positions disponibles.
        if(n>1)
            return (int)(0.5 * ( Math.log(MAXCALCUL)/Math.log(n) + 1 )); //Selon le formule de complexite. Veuillez consulter le rapport.
        return 16; //Comme il y a 16 case dans le plateau, tout profondeur > 16 est equivalent au profondeur 16.
    }
}
