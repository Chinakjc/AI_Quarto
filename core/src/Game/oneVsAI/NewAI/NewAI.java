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

    private final int MINSCORE = 0;

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
    }

    /**
     * pour obtenir le statu de jeu.
     * 1 == gagner, 0 == jeu pas fini, -1 == match null
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getGameStatus(){
        return data.getGameStatus();
    }

    /**
     * methode pour mesurer le score d'un etat
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getScore(){
        return data.getScore();
    }

    /**
     * poser une piece à une position
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param piece
     * @param pos
     */
    public void put(int piece,Coordinate pos){
        data.put(piece,pos);
    }

    /**
     * deposer une piece.
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param pos
     */
    private void unPut(Coordinate pos){
        data.unPut(pos);
    }


    /**
     * selectionner une piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     */
    public void select(int p){
        data.select(p);
    }

    /**
     * Deselectionner une piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @param p
     */
    private void unSelect(int p){
        data.unSelect(p);
    }

    /**
     * obtenir la piece sellectionnee
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private int getPieceSelectionnee(){
        return data.getPieceSelectionne();
    }

    /**
     * Obtenir les posisions où il n'y a pas de piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    private ArrayList<Coordinate> getPostionsDisponibles(){
        return data.getPostionsDisponibles();
    }

    /**
     * obtenir les pieces disponibles
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public ArrayList<Integer> getPiecesDisponibles(){
        return data.getPiecesDisponibles();
    }

    /**
     * Obtenir la meilleure position étant donné une piece en utilisant l'algo Min-Max et Alpha-Beta
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
            if(getGameStatus()==0) { //jeu pas fini
                NewAI newAI = new NewAI(depth -1,data);
                int piece1 = newAI.getPiece();
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
                if(s<MINSCORE){ //alpha-beta
                    return pos;
                }
                if(s<score){  //MIN
                    res = pos;
                    score = s;
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
     * obtenir la meilleure piece
     * @author Jincheng KE, Yi Qin
     * @since 2022
     * @return
     */
    public int getPiece(){
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
            if(score < MINSCORE)  // alpha-beta
                return p;
        }
        return res;
    }


}
