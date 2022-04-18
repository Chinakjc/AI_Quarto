package Game.oneVsAI.NewAI;

import Database.Data;
import Game.oneVsAI.Combination;
import Game.oneVsAI.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;

public class NewAI {
    private Data data;
    private int depth;

    public NewAI(Data data,int depth){
        this.data = new Data(data);
        this.depth = depth;
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
                    if(s>score){
                        res = pos;
                        score = s;
                    }
                }
                unPut(pos);
            }
            return res;
        }

        for (Coordinate pos :options
             ) {
            put(piece,pos);
            int piece1 = getPiece();
            NewAI ai = new NewAI(data,depth-1);
            return ai.getPostion(piece1);
        }


        return new Coordinate(0,0);
    }

    private int getPiece(){
        //todo
        return 0;
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
