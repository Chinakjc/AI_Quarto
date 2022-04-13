package Game.oneVsAI.NewAI;

import Database.Data;
import Game.oneVsAI.Combination;
import Game.oneVsAI.Coordinate;

public class NewAI {
    private Data data;

    public NewAI(Data data){
        this.data = new Data(data);
    }

    private int getScore(){
        //todo
        return data.getScore();
    }

    private Coordinate getPostion(){
        //todo
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
