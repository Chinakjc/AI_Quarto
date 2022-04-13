package com.mygdx.game;

import MainMenu.ScreenMainMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Cette classe a comme objectif de lancer un effet sonore lorsqu'on demarre le jeu grace a l'interface Sound de "gdx.audio"
 * et de lancer l'affichage du Menu Principal.
 */
public class Welcome implements Screen {
    private MainGame game;
    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/Windows95.wav"));

    public Welcome(MainGame game){
        this.game = game;
    }

    /**
     * Lance un écran noir au début du jeu, joue le son du lancement du jeu, et adapte l'ecran de jeu
     * @param delta
     * @see ScreenMainMenu
     * @see Tools.Vol#base
     */
    @Override
    public void render(float delta){
        ScreenUtils.clear(0,0,0,1); //on lance durant le chargement du jeu un écran noir
        sound.play(0.55f*MainGame.vol.base);
        this.dispose();
        game.setScreen(new ScreenMainMenu(game));
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
    }
}
