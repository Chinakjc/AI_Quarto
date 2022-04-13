package Setting;

/**
 * Cette classe a comme objectif une meilleure gestion des differents affichages de widgets a l'ecran
 * pour toujours respecter une certaine proportion d'ecran.
 */
public class SizeSetting {
    private int width_screen;
    private int height_screen;

    public int width_boutons;
    public int height_boutons;
    public int width_slider;
    public int height_slider;
    public int width_logo;
    public int height_logo;
    public int size_smallButton;
    public int position_x_initial_boutons;
    public int position_y_initial_boutons;
    public int position_x_initial_slider;
    public int position_y_initial_slider;
    public int position_x_initial_logo;
    public int position_y_initial_logo;
    public int unite;
    public float fontScale;
    public int position_x_initial_label;

    public SizeSetting(int width, int height){
        this.width_screen = width;
        this.height_screen = height;


        int a = width_screen/(1+3+2+1+1+1+1+1+1);
        int b = height_screen/(9);

        this.unite = Math.min(a,b);

        width_boutons = unite;
        height_boutons = unite;

        width_slider = 4*unite;
        height_slider = unite/3;

        width_logo = 9*unite;
        height_logo = 3*unite;

        size_smallButton = unite*2/3;

        position_x_initial_boutons = (int) (width/2 + 1.5*unite);
        position_y_initial_boutons = height/2 - unite;

        position_x_initial_slider = position_x_initial_boutons;
        position_y_initial_slider = Math.min(height/2 - 2*unite,2*unite);

        position_x_initial_logo = (width - width_logo)/2;
        position_y_initial_logo = (height - height_logo)/2;

        fontScale = unite/(50.0f);

        position_x_initial_label = (int) Math.max((width/2 -6.5*unite),unite);
    }
}