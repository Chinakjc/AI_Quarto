package Game.oneVsAI.NewAI;

import Database.Data;
import Game.oneVsAI.Combination;
import Game.oneVsAI.Coordinate;

import java.util.ArrayList;

public class NewAI {
    private Data data;
    private int depth;

    public NewAI(Data data,int depth){
        this.data = new Data(data);
        this.depth = depth;
    }
    private int getGameStatus(){
        return data.getGameStatus();
    }
    private int getScore(){
        return data.getScore();
    }

    private void put(int piece,Coordinate pos){
        data.put(piece,pos);
    }

    private void unPut(Coordinate pos){
        data.unPut(pos);
    }


    private void select(int p){
        data.select(p);
    }

    private void unSelect(int p){
        data.unSelect(p);
    }

    public ArrayList<Coordinate> getPostionsDisponibles(){
        return data.getPostionsDisponibles();
    }

    public ArrayList<Integer> getPiecesDisponibles(){
        return data.getPiecesDisponibles();
    }

    private Coordinate getPostion(int piece){
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
            if(getGameStatus()==1) //gagne
                return pos;
            if(getGameStatus()==0) { //pas fini
                int piece1 = getPiece();
                select(piece1);
                NewAI newAI = new NewAI(data,depth -1);
                Coordinate pos1 = newAI.getPostion(piece1);
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

    private int getPiece(){
        ArrayList<Integer> options = getPiecesDisponibles();
        if(depth<=0){
            return options.get(0);
        }
        int score = 0;
        Integer res = null;
        for (Integer p:options
             ) {
            select(p);
            Coordinate pos = getPostion(p);
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
