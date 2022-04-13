package Tools;

/**
 * Cette classe a pour objectif de stocker l'ensemble des valeurs des effets sonores pour une meilleure gestion du volume
 * dans la page option via l'utilisation du slider.
 */
public class Vol {
    public static float base=0.5f;
    public static float[] vols = {2.0f*base,2.0f*base,2.0f*base,1.0f*base,1.4f*base,1.8f*base,2.0f*base};

    /**
     * C'est une methode pour mettre la nouvelle valeur qu'on donne et met a jour la liste des volumes.
     * @param base
     */
    public void setBase(float base){
        Vol.base = base;
        vols[0] = 2.0f * base;
        vols[1] = 2.0f * base;
        vols[2] = 2.0f * base;
        vols[3] = 1.0f * base;
        vols[4] = 1.4f * base;
        vols[5] = 1.8f * base;
        vols[6] = 2.0f * base;
    }


}
