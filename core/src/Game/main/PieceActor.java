package Game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;

/**
 * La classe sert a l'affichage des 16 pieces sur l'ecran de jeu
 */
public class PieceActor extends Actor {
    private Texture texture;
    private Piece piece;

    public PieceActor(Piece piece){
        super();
        this.texture = new Texture(piece.getIndexTexture()+".png");
        this.piece = piece;
    }

    public void select(){
        this.piece.select();
    }

    public void mettre_au_plateau(int l,int c){
        this.piece.setOnBoard(l,c);
    }

    public Piece getPiece(){
        return this.piece;
    }


    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode sert a la creation du carre blanc une fois la piece selectionnee ainsi qu'afficher les pieces sur l'ecran de jeu
     * @param batch
     * @param parentAlpha
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see SizeMainGame
     * @see Piece
     * @see Piece#getColorSettings()
     * @see Piece#getSizeSettings()
     * @see Piece#positionSquare()
     * @see Piece#position()
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch, parentAlpha);
        if(!isVisible()){
            return;
        }
        SizeMainGame tailleMainGame = new SizeMainGame(MainGame.width_current,MainGame.height_current);
        int r = tailleMainGame.width/4;
        if(piece.est_selectionnee == 1) {
            batch.setColor(1, 1, 1, 0.5f);
            batch.draw(
                    new Texture("carre.png"),
                    piece.positionSquare()[0], piece.positionSquare()[1],
                     r,  r
            );
        }
        batch.setColor(piece.getColorSettings()[0],piece.getColorSettings()[1],piece.getColorSettings()[2],1); //Colorie les pieces blanches des assets en marron
        batch.draw(
                texture,
                piece.position()[0],piece.position()[1],
                piece.getSizeSettings()*r,piece.getSizeSettings()*r
        );
    }
}
