package Game.main;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;

/**
 * Cette classe sert a afficher le logo en jeu
 */
public class LogoActorMainGame extends Actor{
    private Texture texture;


    public LogoActorMainGame(){
        super();
        this.texture = new Texture("Logo.png");
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode sert a afficher le logo du jeu durant une partie, en transparent
     * @param batch
     * @param parentAlpha
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see SizeMainGame
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch, parentAlpha);
        if(!isVisible()){
            return;
        }
        SizeMainGame taille = new SizeMainGame(MainGame.width_current,MainGame.height_current);
        batch.setColor(1,1,1,0.35f);
        batch.draw(texture,
                taille.position_x_logo,taille.position_y_logo,
                taille.width/4*3,taille.height/4);

    }
}
