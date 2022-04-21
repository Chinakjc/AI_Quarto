package Game.oneVsAI.NewAI;

import Database.Data;
import Game.oneVsAI.Combination;
import Game.oneVsAI.Coordinate;

import java.util.ArrayList;

public class NewAI {
    private Data data;
    private int depth;

    private final int MINSCORE = 4;

    public NewAI(int depth,Data data){
        this.data = new Data(data);
        this.depth = depth;
    }
    private int getGameStatus(){
        return data.getGameStatus();
    }
    private int getScore(){
        return data.getScore();
    }

    public void put(int piece,Coordinate pos){
        data.put(piece,pos);
    }

    private void unPut(Coordinate pos){
        data.unPut(pos);
    }


    public void select(int p){
        data.select(p);
    }

    private void unSelect(int p){
        data.unSelect(p);
    }

    private int getPieceSelectionnee(){
        return data.getPieceSelectionne();
    }
    private ArrayList<Coordinate> getPostionsDisponibles(){
        return data.getPostionsDisponibles();
    }

    public ArrayList<Integer> getPiecesDisponibles(){
        return data.getPiecesDisponibles();
    }

    public Coordinate getPosition(int piece){
        ArrayList<Coordinate> options = getPostionsDisponibles();
        //System.out.println("len = "+options.size());
        if(depth<=0)
            return options.get(0);
        if(depth==1){
            int score = 0;
            Coordinate res = null;
            for (Coordinate pos :options
            ) {
                //System.out.println("Test0");
                put(piece,pos);
                //System.out.println("Test1");
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
                //System.out.println("Test3");
                unPut(pos);
                //System.out.println("Test4");
            }
            return res;
        }

        int score = 0;
        Coordinate res = null;
        for (Coordinate pos :options
        ) {
            //System.out.println("x = "+pos.getX());
            put(piece,pos);
            int s = 0;
            if(getGameStatus()==1) //gagne
                return pos;
            if(getGameStatus()==0) { //pas fini
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
                if(s<score){  //MIN
                    res = pos;
                    score = s;
                }
            }
        }
        return res;
    }

    public Coordinate getPosition(){
        int piece = getPieceSelectionnee();
        return getPosition(piece);
    }
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
            if(score < MINSCORE)
                return p;
        }
        return res;
    }

    public Combination AI(){
        //if choisir la piece alors choir une piece par hasard
        // return
        //else
        // pos = getPosition()
        // update data
        // p = getPiece()
        // return new Combination(pos,p)


        //todo
        return new Combination(new Coordinate(0,0),0);
    }
}
