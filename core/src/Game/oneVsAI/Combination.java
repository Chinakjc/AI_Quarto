package Game.oneVsAI;

/**
 * C'est une classe pour grouper la position (Coordinate) et la piece (int).
 * @see Coordinate
 */
public class Combination {
    private Coordinate pos;
    private int piece;

    public Combination(Coordinate coordonnee, int n){
        this.pos = coordonnee;
        this.piece = n;
    }

    public Coordinate getPos() {
        return this.pos;
    }

    public int getPiece() {
        return this.piece;
    }
}
