package Game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;

/**
 * Cette classe sert a l'affichage du plateau de jeu
 */
public class BoardActor extends Actor {
    private Texture texture;
    private Texture bg;

    public BoardActor(){
        super();
        this.texture = new Texture("plateau.png");
        this.bg = new Texture("carre.png");
    }


    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode sert a afficher le plateau avec un fond blanc transparent
     * @param batch
     * @param parentAlpha
     * @see SizeMainGame
     * @see MainGame#height_current
     * @see MainGame#width_current
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch, parentAlpha);
        if(!isVisible()){
            return;
        }
        SizeMainGame tailleMainGame = new SizeMainGame(MainGame.width_current,MainGame.height_current);
        int r = tailleMainGame.width/4;
        int y = tailleMainGame.position_y_initial_plateau;


        for(int i=0; i<4; i++){
            int x = tailleMainGame.position_x_initial_plateau;
            for(int j=0; j<4; j++){
                batch.setColor(1,1,1,0.1f); //permet d'afficher le fond blanc transparent du plateau (plus joli)
                batch.draw(bg,x,y,r,r);
                batch.setColor(1,1,1,1); //permet d'afficher le tour noir de chaque case du plateau
                batch.draw(texture,x,y,r,r);
                x += r;
            }
            y += r;
        }
    }
}