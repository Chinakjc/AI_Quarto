package MenuNewGame;

import Database.Backup;
import Database.Data;
import Database.Setting;
import Game.oneVsOne.GameScreen1v1;
import Game.oneVsAI.GameScreenVsAI;
import MainMenu.ScreenMainMenu;
import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.*;

/**
 * Cette classe permet d'afficher la page Nouvelle Partie et la rendre fonctionnelle
 */
public class ScreenMenuNewGame implements Screen {
    protected MainGame game;
    protected BackGroundActor bg;
    protected NewGameActor newgameActor;
    protected Stage stage;
    public static Data data_1v1;
    public static Data data_vs_ordinateur;
    public int r;

    private float volume;
    
    protected Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));

    protected int compter;



    public ScreenMenuNewGame(MainGame game) {
        this.game = game;
        data_1v1 = new Data(0);
        Backup backup1 = new Backup();
        if(!backup1.readData("2")) {
            new Data(0).toBackup().writeData("2");
            backup1.readData("2");
        }
        data_1v1 = backup1.toData();

        if(!backup1.readData("1")) {
            new Data(0).toBackup().writeData("1");
            backup1.readData("1");
        }
        data_vs_ordinateur = backup1.toData();

        stage = new Stage(new StretchViewport(MainGame.width_current, MainGame.height_current));
        
        bg = new  BackGroundActor("pageNP.jpg");
        stage.addActor(bg);
        newgameActor = new NewGameActor();
        stage.addActor(newgameActor);

        Setting setting = new Setting();
        setting.readSetting();
        compter = setting.getFps()/2;
        
    }

    /**
     * Cette methode sert a rendre interactifs et fonctionnels les boutons sur la page du menu Nouvelle Partie avec le son adapte
     * @param delta
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see Tools.Vol#vols
     * @see NewGameActor#initialiser()
     * @see NewGameActor#select(int)
     * @see Data
     */
    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,1);
        compter--;

        if((MainGame.height_current!=Gdx.graphics.getHeight())||(MainGame.width_current!=Gdx.graphics.getWidth())){
            MainGame.width_current=Gdx.graphics.getWidth();

            MainGame.height_current=Gdx.graphics.getHeight();
            this.game.setScreen(new ScreenMenuNewGame(this.game));
        }

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        y = MainGame.height_current - y;
        r = Math.min(MainGame.width_current,MainGame.height_current);
        r = r/8;
        newgameActor.initialiser();

        if((y>MainGame.height_current/2)&&(y<MainGame.height_current/2+ newgameActor.r)){
            if((x> MainGame.width_current/4 )&&((x<MainGame.width_current/4 +2*r))&&(compter<0)) {
                newgameActor.select(1);
                if(Gdx.input.isTouched()){
                    volume = MainGame.vol.vols[0];
                    sound.play(volume);
                    ScreenMenuNewGame.data_1v1 = new Data(0);
                    this.game.setScreen(new GameScreen1v1(this.game));
                }
            }
            if((x> (MainGame.width_current - (MainGame.width_current/4))-2*r)&&(x <(MainGame.width_current - (MainGame.width_current/4)))&&(compter<0)) {
                newgameActor.select(2);
                if(Gdx.input.isTouched()){
                    volume = MainGame.vol.vols[0];
                    sound.play(volume);
                    ScreenMenuNewGame.data_vs_ordinateur = new Data(1);
                    this.game.setScreen(new GameScreenVsAI(this.game));
                }
            }
        }
        
        if((y>MainGame.height_current/6)&&(y<MainGame.height_current/6+ newgameActor.r)){
        	if ( ( x> (MainGame.width_current/2)-3*r)  &&(x< (MainGame.width_current/2)+3*r) &&  (compter<0)) {
        	    newgameActor.select(3);
        	    if(Gdx.input.isTouched() ){
                    volume = MainGame.vol.vols[0];
                    sound.play(volume); this.game.setScreen(new ScreenMainMenu(this.game));
                }
            }
        }   
        
        if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            this.dispose();
            Gdx.app.exit();
        }
        
        if((Gdx.input.isKeyPressed(Keys.M))&&(compter<0)) {
			this.dispose();
			game.setScreen(new ScreenMainMenu(game));
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
