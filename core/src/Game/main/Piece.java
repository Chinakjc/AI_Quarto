package Game.main;

import com.mygdx.game.MainGame;

/**
 * La classe piece sert a definir les 16 pieces qui composent le jeu de Quarto
 */
public class Piece {
    public int est_blanche; // 1: blanche; 0: marron
    public int est_ronde; // 1: ronde; 0: carree
    public int est_grande; // 1: grande; 0: petite
    public int est_pleine; // 1: pleine; 0: creuse
    public int est_disponible; // 1: disponible; 0: deja mise au plateau
    public int est_selectionnee; //1: selectionnee; 0: pas selectionnee

    public int ligne;
    public int colonne;

    private float[] parametre_couleur = new float[4]; //batch.setcolor(r,g,b,a)      un parametre par couleur

    public Piece(int blanche, int ronde, int grande, int pleine, int dispo, int select){
        this.est_blanche = blanche;
        this.est_ronde = ronde;
        this.est_grande = grande;
        this.est_pleine = pleine;
        this.est_disponible = dispo;
        this.est_selectionnee = select;


        //pour afficher les pieces plus joli initialement (ce n'est pas la position sur le plateau) !
        this.ligne = 2*(1-ronde) + (1-grande);
        this.colonne = 2*(1-blanche) + pleine;
        /*
        blanche ronde grande pleine
            0-1     0-1   0-1    0-1
        1 piece : 4bit de chiffre binaire

        Ligne colonne
          0-3   0-3

        Ligne est un chiffre de 1bit en base 4
        Colonne est un chiffre de 1bit en base 4

        X = abcd  0b
        ab et cd
        ab -> ligne
        Ligne = a * 2 + b         (base 4)
        Colonne = c * 2 + d       (base 4)


    Exemple :

    Blanche ronde grande pleine --> 1111 serait placee en (3, 3)

    Alors on a
    A = 1 - ronde
    B = 1 - grande
    C = 1 - blanche
    D = pleine
    Avec abcd  pour la meme piece --> 0001 serait placee en (0, 1) ---> plus joli visuellement a l'etat initial
         */
    }

    public Piece(Piece piece){
        this(piece.est_blanche,piece.est_ronde,piece.est_grande,piece.est_pleine,piece.est_disponible,piece.est_selectionnee);
    }

    public float[] getColorSettings(){ //créer les couleurs des pieces
        if(est_blanche == 1) { //si la piece est blanche
            for (int i = 0; i < 4; i++) { //i allant de 0 a 3 car r,g,b,a
                parametre_couleur[i] = 1; //on change pas la couleur, on laisse blanc
            }
        }
        else{
            // r:g:b = 145:51:34  ; a=1 ---> couleur des pieces marrons
            float r = 1.0f/(145.0f+51.0f+34.0f);
            parametre_couleur[0] = r*145.0f; //r
            parametre_couleur[1] = r*51.0f;  //g
            parametre_couleur[2] = r*34.0f;  //b
            parametre_couleur[3] = 1.0f; //a
        }
        return  parametre_couleur;
    }

    public float getSizeSettings(){ //mise a l'echelle des pieces
        if(est_grande == 1) return 0.9f; //les grandes font 90% de la taille d'une case du plateau
        return 0.5f;// les petites font 50% de la taille d'une case du plateau
    }

    public int getIndexTexture(){ //en rapport avec les noms des fichiers des pièces dans "assets"
        if(est_ronde == 1){
            if(est_pleine == 1) return 0; //"0.png" est le rond plein
            return 1; //"1.png" est le rond creux
        }
        if(est_pleine == 1) return 2; //"2.png" est le carré plein
        return 3; //"3.png" est le carré creux
    }

    public void setOnBoard(int l, int c){
        this.est_disponible=0; //la piece devient indisponible
        this.ligne = l; //car placee a la ligne l
        this.colonne = c; // et a la colonne c
        this.est_selectionnee = 0; //la piece est deselectionnee (le carre blanc de l'input disparait)
    }

    public void select(){
        this.est_selectionnee = 1;
    } // on selectionne la piece

    /**
     * Cette methode sert a calculer la position d'une piece que ce soit sur le plateau ou non
     * @return
     * @see MainGame#width_current
     * @see MainGame#height_current
     * @see SizeMainGame
     * @see Piece#getSizeSettings()
     */
    public int[] position(){
        int[] pos = new int[2]; //pos[0]: position_x; pos[1]: position_y
        SizeMainGame tailleMainGame = new SizeMainGame(MainGame.width_current,MainGame.height_current);
        // on cree un nouvel objet: un ecran de jeu de longueur width_current et de hauteur height_current
        int r = tailleMainGame.width/4;
        float parametre_taille = this.getSizeSettings();
        int taille_piece = (int)((float)(r*parametre_taille));

        if(est_disponible == 0){
            int x = tailleMainGame.position_x_initial_plateau;
            int y = tailleMainGame.position_y_initial_plateau;
            pos[0] = x + (r-taille_piece)/2 + this.colonne*r;
            pos[1] = y +(r-taille_piece)/2 + this.ligne*r;
        }
        else{
            int x = tailleMainGame.position_x_initial_piece;
            int y = tailleMainGame.position_y_initial_piece;
            pos[0] = x  + (r-taille_piece)/2 + this.colonne*r;
            pos[1] = y  + (r-taille_piece)/2 + this.ligne*r;
        }
        return pos;
    }

    /**
     * Cette methode sert a definir la position du carre blanc une fois la piece selectionnee
     * @return
     * @see MainGame#width_current
     * @see MainGame#height_current
     * @see SizeMainGame
     */
    public int[] positionSquare(){

        int[] pos = new int[2];
        SizeMainGame tailleMainGame = new SizeMainGame(MainGame.width_current,MainGame.height_current);
        int r = tailleMainGame.width/4;
        int x=tailleMainGame.position_x_initial_piece;
        int y=tailleMainGame.position_y_initial_piece;
        pos[0]= x+this.colonne*r;
        pos[1]= y+this.ligne*r;
        return pos;
    }
}