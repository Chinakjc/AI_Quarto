package Game.oneVsOne;

import Database.Backup;
import Database.Data;
import Database.Setting;
import Game.main.*;
import MainMenu.ScreenMainMenu;
import MenuNewGame.ScreenMenuNewGame;

import Setting.ScreenSetting;
import Tools.BaseConversion;
import WidgetPackage.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.*;

/**
 * Cette classe sert a l'affichage d'une partie en un contre un
 */
public class GameScreen1v1 implements Screen {
    private MainGame game;
    private Stage stage;
    private PieceActor[] pieceActor;
    private BoardActor plateauActor;
    private BackGroundActor bg;
    private Piece[] piece;
    private LogoActorMainGame logo;
    private SizeMainGame taille;
    private int[][] indice_piece_sur_case_de_plateau;
    private Data data;
    private int indice_joueur;
    private BitmapFont font;
    private Label label;
    private Label.LabelStyle style;
    private int declencheur = 0;
    private Sound SONset = Gdx.audio.newSound(Gdx.files.internal("sounds/setPiece.wav"));
    private Sound SONpick = Gdx.audio.newSound(Gdx.files.internal("sounds/pickPiece.wav"));
    private Sound SONend = Gdx.audio.newSound(Gdx.files.internal("sounds/end.wav"));

    private Button newGame;
    private Button backUP;
    private Button quit;
    private Button settings;

    private Backup oldBackup;

    private float vol1;
    private float vol2;
    private float vol3;

    protected int compter;


    public GameScreen1v1(MainGame game){
        this.game = game;
        this.data = ScreenMenuNewGame.data_1v1;
        taille = new SizeMainGame(MainGame.width_current,MainGame.height_current);

        stage = new Stage(new StretchViewport(MainGame.width_current,MainGame.height_current));
        bg = new BackGroundActor("bg0.jpg");
        stage.addActor(bg);

        logo= new LogoActorMainGame();
        stage.addActor(logo);

        plateauActor = new BoardActor();
        pieceActor = new PieceActor[16];

        piece= data.piece;

        pieceActor = new PieceActor[16];
        plateauActor = new BoardActor();
        stage.addActor(plateauActor);
        for(int i=0; i<16; i++){
            pieceActor[i] = new PieceActor(piece[i]);
            stage.addActor(pieceActor[i]);
        }

        this.indice_piece_sur_case_de_plateau =data.indice_piece_sur_case_de_plateau;
        this.indice_joueur = data.compteur % 2; //indice_joueur est soit 0 soit 1;

        this.font = new BitmapFont(Gdx.files.internal("font/TimesNewRoman.fnt"));
        this.style = new Label.LabelStyle();
        this.style.font=this.font;
        this.style.fontColor= Color.BLACK;

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

        backUP = new Button(MainGame.width_current,MainGame.height_current
                ,taille.width
                ,percent
                ,percent
                ,(int)(0.1*percent*taille.width)
                ,(int)(0.1*percent*taille.height)
                ,"backupW.png");
        stage.addActor(backUP);
        backUP.setVisible(true);

        newGame = new Button(MainGame.width_current,MainGame.height_current
                ,taille.width
                ,percent
                ,percent
                ,MainGame.width_current-(int)(2.5*percent*taille.width)
                ,(int)(0.1*percent*taille.height)
                ,"newgameW.png");
        stage.addActor(newGame);
        newGame.setVisible(true);

        settings = new  Button(MainGame.width_current,MainGame.height_current
                ,taille.width
                ,percent
                ,percent
                ,MainGame.width_current - (int)(percent*taille.width)
                ,MainGame.height_current - (int)(percent*taille.height) - (int)(0.1*percent*taille.height)
                ,"setting.png");
        stage.addActor(settings);
        settings.setVisible(true);


        this.label = new Label("", this.style);
        this.label.setFontScale(this.taille.parametre_taille_label);
        this.label.setPosition(this.taille.position_x_label, this.taille.position_y_label);
        this.stage.addActor(this.label);

        oldBackup = data.toBackup();

        Setting setting = new Setting();
        setting.readSetting();
        compter = setting.getFps()/2;



    }

