package WidgetPackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * C'est une classe qui permet d'ajouter et d'afficher le logo plus facilement pour l'ensemble des packages.
 * @see Widget
 */
public class Logo extends Widget{
    protected Texture texture;
    protected float parameterAlpha;
    public Logo(int width, int height, int sizeUnite, double percentageWidth, double percentageHeight, int position_x, int position_y, String fileName){
        super(width,height,sizeUnite,percentageWidth,percentageHeight,position_x,position_y);
        this.texture = new Texture(fileName);
        this.parameterAlpha = 0.3f;
    }

    public void setParameterAlpha(float parameterAlpha) {
        this.parameterAlpha = parameterAlpha;
    }

    /**
     * Cette methode sert a colorier le logo et l'afficher
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch,parentAlpha);
        if(!isVisible()){
            return;
        }

        batch.setColor(1,1,1,parameterAlpha);
        batch.draw(texture,position_x,position_y,(int) (sizeUnite*percentageWidth),(int) (sizeUnite*percentageHeight));
    }
}
