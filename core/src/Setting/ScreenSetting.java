package Setting;


import Database.Setting;
import MainMenu.ScreenMainMenu;
import WidgetPackage.Button;
import WidgetPackage.Logo;
import WidgetPackage.Slider;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.*;

import java.awt.*;

/**
 * Cette classe a comme objectif l'affichage de la page Regles couple a SizeSettings.
 */
public class ScreenSetting implements Screen {
    private MainGame game;
    private Setting setting;
    private int MAXWIDTH;
    private int MAXHEIGHT;
    private int MINWIDTH;
    private int MINHEIGHT;
    private SizeSetting sizeSetting;
    private BackGroundActor backGroundActor;
    private Button[] buttons0;
    private Button[] buttons1;
    private Slider slider;
    private Stage stage;
    private Button mut;
    private Button unMut;
    private Button quit;
    private Button toFullScreen;
    private Button toWindow;
    private Button rollBack;
    private BitmapFont font;
    private Label.LabelStyle style;
    private Label labelMode;
    private Label labelWidth;
    private Label labelHeight;
    private Label labelVol;
    private Logo logo;

    private int compter;

    private boolean open;

    private float oldVolume;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Windows8.wav"));


    private String mode(){
        if(setting.getIsFullscreen())
            return "Fullscreen";
        return "Window";
    }



    public ScreenSetting(MainGame game) {
    	
        this.game = game;
        this.setting = new Setting();
        if(setting.readSetting());

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.MAXWIDTH = gd.getDisplayMode().getWidth();
        this.MAXHEIGHT = gd.getDisplayMode().getHeight();
        this.MINWIDTH = 1024;
        this.MINHEIGHT = 640;
        this.sizeSetting = new SizeSetting(MainGame.width_current,MainGame.height_current);
        this.buttons0 = new Button[2];
        this.buttons1 = new Button[2];
        this.stage = new Stage();
        this.backGroundActor = new BackGroundActor("options.jpg");
        this.stage.addActor(backGroundActor);
        
        this.slider = new Slider(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double)sizeSetting.width_slider/(double)sizeSetting.unite
                ,(double) sizeSetting.height_slider/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_slider, sizeSetting.position_y_initial_slider
                ,"black.jpg","blue.jpg",setting.getBaseVol());
        this.stage.addActor(slider);
        

