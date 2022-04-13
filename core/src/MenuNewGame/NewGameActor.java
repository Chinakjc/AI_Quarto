package MenuNewGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;

/**
 * Cette classe sert a afficher la totalite des textures sur la page de Nouvelle Partie
 */
public class NewGameActor extends Actor {
    private Texture texture_1v1;
    private Texture texture_ia;
    private Texture backMenu;
    public int r;
    public int r1;

    public NewGameActor(){
        super();
        this.backMenu = new Texture("MainMenu.png");
        this.texture_1v1 = new Texture("1v1.png");
        this.texture_ia = new Texture("ia.png");
    }

    /**
     * Cette methode permet d'afficher la version modifiee des textures lorsqu'elles sont survolees par le curseur de la souris
     * @param n
     */
    public void select(int n){
        switch (n){
            case 1:
                texture_1v1 = new Texture("1v1G.png");
                break;
            case 2:
                texture_ia = new Texture("iaG.png");
                break;
            default:
                backMenu = new Texture("MainMenuG.png");
                break;
        }
    }

    /**
     * Cette methode permet d'afficher la version de base des textures 1v1, IA, Menu Principal
     */
    public void initialiser(){
        texture_1v1 = new Texture("1v1.png");
        texture_ia = new Texture("ia.png");
        backMenu = new Texture("MainMenu.png");
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode sert a afficher et postionner les textures 1v1, IA, Menu principal
     * @param batch
     * @param parentAlpha
     * @see MainGame#height_current
     * @see MainGame#width_current
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch, parentAlpha);
        if(!isVisible()){
            return;
        }
        batch.setColor(1,1,1,1);
        r = Math.min(MainGame.width_current,MainGame.height_current);
        r = r/4;
        r1= r/2;

        batch.draw(texture_1v1,(MainGame.width_current/2)/2,MainGame.height_current/2, r,r);
        batch.draw(texture_ia,(MainGame.width_current-MainGame.width_current/4)-r ,MainGame.height_current/2,r,r);
        batch.draw(backMenu,(MainGame.width_current/2)-3*r1,MainGame.height_current/6,3*r,r);

    }
}