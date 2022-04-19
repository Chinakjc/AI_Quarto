package com.mygdx.game.desktop;

import Database.Setting;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;


/**
 * Cette classe est le Launcher de notre jeu, ainsi c'est dans cette classe que nous allons indiquer les valeurs des caracteristiques du jeu.
 *
 */
public class DesktopLauncher {
	/**
	 * Cette methode lit le fichier config s'il existe afin de recuperer les parametres, sinon il va le creer avec les parametres par defaut.
	 * Puis il definit le nom de la fenetre, les FPS (images par secondes), la taille de la fenetre, le mode d'affichage (plein ecran / fenetre) et enfin l'icone du jeu.
	 * Il s'agit du main de notre programme
	 * @param arg
	 * @see Setting
	 * @see Setting#readSetting()
	 * @see Setting#writeSetting()
	 * @see Setting#getHeight()
	 * @see Setting#getWidth()
	 * @see Setting#getIsFullscreen()
	 * @see Setting#getFps()
	 */
	public static void main (String[] arg) {

		Setting setting = new Setting();
		setting.readSetting(); //readSetting() est interprete uniquement si le fichier config existe
		setting.writeSetting();
		int width = setting.getWidth();
		int height = setting.getHeight();
		boolean isFullscreen = setting.getIsFullscreen();
		int fps = setting.getFps();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Quarto V2.0";
		config.foregroundFPS = fps;
		config.width = width;
		config.height = height;
		config.resizable=false;
		config.fullscreen = isFullscreen;
		config.addIcon("icon.png", Files.FileType.Internal);

		new LwjglApplication(new MainGame(), config);
	}
}
