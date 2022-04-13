package Game.oneVsAI.NewAI;

import Database.Data;

public class NewAI {
    private Data data;

    public NewAI(Data data){
        this.data = new Data(data);
    }

    private int getScore(){
        return data.getScore();
    }


}
