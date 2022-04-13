package MainMenu;


/**
 * Cette classe a comme objectif une meilleure gestion des differentes tailles d'affichages de boutons et textures a l'ecran
 * pour toujours respecter une certaine proportion d'ecran.
 */
public class SizeMainMenu {

    public int width_boutons;
    public int height_boutons;
    public int width_logo;
    public int height_logo;
    public int position_x_initial_boutons;
    public int position_y_initial_boutons;
    public int position_x_initial_logo;
    public int position_y_initial_logo;
    public int unite;
    
    public int width_pegi;
    public int height_pegi;
    public int position_x_initial_pegi;
    public int position_y_initial_pegi;

    public int width_leaf;
    public int height_leaf;
    public int position_x_initial_leaf;
    public int position_y_initial_leaf;
    
    public SizeMainMenu(int w, int h){
        int a;
        int b;

        a = Math.max(w,h)/(3+9+3); //max est pour adapter les écrans horzontaux(w>h) et verticaux(w<h) !
        b = Math.min(w,h)/(1+3+1+2+1+2+1+2+1+2+1+2+1); // min est pour adapter les écrans horizontaux et verticaux !

        this.unite = Math.min(a,b);

        this.width_boutons = unite*8;  //w:h = 4:1
        this.height_boutons = unite*2;

        this.width_logo = unite*9;  //w:h = 3:1
        this.height_logo = unite*3;
        
        this.width_pegi=unite*2;
        this.height_pegi=unite*2;

        this.width_leaf=unite*6;
        this.height_leaf=unite*4;
        
        this.position_x_initial_logo= (w-this.width_logo)/2;
        this.position_y_initial_logo = h-(1+3)*unite;
        this.position_x_initial_boutons = (w-this.width_boutons)/2;
        this.position_y_initial_boutons = unite;

        this.position_x_initial_pegi= (int) (w-(2*this.width_pegi)+ this.width_pegi/1.5);
        this.position_y_initial_pegi= (int) (0.5*unite);
        
        this.position_x_initial_leaf=(w-this.width_leaf);
        this.position_y_initial_leaf=h-(1+3)*unite;

        if(w<h){
            double proportion = h/w;
            double k = 1+Math.atan(proportion)/Math.PI; //( k ]1,1.5[ );
            width_logo = (int) (width_logo*k);
            height_logo = (int)(height_logo*k);
            int y0 = position_y_initial_boutons + 5*(unite + height_boutons);
            int y = (int)(0.5*(h +y0 - height_logo));
            this.position_x_initial_logo= (w-this.width_logo)/2;
            this.position_y_initial_logo = y;
        }
    }
}
