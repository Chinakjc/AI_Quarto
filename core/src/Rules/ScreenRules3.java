package Rules;

import Database.Setting;
import WidgetPackage.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.BackGroundActor;
import com.mygdx.game.MainGame;

import Game.main.SizeMainGame;
import MainMenu.ScreenMainMenu;
/**
 * Cette classe a comme objectif l'affichage de la page 3 des regles.
 */
public class ScreenRules3 implements Screen{

	
	protected MainGame game;
	protected Stage stage;
	protected BackGroundActor bg;
	protected SizeMainGame taille;
	protected Sound SONtransition = Gdx.audio.newSound(Gdx.files.internal("sounds/transition.wav"));

	private float volume;

	protected Button flecheLeft;
	protected Button flecheRight;
	protected Button quit;

	private int compter;

	public ScreenRules3(MainGame game) {
		
		this.game = game;
	    taille = new SizeMainGame(MainGame.width_current,MainGame.height_current);
	    stage = new Stage(new StretchViewport(MainGame.width_current, MainGame.height_current));
	    
	    bg = new BackGroundActor("page3.jpg");
        stage.addActor(bg);

		double percent = 0.1;
		quit = new Button(MainGame.width_current,MainGame.height_current
				,taille.width
				,percent
				,percent
				,MainGame.width_current-(int)(percent*taille.width) - (int)(0.1*percent*taille.width)
				,(int)(0.1*percent*taille.height)
				,"quitW.png");
		stage.addActor(quit);
		quit.setVisible(true);
		quit.setColor(Color.BLACK);

		flecheLeft = new Button(MainGame.width_current,MainGame.height_current
				,taille.width
				,percent
				,percent
				, (int)((MainGame.width_current- 2.35*percent*taille.width)/2)
				,(int)(0.1*percent*taille.width)
				,"left.png");
		stage.addActor(flecheLeft);
		flecheLeft.setVisible(true);

		flecheRight = new Button(MainGame.width_current,MainGame.height_current
				,taille.width
				,percent
				,percent
				,(int)((MainGame.width_current+ 0.35*percent*taille.width)/2)
				,(int)(0.1*percent*taille.width)
				,"right.png");
		stage.addActor(flecheRight);
		flecheRight.setVisible(false);

		Setting setting = new Setting();
		setting.readSetting();
		compter = setting.getFps()/2;
	}	

	@Override
	public void show() {
	}

	/**
	 * Cette methode permet d'afficher la troisieme et derniere page des regles ainsi que les boutons en les rendant utilisables
	 * @param delta
	 * @see MainGame#height_current
	 * @see ScreenRules2
	 * @see Tools.Vol#vols
	 * @see ScreenMainMenu
	 */
	@Override
	public void render(float delta) {
		ScreenUtils.clear(1,1,1,1);
		compter--;
	     
	     if(Gdx.input.isKeyPressed(Keys.M)) {
	     	this.dispose();
	     	game.setScreen(new ScreenMainMenu(game));
	     }
		int x = Gdx.input.getX();
		int y = MainGame.height_current - Gdx.input.getY();

		if((((Gdx.input.isTouched())&&(flecheLeft.isClicked(x,y)))
				||(Gdx.input.isKeyPressed(Keys.LEFT)))
				&&(compter<0)) {
	     	this.dispose();
	     	game.setScreen(new ScreenRules2(game));
	     	volume = MainGame.vol.vols[1];
	     	SONtransition.play(volume);
		}
	    
		if((Gdx.input.isTouched())&&(quit.isClicked(x,y))&&(compter<0)){
			this.dispose();
			game.setScreen(new ScreenMainMenu(game));
		}
	     stage.act(delta);
	     stage.draw();
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
	}
}