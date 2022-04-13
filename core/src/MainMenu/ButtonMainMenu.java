package MainMenu;

import com.mygdx.game.MainGame;

/**
 * Cette classe sert à la definition des boutons et au calcul de leur positions
 */
public class ButtonMainMenu {

    private int fonction; //0: "NOUVELLE PARTIE" 1: "CHARGER PARTIE" 2: "OPTIONS" 3: "REGLES" 4: "QUITTER"
    public int est_selectionne; //0: non 1: oui

    public ButtonMainMenu(int fonction, int select){
        this.fonction = fonction;
        this.est_selectionne = select;
    }

    /**
     * Cette methode indique les indices afin de recuperer le bouton adequat (voir assets : bouton0.png, bouton1.png,...)
     */
    public int get_Indice_Texture(){
        return this.fonction+5*this.est_selectionne;
    }

    /**
     * Cette methode sert a definir la position des boutons sur l'ecran en fonction de la taille de la fenetre
     * @return
     * @see MainGame#width_current
     * @see MainGame#height_current
     * @see SizeMainMenu
     * */
    public int[] position(){
        SizeMainMenu taille = new SizeMainMenu(MainGame.width_current,MainGame.height_current);
        int[] pos = new int[2]; //pos[0]=x pos[1]=y
        pos[0] = taille.position_x_initial_boutons;
        int i = 4-fonction;
        pos[1] = taille.position_y_initial_boutons + i* taille.unite + i*taille.height_boutons;
        return pos;
    }
    public void select(){
        this.est_selectionne = 1;
    }

    public void pas_select(){
        this.est_selectionne = 0;
    }

}
