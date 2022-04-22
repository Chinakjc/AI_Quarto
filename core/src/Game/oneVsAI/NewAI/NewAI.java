package Game.oneVsAI.NewAI;

import Database.Data;

import Game.oneVsAI.Coordinate;

import java.util.ArrayList;

/**
 * @author Jincheng KE, Yi Qin
 * @since 2022
 * Nouvelle classe pour améliorer l'IA
 */
public class NewAI {
    private Data data;
    private int depth;

    private Integer betterPiece;

    private final int MINSCORE = 4;

    /**
     * Constructeur de cette classe
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param depth
     * @param data
     */
    public NewAI(int depth,Data data){
        this.data = new Data(data);
        this.depth = depth;
        this.betterPiece = null;
    }

    /**
     * Pour obtenir le statut de jeu.
     * 1 == gagner, 0 == jeu pas fini, -1 == match null
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getGameStatus(){
        return data.getGameStatus();
    }

    /**
     * Méthode pour mesurer le score d'un état.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getScore(){
        return data.getScore();
    }

    /**
     * Poser une pièce à une position.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param piece
     * @param pos
     */
    public void put(int piece,Coordinate pos){
        data.put(piece,pos);
    }

    /**
     * Déposer une pièce.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param pos
     */
    private void unPut(Coordinate pos){
        data.unPut(pos);
    }


    /**
     * Sélectionner une pièce
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     */
    public void select(int p){
        data.select(p);
    }

    /**
     * Désélectionner une pièce
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     */
    private void unSelect(int p){
        data.unSelect(p);
    }

    /**
     * Obtenir la pièce sélectionnée
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getPieceSelectionnee(){
        return data.getPieceSelectionne();
    }

    /**
     * Obtenir les positions où il n'y a pas de pièce
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private ArrayList<Coordinate> getPostionsDisponibles(){
        return data.getPostionsDisponibles();
    }

    /**
     * Obtenir les pièces disponibles
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public ArrayList<Integer> getPiecesDisponibles(){
        return data.getPiecesDisponibles();
    }

    /**
     * Obtenir la meilleure position étant donné une pièce en utilisant l'algo Min-Max et Alpha-Beta
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param piece
     * @return
     */

    public Coordinate getPosition(int piece){
        ArrayList<Coordinate> options = getPostionsDisponibles();
        if(depth<=0)
            return options.get(0);
        if(depth==1){
            int score = 0;
            Coordinate res = null;
            for (Coordinate pos :options
            ) {
                put(piece,pos);
                if(res == null){
                    res = pos;
                    score = getScore();
                }else{
                    int s = getScore();
                    if(s>score){ //MAX
                        res = pos;
                        score = s;
                    }
                }
                unPut(pos);
            }
            return res;
        }

        int score = 0;
        Coordinate res = null;
        for (Coordinate pos :options
        ) {
            put(piece,pos);
            int s = 0;
            if(getGameStatus()==1) //gagne  // alpha-beta
                return pos;
            Integer temp = null;
            if(getGameStatus()==0) { //jeu pas fini
                NewAI newAI = new NewAI(depth -1,data);
                int piece1 = newAI.getPiece();
                temp = piece1;
                select(piece1);
                newAI.select(piece1);
                Coordinate pos1 = newAI.getPosition(piece1);
                put(piece1,pos1);
                s = getScore();
                unPut(pos1);
                unSelect(piece1);
            }
            unPut(pos);
            if(res == null){
                res = pos;
                score = s;

            }else{
                if(s<=MINSCORE){ //alpha-beta
                    betterPiece = temp;
                    return pos;
                }
                if(s<score){  //MIN
                    res = pos;
                    score = s;
                    betterPiece = temp;
                }
            }
        }
        return res;
    }

    /**
     * Obtenir la meilleure position
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public Coordinate getPosition(){
        int piece = getPieceSelectionnee();
        return getPosition(piece);
    }

    /**
     * Obtenir la meilleure pièce
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public int getPiece(){
        if(betterPiece != null){
            int p = betterPiece;
            betterPiece = null;
            return p;
        }
        ArrayList<Integer> options = getPiecesDisponibles();
        if(depth<=0){
            return options.get(0);
        }
        int score = 0;
        Integer res = null;
        for (Integer p:options
             ) {
            select(p);
            Coordinate pos = getPosition(p);
            put(p,pos);
            if(res == null){
                res = p;
                score = getScore();
            }else{
                int s = getScore();
                if(s<score){  //MIN
                    score = s;
                    res = p;
                }
            }
            unPut(pos);
            unSelect(p);
            if(score <= MINSCORE)  // alpha-beta
                return p;
        }
        return res;
    }


}
