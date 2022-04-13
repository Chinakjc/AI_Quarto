package MainMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;
/**
 *Cette classe a comme objectif l'affichage des textures (logo du jeu, le logo "pegi 3" et les feuillages
 */
public class LogoActorMainMenu extends Actor{
    private Texture logo;
    private Texture pegi;
    private Texture leaf;

    public LogoActorMainMenu(){
        super();
        this.logo = new Texture("Logo.png");
        this.pegi = new Texture("pegi.png");
        this.leaf = new Texture("leaf.png");
    }


    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode sert a afficher a l'ecran les textures Logo, Pegi, Leaf en fonction de la taille de la fenetre
     * @param batch
     * @param parentAlpha
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see SizeMainMenu
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch, parentAlpha);
        if(!isVisible()){
            return;
        }
        SizeMainMenu taille = new SizeMainMenu(MainGame.width_current,MainGame.height_current);


        batch.draw(logo,
                taille.position_x_initial_logo,taille.position_y_initial_logo,
                taille.width_logo,taille.height_logo);
        
        batch.draw(pegi,
                taille.position_x_initial_pegi,taille.position_y_initial_pegi,
                taille.width_pegi,taille.height_pegi);
        
        
        batch.draw(leaf,
                taille.position_x_initial_leaf,taille.position_y_initial_leaf,
                taille.width_leaf,taille.height_leaf);
        
    }
}
