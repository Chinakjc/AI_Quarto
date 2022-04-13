package com.mygdx.game;

import Database.Setting;

import Tools.Vol;
import com.badlogic.gdx.Game;

/**
 * La classe sert a creer l'ecran de jeu
 */
public class MainGame extends Game {
	public static int width_current ;
	public static int height_current;
	public static Vol vol = new Vol();

	/**
	 * Cette methode sert a lire le fichier config, et a initialiser la variable vol pour le volume des sons, et lancer Welcome
	 * @see	Setting
	 * @see Setting#readSetting()
	 * @see Setting#getBaseVol()
	 * @see Tools.Vol#setBase(float)
	 * @see Welcome
	 */
	@Override
	public void create(){
		Setting setting = new Setting();
		setting.readSetting();
		vol.setBase(setting.getBaseVol());
		setScreen(new Welcome(this));
	}

	@Override
	public void render(){
		super.render();
	}

	@Override
	public void resize(int width,int height){
	}
}






