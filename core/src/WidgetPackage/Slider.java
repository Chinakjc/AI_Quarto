package WidgetPackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * C'est une classe qui permet d'ajouter et d'afficher un slider (barre de gestion de volume) plus facilement.
 * @see Widget
 */
public class Slider extends Widget{
    protected Texture background;
    protected Texture indicator;
    protected double value; //de 0 à 1;
    protected int positionIndicator_x;
    protected int positionIndicator_y;
    protected int widthBG;
    protected int heightBG;
    protected int widthIndicator;
    protected int heightIndicator;

    public Slider(int width, int height, int sizeUnite, double percentageWidth, double percentageHeight,int position_x, int position_y,String fileName1,String fileName2,double value){
        super(width,height,sizeUnite,percentageWidth,percentageHeight,position_x,position_y);
        this.background = new Texture(fileName1);
        this.indicator = new Texture(fileName2);
        this.value = value;
        this.widthBG= (int)(sizeUnite*percentageWidth);
        this.heightBG= (int)(sizeUnite*percentageHeight);
        this.widthIndicator = this.heightBG;
        this.heightIndicator = this.heightBG*2;
        this.positionIndicator_y = (position_y-(int)((this.heightIndicator-this.heightBG)/2));
        this.positionIndicator_x = (int) (position_x - this.widthIndicator/2 + value*this.widthBG);
    }


    /**
     * C'est une methode pour mettre la nouvelle valeur du slider et met a jour l'affichage de ce slider.
     * @param value
     */
    public void setValue(double value) {
        this.value = value;
        this.positionIndicator_y = (position_y-(int)((this.heightIndicator-this.heightBG)/2));
        this.positionIndicator_x = (int) (position_x - this.widthIndicator/2 + value*this.widthBG);
    }

    /**
     * C'est une methode qui calcule la nouvelle valeur en fonction de la position du slider et qui le met a jour.
     * @param x
     * @param y
     * @see Slider#setValue(double) 
     */
    public void setValue(int x,int y){
        double l = x-position_x;
        l = l/widthBG;
        l = Math.max(0.0,Math.min(l,1.0));
        this.setValue(l);
    }

    public double getValue() {
        return value;
    }

    /**
     * C'est une methode pour verifier si le slider est clique.
     * @param x
     * @param y
     * @return
     */
    public boolean isClicked(int x, int y){
        return (x>position_x)&&(x<position_x+widthBG)&&(y>(position_y-heightBG))&&(y<(position_y+2*heightBG));
    }

    /**
     * Cette methode sert a colorier le slider (background+indicator) et l'afficher
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch,float parentAlpha){
        super.draw(batch,parentAlpha);
        if(!isVisible()){
            return;
        }

        batch.setColor(1,1,1,1);
        batch.draw(background,position_x,position_y,widthBG,heightBG);
        batch.draw(indicator,positionIndicator_x,positionIndicator_y,widthIndicator,heightIndicator);
    }
}
