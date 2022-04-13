package MainMenu;

import Database.Setting;
import MenuLoadGame.ScreenLoadGame;
import MenuNewGame.ScreenMenuNewGame;
import Rules.ScreenRules;
import Setting.ScreenSetting;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.*;

/**
 * Cette classe sert a rendre le menu principal fonctionnel
 */
public class ScreenMainMenu implements Screen {
    private MainGame game;
    private Stage stage;
    private ButtonActorMainMenu[] boutonActor;
    private LogoActorMainMenu logo;
    private BackGroundActor bg;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));

    private float volume;
    protected int compter;

    public ScreenMainMenu(MainGame game) {
        this.game = game;

        stage = new Stage(new StretchViewport(MainGame.width_current, MainGame.height_current));
        bg = new BackGroundActor();
        stage.addActor(bg);

        logo = new LogoActorMainMenu();
        stage.addActor(logo);

        boutonActor = new ButtonActorMainMenu[5];
        for (int i = 0; i < 5; i++) {
            boutonActor[i] = new ButtonActorMainMenu(new ButtonMainMenu(i,0));
            stage.addActor(boutonActor[i]);
        }
        Setting setting = new Setting();
        setting.readSetting();
        compter = setting.getFps()/2;
    }

    /**
     * Cette methode sert a afficher les textures de maniere interactives (modification de la texture lorsque le curseur survole
     * le bouton et une fois clique redirige sur la fenetre voulue)
     * @param delta
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see SizeMainMenu
     * @see ButtonMainMenu
     * @see ButtonActorMainMenu#getBouton()
     * @see Tools.Vol#vols
     */
    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,1);
        compter--;

        if((MainGame.height_current!=Gdx.graphics.getHeight())||(MainGame.width_current!=Gdx.graphics.getWidth())){
            MainGame.width_current=Gdx.graphics.getWidth();

            MainGame.height_current=Gdx.graphics.getHeight();
            this.game.setScreen(new ScreenMainMenu(this.game));
        }

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        y = MainGame.height_current -y;
        SizeMainMenu taille = new SizeMainMenu(MainGame.width_current,MainGame.height_current);
        int width_boutons = taille.width_boutons;
        int height_boutons = taille.height_boutons;


        for(int i=0; i<5; i++){
            ButtonMainMenu bt = boutonActor[i].getBouton();

            if((x>bt.position()[0])&&(x<(bt.position()[0]+width_boutons))&&
                    (y>bt.position()[1])&&(y<(bt.position()[1]+height_boutons))){
                boutonActor[i].select();
            }
            else boutonActor[i].pas_select();
        }

        if((boutonActor[0].getBouton().est_selectionne==1)&&(Gdx.input.isTouched())&&(compter<0)){
            volume = MainGame.vol.vols[0];
            sound.play(volume);
            this.game.setScreen(new ScreenMenuNewGame(this.game));
        }

        if((boutonActor[1].getBouton().est_selectionne==1)&&(Gdx.input.isTouched())&&(compter<0)){
            volume = MainGame.vol.vols[0];
            sound.play(volume);
            this.game.setScreen(new ScreenLoadGame(this.game));
        }

        if((boutonActor[2].getBouton().est_selectionne==1)&&(Gdx.input.isTouched())&&(compter<0)){
            volume = MainGame.vol.vols[0];
            sound.play(volume);
            this.game.setScreen(new ScreenSetting(game));
        }

        if((boutonActor[3].getBouton().est_selectionne==1)&&(Gdx.input.isTouched())&&(compter<0)){
            volume = MainGame.vol.vols[0];
        	sound.play(volume);
            this.game.setScreen(new ScreenRules(this.game));
           }

        if((boutonActor[4].getBouton().est_selectionne==1)&&(Gdx.input.isTouched())&&(compter<0)){
            volume = MainGame.vol.vols[0];
        	sound.play(volume);
        	Gdx.app.exit();
           }
        
        if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			this.dispose();
			Gdx.app.exit();
        }

        stage.act(delta);
        stage.draw();
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
