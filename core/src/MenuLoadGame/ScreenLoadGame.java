package MenuLoadGame;

import Game.oneVsAI.GameScreenVsAI;
import Game.oneVsOne.GameScreen1v1;
import MainMenu.ScreenMainMenu;
import MenuNewGame.NewGameActor;
import MenuNewGame.ScreenMenuNewGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.BackGroundActor;
import com.mygdx.game.MainGame;

/**
 * Cette classe permet d'afficher la page Charger Partie et la rendre fonctionnelle
 * @see ScreenMenuNewGame
 */
public class ScreenLoadGame extends ScreenMenuNewGame {

    private float volume;

    public ScreenLoadGame(MainGame game){
    	
        super(game);
        bg = new  BackGroundActor("pageCP.jpg");    
        stage.addActor(bg);
        
        newgameActor = new NewGameActor();
        stage.addActor(newgameActor);
    }

    /**
     * Cette methode sert a rendre interactifs et fonctionnels les boutons sur la page du menu Charger Partie avec le son adapte
     * @param delta
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see Tools.Vol#vols
     * @see NewGameActor#initialiser()
     * @see NewGameActor#select(int)
     */
    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,1);
        compter--;

        if((MainGame.height_current!= Gdx.graphics.getHeight())||(MainGame.width_current!=Gdx.graphics.getWidth())){
            MainGame.width_current=Gdx.graphics.getWidth();

            MainGame.height_current=Gdx.graphics.getHeight();
            this.game.setScreen(new ScreenMenuNewGame(this.game));
        }

        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        
        y = MainGame.height_current -y;
        
        r = Math.min(MainGame.width_current,MainGame.height_current);
        r = r/8;

        newgameActor.initialiser();

        if((y>MainGame.height_current/2)&&(y<MainGame.height_current/2+ newgameActor.r)){
            if((x> MainGame.width_current/4 )&&((x<MainGame.width_current/4 +2*r))&&(compter<0)) {
                newgameActor.select(1);
                if(Gdx.input.isTouched()){
                    volume = MainGame.vol.vols[0];
                    sound.play(volume);                 
                    this.game.setScreen(new GameScreen1v1(this.game));
                }
            }
            
            if((x>(MainGame.width_current - (MainGame.width_current/4))-2*r)&&(x <(MainGame.width_current - (MainGame.width_current/4)))&&(compter<0)) {
                newgameActor.select(2);
                if(Gdx.input.isTouched()){
                    volume = MainGame.vol.vols[0];
                    sound.play(volume);                    
                    this.game.setScreen(new GameScreenVsAI(this.game));
                }
            }
        }
        
        if((y>MainGame.height_current/6)&&(y<MainGame.height_current/6+ newgameActor.r)){
        	if ( ( x> (MainGame.width_current/2)-3*r)  &&(x< (MainGame.width_current/2)+3*r) &&  (compter<0)) {
        	    newgameActor.select(3);
        	    if(Gdx.input.isTouched() ){
                    volume = MainGame.vol.vols[0];
                    sound.play(volume); 
                    this.game.setScreen(new ScreenMainMenu(this.game));
        	    }
        	}
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            Gdx.app.exit();
        }

        if((Gdx.input.isKeyPressed(Input.Keys.M))&&(compter<0)) {
            this.dispose();
            game.setScreen(new ScreenMainMenu(game));
        }
        stage.act(delta);
        stage.draw();
    }
}