    /**
     * Cette methode permet de recuperer les indices des pieces par sa position donnee
     * @param l
     * @param c
     * @return
     * @see BaseConversion#decimalToBinary(int)
     */
    private int getIndexPieceFreeWithLineColumn(int l, int c){
        int x,y;
        int a1,a2,a3,a4;
        x = BaseConversion.decimalToBinary(l);
        y = BaseConversion.decimalToBinary(c);
        a1=x/10;
        a2=x%10;
        a3=y/10;
        a4=y%10;
        return (1-a3)*8+(1-a1)*4+(1-a2)*2+a4;
    }

    /**
     * Cette methode sert a afficher tous les elements d'une partie en un contre un et à jouer tous les sons nécessaires
     * @param delta
     * @see Backup
     * @see Backup#Backup()
     * @see Backup#equals(Object)
     * @see Backup#writeData(String)
     * @see Data#getGameStatus()
     * @see Data#toBackup()
     * @see Data#liste_joueur
     * @see Button#setColor(Color)
     * @see Button#isClicked(int, int)
     * @see MainGame#height_current
     * @see MainGame#width_current
     * @see Piece
     * @see PieceActor#select()
     * @see PieceActor#mettre_au_plateau(int, int)
     * @see SizeMainGame
     * @see Tools.Vol#vols
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1); //initialisation de la fenetre avec un fond blanc avant l'ajout du background
        compter--; //buffer avant de pouvoir interagir avec la fenetre

        if(data.getGameStatus()==0){ //initialisation des couleurs des boutons sauvegarde/reload/quitter/options
            quit.setColor(Color.BLACK);
            newGame.setColor(Color.BLACK);

            if(oldBackup.equals(data.toBackup())){ //Si donnees de sauvegarde correspondent a statut actuel de partie
                backUP.setColor(Color.OLIVE); // bouton sauvegarde en vert
            }
            else {
                backUP.setColor(Color.BLACK); //sinon noir
            }
        }
        else{    //Si la partie est finie, boutons reload/quitter couleur saumons, sauvegarde couleur grise
            quit.setColor(Color.SALMON);
            backUP.setColor(Color.GRAY);
            newGame.setColor(Color.SALMON);
        }
        stage.act(delta);
        stage.draw();
        if ((MainGame.height_current != Gdx.graphics.getHeight()) || (MainGame.width_current != Gdx.graphics.getWidth())) {
            MainGame.width_current = Gdx.graphics.getWidth();
            MainGame.height_current = Gdx.graphics.getHeight();
            this.game.setScreen(new GameScreen1v1(this.game)); //adapter resolution
        }
        int x = Gdx.input.getX();
        int y = Gdx.input.getY();
        y = MainGame.height_current - y;

        if (data.getGameStatus() == 0) {
            if (Gdx.input.isKeyPressed(Keys.M)) { //si partie pas finie, appuyer sur M = sauvegarde + retour menu principal
                this.dispose();
                data.toBackup().writeData("2"); //"2" pour 1v1 et "1" pour vsAI
                game.setScreen(new ScreenMainMenu(game));
            }
            if(Gdx.input.isKeyPressed(Keys.P)) { //si partie pas finie, appuyer sur P = sauvegarde
                data.toBackup().writeData("2");
                oldBackup = data.toBackup(); //on remplace l'ancienne sauvegarde par la nouvelle
            }
            if((Gdx.input.isTouched())&&(backUP.isClicked(x,y))&&(compter<0)){ //si partie pas finie, cliquer sur bouton de sauvegarde = sauvegarde
                data.toBackup().writeData("2");
                oldBackup = data.toBackup();
            }
            int k = 0; // k=1 : piece selectionnee ; k=0 : pas de piece selectionnee
            int i = -1;
            while (k == 0 && i < 15) {
                i++;
                if ((piece[i].est_disponible == 1) && (piece[i].est_selectionnee == 1)) k = 1;
            }

            if (k == 0) {
                label.setText(data.liste_joueur[this.indice_joueur] + ", veuillez choisir une piece pour " + data.liste_joueur[(this.indice_joueur + 1) % 2] + " !");
                if ((x > taille.position_x_initial_piece) && (x < taille.position_x_initial_piece + taille.width) &&
                        (y > taille.position_y_initial_piece) && (y < taille.position_y_initial_piece + taille.height) &&
                        (Gdx.input.isTouched())&&(compter<0)) {
                    //une fois la piece selectionnee
                    int l;
                    int c;
                    int r = taille.width / 4;
                    c = (x - taille.position_x_initial_piece) / r;
                    l = (y - taille.position_y_initial_piece) / r;
                    if (pieceActor[this.getIndexPieceFreeWithLineColumn(l, c)].getPiece().est_disponible == 1) {
                        //une fois la piece selectionnee et pas encore posee
                        pieceActor[this.getIndexPieceFreeWithLineColumn(l, c)].select();
                        data.compteur++;
                        this.indice_joueur = data.compteur % 2;
                        vol1 = MainGame.vol.vols[2];
                        SONpick.play(vol1);
                    }
                }
            }
            if (k == 1) {
                label.setText(data.liste_joueur[this.indice_joueur] + ", veuillez placer la piece choisie sur le plateau !");
                if ((x > taille.position_x_initial_plateau) && (x < taille.position_x_initial_plateau + taille.width) &&
                        (y > taille.position_y_initial_plateau) && (y < taille.position_y_initial_plateau + taille.height) &&
                        (Gdx.input.isTouched())&&(compter<0)) {
                    //une fois avoir clique sur une case du plateau
                    int l;
                    int c;
                    int r = taille.width / 4;
                    c = (x - taille.position_x_initial_plateau) / r;
                    l = (y - taille.position_y_initial_plateau) / r;
                    if (this.indice_piece_sur_case_de_plateau[l][c] == -1) { //-1 = case disponible
                        pieceActor[i].mettre_au_plateau(l, c);
                        this.indice_piece_sur_case_de_plateau[l][c] = i; //tres important
                        vol2 = MainGame.vol.vols[3];
                        SONset.play(vol2);
                    }
                }
            }
        }
        else{
            if (Gdx.input.isKeyPressed(Keys.M)) { //jeu termine, donc sauvegarde impossible, on retourne seulement au menu principal
                this.dispose();
                game.setScreen(new ScreenMainMenu(game));
            }
        }
        if (data.getGameStatus() == 1) { // la partie est finie, l'un des joueurs a gagne
            label.setText(data.liste_joueur[this.indice_joueur] + " remporte la partie !");
            if (declencheur == 0) {
                declencheur++;
                vol3 = MainGame.vol.vols[4];
                SONend.play(vol3);
            }

        }
        if (data.getGameStatus() == -1) { //la partie se termine sur un match nul
            label.setText("Match nul !");
            if (declencheur == 0) {
                declencheur++;
                vol3 = MainGame.vol.vols[4];
                SONend.play(vol3);
            }
        }

        if (Gdx.input.isKeyPressed(Keys.Q)) { //on peut quitter avec la touche Q pour retourner au menu principal
            this.dispose();
            game.setScreen(new ScreenMainMenu(game));
        }
        if((Gdx.input.isTouched())&&(quit.isClicked(x,y))&&(compter<0)){ //ou cliquer sur le bouton de la porte
            this.dispose();
            game.setScreen(new ScreenMainMenu(game));
        }

        if(Gdx.input.isKeyPressed(Keys.N)){ //La touche N permet de recommencer une partie et fait revenir sur le menu de Nouvelle Partie
            data = new Data(0);
            data.toBackup().writeData("2");
            game.setScreen(new ScreenMenuNewGame(game));
        }
        if((Gdx.input.isTouched())&&(newGame.isClicked(x,y))&&(compter<0)){ //ou en cliquant sur le bouton du triangle et de la fleche circulaire
            data = new Data(0);
            data.toBackup().writeData("2");
            game.setScreen(new ScreenMenuNewGame(game));
        }

        if((Gdx.input.isTouched())&&(settings.isClicked(x,y))&&(compter<0)){ //On peut toujours aller dans les options en cliquant sur l'engrenage
            this.dispose();
            game.setScreen(new ScreenSetting(game));
            if(data.getGameStatus() == 0) {
            data.toBackup().writeData("2");
            }
        }

    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause(){
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
