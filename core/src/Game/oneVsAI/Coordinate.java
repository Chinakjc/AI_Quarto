package Game.oneVsAI;

/**
 * C'est une classe pour exprimer la position plus facilement.
 */
public class Coordinate {
    private int x;
    private int y;
    public Coordinate(int x, int y){
        this.x=x;
        this.y=y;
    }

    public int getX() {
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void show(){
        System.out.print("( "+x+" , "+y+ " )");
    }
}