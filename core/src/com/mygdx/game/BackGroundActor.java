package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 *Cette  classe a comme objectif de generer le fond d'ecran.
 *Ainsi cette classe est necessaire et est utilisee dans plusieurs packages (Rules, Setting, MenuLoadGame, MenuNewGame, MainMenu, Game.oneVsOne et Game.oneVsAI).
 */
public class BackGroundActor extends Actor {
    private Texture texture;

    public BackGroundActor(){
        super();
        this.texture = new Texture("bg.jpg");
    }

    public BackGroundActor(String string){
        super();
        this.texture = new Texture(string);
    }


    @Override
    public void act(float delta){
        super.act(delta);
    }

    /**
     * Cette methode sert a afficher le fond d'ecran / background
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
        batch.draw(texture,0,0,MainGame.width_current,MainGame.height_current);
    }

}

