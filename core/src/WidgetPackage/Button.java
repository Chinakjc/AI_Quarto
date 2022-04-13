package WidgetPackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * C'est une classe qui permet d'ajouter un bouton plus facilement pour l'ensemble des packages.
 * @see Widget
 */
public class Button extends Widget{
    protected Texture texture;
    protected boolean visible;
    public Button(int width, int height, int sizeUnite, double percentageWidth, double percentageHeight, int position_x, int position_y, String fileName){
        super(width,height,sizeUnite,percentageWidth,percentageHeight,position_x,position_y);
        this.texture = new Texture(fileName);
        this.visible = true;
    }

    /**
     * C'est une methode pour savoir si le bouton est bien selectionne.
     * @param x
     * @param y
     * @return
     */
    public boolean isClicked(int x, int y){
        int l = (int) (sizeUnite*percentageWidth);
        int h = (int) (sizeUnite*percentageHeight);
        return (x>position_x)&&(x<position_x+l)&&(y>position_y)&&(y<position_y+h);
    }

    public boolean getVisible(){
        return visible;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Cette methode sert a colorier le bouton et l'afficher
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch,parentAlpha);
        if(!isVisible()){
            return;
        }

        batch.setColor(this.getColor());
        if(visible)
            batch.draw(texture,position_x,position_y,(int) (sizeUnite*percentageWidth),(int) (sizeUnite*percentageHeight));
    }
}
