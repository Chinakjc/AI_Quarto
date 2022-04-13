package MainMenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.MainGame;

/**
 *  Cette classe sert à l'affichage des boutons du menu principal
 */
public class ButtonActorMainMenu extends Actor {

    private Texture texture;
    private ButtonMainMenu bouton;

    public ButtonActorMainMenu(ButtonMainMenu bouton){
        super();
        this.bouton = bouton;
        this.texture = new Texture("bouton"+this.bouton.get_Indice_Texture()+".png");
    }

    public ButtonMainMenu getBouton(){
        return this.bouton;
    }

    /**
     * Cette methode permet d'afficher la texture modifiée du bouton lorsque ce dernier est survolé par la souris
     * @see ButtonMainMenu#select()
     * @see ButtonMainMenu#get_Indice_Texture()
     */
    public void select(){
        this.bouton.select();
        this.texture = new Texture("bouton" + this.bouton.get_Indice_Texture()+".png");
    }

    /**
     * Cette methode permet d'afficher la texture de base du bouton
     * @see ButtonMainMenu#select()
     * @see ButtonMainMenu#get_Indice_Texture()
     */
    public void pas_select(){
        this.bouton.pas_select();
        this.texture = new Texture("bouton"+this.bouton.get_Indice_Texture()+".png");
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode permet d'afficher les boutons a l'ecran en tenant compte de la resolution de la fenetre
     * @param batch
     * @param parentAlpha
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see SizeMainMenu
     * @see ButtonMainMenu#position()
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch, parentAlpha);
        if(!isVisible()){
            return;
        }
        SizeMainMenu taille = new SizeMainMenu(MainGame.width_current,MainGame.height_current);
        batch.draw(this.texture,
                this.bouton.position()[0],this.bouton.position()[1],
                taille.width_boutons,taille.height_boutons);
    }
}