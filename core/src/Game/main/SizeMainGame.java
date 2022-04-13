package Game.main;

/**
 * La classe sert a adapter l'ecran de jeu a toutes les resolutions possibles
 */
public class SizeMainGame {

    public int width;
    public int height;
    public int position_x_initial_plateau;
    public int position_y_initial_plateau;
    public int position_x_initial_piece;
    public int position_y_initial_piece;
    public int position_x_logo;
    public int position_y_logo;
    public int position_x_label;
    public int position_y_label;
    public float parametre_taille_label;


    public SizeMainGame(int w, int h){
        int a;
        int b;
        int r;

        //max et min pour adapter les ecrans horizontaux (w>h) et verticaux (w<h)
        //a calcule la longueur de l'écran, b calcule la hauteur de l'écran
        a = Math.max(w,h)/(1+4+1+4+1)*4; // 1+4+1+4+1 = "la marge entre le bord de fenetre gauche et les pieces" + "le tableau des pieces initiales" + "l'espace entre le tableau de pieces initiales et le plateau" + " le plateau" + la marge entre le plateau et la bord de la fenetre"
        b = Math.min(w,h)/(1+4+1)*4; // 1+4+1 = "la marge entre le haut de la fenetre et les plateaux" + "hauteur des plateaux" + "la marge entre le bas de fenetre et les plateaux"

        r = Math.min(a,b); //r = longueur d'un cote du plateau de jeu

        this.width = r; // taille du plateau = r
        this.height = this.width; // car plateau carre

        r = r/4; //r = longueur d'une unite de mesure

        this.position_x_initial_plateau = w-(1+4)*r; //coordonnee x de la ligne 0 et colonne 0 du plateau
        this.position_y_initial_plateau = h-(1+4)*r; //coordonnee y de la ligne 0 et colonne 0 du plateau
        this.position_x_initial_piece = r; //coordonnee x de la ligne 0 et colonne 0 du plateau de piece initial
        this.position_y_initial_piece = r; //coordonnee y de la ligne 0 et colonne 0 du plateau de piece initial

        this.position_x_logo = this.position_x_initial_plateau+(this.width-3*r)/2;
        this.position_y_logo = this.position_y_initial_plateau+(this.width-r)/2;

        a = w/(1+4+1+4+1)*4;
        b = h/(1+4+1)*4;
        r = Math.min(a,b);
        r=r/4;
        this.position_x_label = 2*r/3;
        this.position_y_label = h-2*r/3;
        this.parametre_taille_label = (float) r / (6.0f*15.0f);
    }
}
