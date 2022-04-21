package Game.oneVsAI;

import Database.Data;
import Game.main.Piece;

import java.util.Vector;

/**
 * La classe sert a gerer l'intelligence artificielle pour jouer contre l'ordinateur
 */
public class AI_New {
    private int niveau;
    public Data data;
    public Vector<Combination> options;

    public AI_New(int niveau, Data data){
        this.niveau = niveau;
        this.data = new Data(data);
        this.options = this.getOptions();
    }

    public AI_New(AI_New ia){
        this(ia.niveau, ia.data);
    }

    /**
     * C'est une methode qui donne l'indice de la piece selectionnee.
     * @return
     * @see Data
     * @see Piece
     */
    private int getIndexPiecePicked(){
        for(int i=0;i<16;i++){
            if(data.piece[i].est_selectionnee==1) return i;
        }
        return -1;
    }

    /**
     * C'est une methode qui donne les positions disponibles sur le plateau.
     * @return
     * @see Coordinate
     * @see Data
     */
    public Vector<Coordinate> getPositionsFree(){
        Vector<Coordinate> res = new Vector<>();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if(data.indice_piece_sur_case_de_plateau[i][j]==-1)
                    res.add(new Coordinate(i,j));
            }
        }
        return res;
    }

    /**
     * C'est une methode qui donne les pieces disponibles.
     * @return
     * @see Data
     * @see Piece
     */
    private Vector<Integer> getPiecesFree(){
        Vector<Integer> res = new Vector<>();
        for (int i=0;i<16;i++){
            if((data.piece[i].est_disponible==1)&&(data.piece[i].est_selectionnee==0))
                res.add(i);
        }
        return res;
    }

    /**
     * C'est une methode qui donne toutes les possibilites de pieces disponibles et de positions sur le plateau disponibles.
     * @return
     * @see Coordinate
     * @see AI_New#getPositionsFree()
     * @see AI_New#getPiecesFree()
     * @see Combination
     */
    private Vector<Combination> getOptions(){
        Vector<Combination> res = new Vector<>();
        Vector<Coordinate> pos = this.getPositionsFree();
        Vector<Integer> pieces = this.getPiecesFree();
        for (int i=0; i< pos.size();i++){
            if(pieces.size()>0) {
                for (int j = 0; j < pieces.size(); j++) {
                    res.add(new Combination(pos.elementAt(i), pieces.elementAt(j)));
                }
            }
            else{
                res.add(new Combination(pos.elementAt(i),-1));
            }
        }
        return res;
    }

    /**
     * C'est une methode qui peut mettre la piece indiquee sur le plateau a la position donnee.
     * @param indice
     * @param ligne
     * @param colonne
     * @see Game.main.Piece#setOnBoard(int, int)
     * @see Data
     * @see Piece
     */
    public void setOnBoard(int indice, int ligne, int colonne){
        data.piece[indice].setOnBoard(ligne,colonne);
        data.indice_piece_sur_case_de_plateau[ligne][colonne]=indice;
    }

    /**
     * C'est une methode qui peut choisir la piece indiquee.
     * @param indice
     * @see Piece#select()
     * @see Data
     */
    public void selec(int indice){
        data.piece[indice].select();
    }

    /**
     * C'est une methode pour avancer la recursion.
     */
    public void next(){
        niveau--;
    }

    /**
     * C'est une methode pour calculer la meilleure option.
     * @return
     * @see AI_New#getIndexPiecePicked()
     * @see Coordinate
     * @see Combination
     * @see Combination#getPos()
     * @see Combination#getPiece()
     * @see AI_New#setOnBoard(int, int, int)
     * @see AI_New#next()
     * @see AI_New#getOptions()
     */
    public AI_New intelligence(){
        if((niveau ==0)||(data.getGameStatus()!=0))
            return this;
        int p0 = this.getIndexPiecePicked();

        for (int i=0; i< options.size();i++){

            AI_New res = new AI_New(this);
            Coordinate pos = options.elementAt(i).getPos();
            res.setOnBoard(p0,pos.getX(),pos.getY());
            res.next();
            if(res.data.getGameStatus()==1){
                res.options = new Vector<Combination>();
                res.options.add(options.elementAt(i));
                return res;
            }
            int p1 = options.elementAt(i).getPiece();
            res.selec(p1);
            AI_New temp = res.intelligence();
            Vector<Combination> poubelle = new Vector<>();
            if ((res.niveau - temp.niveau) %2  == 1) {// C'est adversaire qui va gagner
                if ((res.niveau - temp.niveau) == 1)
                    options.remove(i);
                else
                    poubelle.add(options.elementAt(i));
            }
            while (((options.size()-poubelle.size())>0)&&(poubelle.size()>0)){
                options.remove(poubelle.elementAt(0));
                poubelle.remove(0);
            }
        }
        if (options.size()==0)
            options = this.getOptions();
        return this;
    }
}
