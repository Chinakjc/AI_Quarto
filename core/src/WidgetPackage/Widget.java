package WidgetPackage;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * C'est une classe abstraite pour definir et ajouter des Widgets plus facilement.
 * @see Actor
 */
public abstract class Widget extends Actor{
    protected int width;
    protected int height;
    protected int sizeUnite;
    protected double percentageWidth;
    protected double percentageHeight;
    protected int position_x;
    protected int position_y;

    public Widget(int width, int height, int sizeUnite, double percentageWidth, double percentageHeight,int position_x, int position_y){
        this.width = width;
        this.height = height;
        this.sizeUnite = sizeUnite;
        this.percentageWidth = percentageWidth;
        this.percentageHeight = percentageHeight;
        this.position_x = position_x;
        this.position_y = position_y;
    }

    @Override
    public void act(float delta){
        super.act(delta);
    }
}