        buttons0[0] = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_boutons,sizeSetting.position_y_initial_boutons
                ,"boutonU.png");
        this.stage.addActor(buttons0[0]);
        
        
        buttons0[1] = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_slider+sizeSetting.width_slider-sizeSetting.width_boutons
                ,sizeSetting.position_y_initial_boutons
                ,"boutonD.png");
        this.stage.addActor(buttons0[1]);
        

        buttons1[0] = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_boutons
                ,sizeSetting.position_y_initial_boutons+2*sizeSetting.unite
                ,"boutonU.png");
        this.stage.addActor(buttons1[0]);
        
        
        buttons1[1] = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_slider+sizeSetting.width_slider-sizeSetting.width_boutons
                ,sizeSetting.position_y_initial_boutons+2*sizeSetting.unite
                ,"boutonD.png");               
        this.stage.addActor(buttons1[1]);

        //son coupe
        mut = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_boutons - sizeSetting.width_boutons - sizeSetting.unite
                ,sizeSetting.position_y_initial_slider +(sizeSetting.height_slider - sizeSetting.height_boutons)/2
                ,"mute1.png");          
        this.stage.addActor(mut);

        //son active
        unMut = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,(double) sizeSetting.width_boutons/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_boutons - sizeSetting.width_boutons - sizeSetting.unite
                ,sizeSetting.position_y_initial_slider +(sizeSetting.height_slider - sizeSetting.height_boutons)/2
                ,"unmute1.png");
        this.stage.addActor(unMut);

        //Bouton vers Menu principal
        quit = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,MainGame.width_current - sizeSetting.size_smallButton
                ,0
                ,"quit.png");
        this.stage.addActor(quit);
        
        //Bouton fleches vers l'exterieur
        toFullScreen = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,MainGame.width_current - sizeSetting.size_smallButton
                ,MainGame.height_current - sizeSetting.size_smallButton
                ,"toFullScreen.png");
        this.stage.addActor(toFullScreen);
        
        //Bouton fleches vers l'interieur
        toWindow = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,MainGame.width_current - sizeSetting.size_smallButton
                ,MainGame.height_current - sizeSetting.size_smallButton
                ,"toWindow.png");
        this.stage.addActor(toWindow);
        
        //Bouton Reinitialiser/Reset
        rollBack = new Button(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,(double) sizeSetting.size_smallButton/(double) sizeSetting.unite
                ,0
                ,0
                ,"rollback.png");
        this.stage.addActor(rollBack);


        if(setting.getBaseVol()==0){
            mut.setVisible(true);
            unMut.setVisible(false);
        }
        else {
            mut.setVisible(false);
            unMut.setVisible(true);
        }

        if(!setting.getIsFullscreen()){
            toFullScreen.setVisible(true);
            toWindow.setVisible(false);
        }
        else{
            toFullScreen.setVisible(false);
            toWindow.setVisible(true);
        }

        this.font = new BitmapFont(Gdx.files.internal("font/TimesNewRoman.fnt"));
        this.style = new Label.LabelStyle();
        this.style.font = this.font;
        this.style.fontColor = Color.BLACK;

        
        labelMode = new Label("Mode : " + mode(),this.style);
        labelWidth = new Label("Width : "+this.setting.getWidth(),this.style);
        labelHeight = new Label("Height : "+this.setting.getHeight(),this.style);
        labelVol = new Label("Volume : "+(int)(100.0*setting.getBaseVol())+"%",this.style);

        labelMode.setFontScale(sizeSetting.fontScale);
        labelWidth.setFontScale(sizeSetting.fontScale);
        labelHeight.setFontScale(sizeSetting.fontScale);
        labelVol.setFontScale(sizeSetting.fontScale);

        labelMode.setPosition(sizeSetting.position_x_initial_label
                ,sizeSetting.position_y_initial_boutons+3*sizeSetting.unite+sizeSetting.height_boutons/2);
        labelWidth.setPosition(sizeSetting.position_x_initial_label
                ,sizeSetting.position_y_initial_boutons+2*sizeSetting.unite+sizeSetting.height_boutons/2);
        labelHeight.setPosition(sizeSetting.position_x_initial_label
                ,sizeSetting.position_y_initial_boutons+sizeSetting.height_boutons/2);
        labelVol.setPosition(sizeSetting.position_x_initial_label
                ,sizeSetting.position_y_initial_slider+sizeSetting.height_slider/2);

        this.stage.addActor(labelMode);
        this.stage.addActor(labelWidth);
        this.stage.addActor(labelHeight);
        this.stage.addActor(labelVol);

        logo = new Logo(MainGame.width_current,MainGame.height_current
                ,sizeSetting.unite
                ,(double) sizeSetting.width_logo/(double) sizeSetting.unite
                ,(double) sizeSetting.height_logo/(double) sizeSetting.unite
                ,sizeSetting.position_x_initial_logo,sizeSetting.position_y_initial_logo
                ,"Logo.png");
        logo.setParameterAlpha(0.2f);
        this.stage.addActor(logo);

        this.oldVolume = setting.getBaseVol();

        compter = setting.getFps()/3;
    }

    /**
     * Cette methode permet d'afficher tous les elements des options (mode d'affichage/resolution/volume) et de les rendre fonctionnels et adapte a la fenetre de l'ecran
     * Il cree aussi des sons pour tester le volume
     * @param delta
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see Slider#isClicked(int, int)
     * @see Setting
     * @see Setting#getBaseVol()
     * @see Setting#getIsFullscreen()
     * @see Setting#setBaseVol(float)
     * @see Setting#writeSetting()
     * @see Setting#setFullscreen(boolean)
     * @see Setting#setWidth(int)
     * @see Setting#setHeight(int)
     * @see Setting#getHeight()
     * @see Setting#getWidth()
     * @see Setting#getFps()
     * @see Button#setVisible(boolean)
     * @see Slider#setValue(int, int)
     * @see Slider#setValue(double)
     * @see Slider#getValue()
     * @see Tools.Vol#setBase(float)
     * @see ScreenMainMenu
     */
    @Override
    public void render(float delta){
        compter--; //buffer (temps avant possibilité d'interaction sur la fenetre)
    	
        ScreenUtils.clear(1,1,1,1);

        if((MainGame.height_current!=Gdx.graphics.getHeight())||(MainGame.width_current!=Gdx.graphics.getWidth())){
            MainGame.width_current=Gdx.graphics.getWidth();

            MainGame.height_current=Gdx.graphics.getHeight();
            this.game.setScreen(new ScreenSetting(this.game)); // on adapte la fenetre a l'ecran
        }

        int x= Gdx.input.getX();
        int y = MainGame.height_current - Gdx.input.getY();

        if(Gdx.input.isTouched()&&slider.isClicked(x,y)&&(compter<0)) //pour permettre de deplasser la barre dans le slider apres avoir clique dessus et de deplacer
            open = true;                                              // cette barre meme si nous ne sommes plus dessus, tant que nous n'avons pas lache le click
        if(!Gdx.input.isTouched())
            open = false;

        if(setting.getBaseVol()==0){ //si le son est coupe, afficher le bouton "mute"
            mut.setVisible(true);
            unMut.setVisible(false);
        }
        else { //sinon on affiche le bouton "unmute"
            mut.setVisible(false);
            unMut.setVisible(true);
        }

        if(setting.getIsFullscreen()){ //si on est en plein ecran, afficher le bouton avec les fleches a l'interieur, si on est en fenetre, afficher le bouton avec les fleches a l'exterieur
            toFullScreen.setVisible(false);
            toWindow.setVisible(true);
        }
        else {
            toFullScreen.setVisible(true);
            toWindow.setVisible(false);
        }

        if(open){ //si on touche au slider, on change l'ancienne valeur par la nouvelle
            slider.setValue(x,y);
            setting.setBaseVol((float) slider.getValue());
        }
        if(!open){ //quand on relache le slider
            if(oldVolume!=setting.getBaseVol()){ //on vérifie si a la derniere operation du slider, on a fait changer le volume. Si oui, on joue le son de test
                oldVolume = setting.getBaseVol();
                sound.play(oldVolume);
            }

            if((Gdx.input.isTouched())&&(unMut.isClicked(x,y))&&(compter<0)){ //si on clique sur le bouton pour mute, alors le volume passe a 0%
                setting.setBaseVol(0.0f);
            }

            if((Gdx.input.isTouched())&&(rollBack.isClicked(x,y))&&(compter<0)){ //si on clique sur reinitialiser les parametres, ils reviennent comme par defaut
                setting = new Setting();
            }

            if((Gdx.input.isTouched())&&(quit.isClicked(x,y))&&(compter<0)){//si on clique sur le bouton de la porte, on sauvegarde les parametres et on retourne au menu principal
                setting.writeSetting();
                MainGame.vol.setBase(setting.getBaseVol());
                this.dispose();
                game.setScreen(new ScreenMainMenu(game));
            }
        }

        if((!open)&&(!setting.getIsFullscreen())){ // si on est en fenetre
            if((Gdx.input.isTouched())&&toFullScreen.isClicked(x,y)&&(compter<0)){ //si on clique sur le bouton pour repasser en plein ecran
                setting.setFullscreen(true); //fullscreen = true

                if(MAXWIDTH>MAXHEIGHT){ //pour adapter a la resolution maximale de l'ecran
                    double scale = (double) MAXWIDTH/(double) MAXHEIGHT;
                    setting.setWidth((int)(Math.min(scale*setting.getHeight(),MAXWIDTH)));
                }
                else{
                    double scale = (double) MAXHEIGHT/(double) MAXWIDTH;
                    setting.setHeight((int)Math.min(scale*setting.getWidth(),MAXHEIGHT));
                }
                compter = setting.getFps()/3; //buffer
            }
            if ((Gdx.input.isTouched())&&(buttons1[0].isClicked(x,y))&&(compter<0)){ //quand on clique sur le bouton pour augmenter la largeur de la resolution, on augmente de 15 en 15 (pixels)
                int temp = setting.getWidth();
                temp = Math.min(temp+15,MAXWIDTH);
                setting.setWidth(temp);
            }

            if ((Gdx.input.isTouched())&&(buttons0[0].isClicked(x,y))&&(compter<0)){ //quand on clique sur le bouton pour augmenter la hauteur de la resolution, on augmente de 15 en 15 (pixels)
                int temp = setting.getHeight();
                temp = Math.min(temp+15,MAXHEIGHT);
                setting.setHeight(temp);
            }

            if((Gdx.input.isTouched())&&(buttons1[1].isClicked(x,y))&&(compter<0)){ //quand on clique sur le bouton pour augmenter la largeur de la resolution, on augmente de 15 en 15 (pixels)
                int w = setting.getWidth();
                int h = setting.getHeight();
                int temp = w-15;
                if(temp<MINWIDTH){ //verification pour eviter des problemes d'affichage du a une resolution trop basse
                    if(h>=MINWIDTH){
                        temp = Math.max(temp,MINHEIGHT);
                    }
                    else{
                        temp = MINWIDTH;
                    }
                }
                setting.setWidth(temp);
            }
            if((Gdx.input.isTouched())&&(buttons0[1].isClicked(x,y))&&(compter<0)){ //quand on clique sur le bouton pour augmenter la hauteur de la resolution, on augmente de 15 en 15 (pixels)
                int w = setting.getWidth();
                int h = setting.getHeight();
                int temp = h-15;
                if(temp<MINWIDTH){ //verification pour eviter des problemes d'affichage du a une resolution trop basse
                    if(w>=MINWIDTH){
                        temp = Math.max(temp,MINHEIGHT);
                    }
                    else{
                        temp = MINWIDTH;
                    }
                }
                setting.setHeight(temp);
            }
        }
        if((!open)&&(setting.getIsFullscreen())){ //si nous sommes en plein ecran
            if((Gdx.input.isTouched())&&(toWindow.isClicked(x,y))&&(compter<0)){ //et qu'on clique sur le bouton avec les fleches vers l'interieur
                setting.setFullscreen(false); //fullscreen=false
                compter = setting.getFps()/3; //buffer
            }

            if ((Gdx.input.isTouched())&&(buttons1[0].isClicked(x,y))&&(compter<0)){ //si on augmente la largeur de la fenetre
                double scale = (double) MAXHEIGHT/(double) MAXWIDTH;
                int w = setting.getWidth();
                w = Math.min(w+15,MAXWIDTH);
                int h = (int) (w*scale); //on modifie aussi la hauteur pour garder les proportions de l'ecran (16:9, 4:3, ...)
                if(h>MAXHEIGHT){ //verification que la hauteur ne depasse pas le maximum
                    h = MINHEIGHT;
                    w = (int)(h/scale);
                }
                setting.setWidth(w);
                setting.setHeight(h);
            }

            if ((Gdx.input.isTouched())&&(buttons0[0].isClicked(x,y))&&(compter<0)){ //si on augmente la hauteur de la fenetre
                double scale = (double) MAXWIDTH/(double) MAXHEIGHT;
                int h = setting.getHeight();
                h = Math.min(h+15,MAXHEIGHT);
                int w = (int) (h*scale); //on modifie aussi la largeur pour garder les proportions de l'ecran (16:9, 4:3, ...)
                if(w>MAXWIDTH){ //verification que la largeur ne depasse pas le maximum
                    w = MINWIDTH;
                    h = (int)(w/scale);
                }
                setting.setWidth(w);
                setting.setHeight(h);
            }

            if((Gdx.input.isTouched())&&(buttons1[1].isClicked(x,y))&&(compter<0)){ //si on diminue la largeur de la fenetre
                int w = setting.getWidth();
                int h ;
                double scale = (double) MAXWIDTH/(double) MAXHEIGHT;
                w -= 15;
                h = (int) (w/scale); //on modifie aussi la hauteur pour garder les proportions de l'ecran (16:9, 4:3, ...)
                if(w>h){ //on bloque a une valeur minimum, en gardant les proportions
                    if(h<MINHEIGHT){
                        h = MINHEIGHT;
                        w = (int)(h * scale);
                        if(w<MINWIDTH){
                            w = MINWIDTH;
                            h = (int)(w/scale);
                        }
                    }
                    else {
                        if(w<MINWIDTH){
                            w =MINWIDTH;
                            h = (int)(w/scale);
                        }
                    }
                }
                else{
                    if(w<MINHEIGHT){
                        w = MINHEIGHT;
                        h = (int)(w /scale);
                        if(h<MINWIDTH){
                            h = MINWIDTH;
                            w = (int)(h*scale);
                        }
                    }
                    else {
                        if(h<MINWIDTH){
                            h =MINWIDTH;
                            w = (int)(h*scale);
                        }
                    }
                }
                setting.setWidth(w);
                setting.setHeight(h);
            }
            if((Gdx.input.isTouched())&&(buttons0[1].isClicked(x,y))&&(compter<0)){ //si on diminue la hauteur de la fenetre
                int h = setting.getHeight();
                int w ;
                double scale = (double) MAXWIDTH/(double) MAXHEIGHT;
                h -= 15;
                w = (int) (h*scale); //on modifie aussi la largeur pour garder les proportions de l'ecran (16:9, 4:3, ...)
                if(w>h){ //on bloque a une valeur minimum, en gardant les proportions
                    if(h<MINHEIGHT){
                        h = MINHEIGHT;
                        w = (int)(h * scale);
                        if(w<MINWIDTH){
                            w = MINWIDTH;
                            h = (int)(w/scale);
                        }
                    }
                    else {
                        if(w<MINWIDTH){
                            w =MINWIDTH;
                            h = (int)(w/scale);
                        }
                    }
                }
                else{
                    if(w<MINHEIGHT){
                        w = MINHEIGHT;
                        h = (int)(w /scale);
                        if(h<MINWIDTH){
                            h = MINWIDTH;
                            w = (int)(h*scale);
                        }
                    }
                    else {
                        if(h<MINWIDTH){
                            h =MINWIDTH;
                            w = (int)(h*scale);
                        }
                    }
                }
                setting.setWidth(w);
                setting.setHeight(h);
            }
        }
        slider.setValue(setting.getBaseVol()); //mettre a jour le slider lorsqu'on clique sur le bouton mute

        labelMode.setText("Mode : "+mode());                                  //affichage des valeurs
        labelWidth.setText("Width : "+setting.getWidth());
        labelHeight.setText("Height : "+setting.getHeight());
        labelVol.setText("Volume : "+(int)(100.0*setting.getBaseVol())+"%");

        stage.act(delta);
        stage.draw();

        if(Gdx.input.isKeyPressed(Keys.M)) { //si on appuie sur M, les parametres sont sauvegardes et on retourne au menu principal
            setting.writeSetting();
            MainGame.vol.setBase(setting.getBaseVol());
			this.dispose();
			game.setScreen(new ScreenMainMenu(game));
        }
    }

    @Override
    public void show(){
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        if (stage != null) {
            stage.dispose();
        }
    }
}