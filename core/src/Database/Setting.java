package Database;

import com.badlogic.gdx.files.FileHandle;

import java.awt.*;
import java.util.StringTokenizer;

/**
 * La classe sert a la gestion des options
 */
public class Setting {
    private float baseVol; //0.0-1.0
    private boolean isFullscreen; //true pour fullscreen
    private int width;
    private int height;
    private int fps;

    public Setting(){
        this.isFullscreen = true;
        this.baseVol = 0.5f;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.width = gd.getDisplayMode().getWidth();
        this.height = gd.getDisplayMode().getHeight();
        this.fps = 60;
    }

    public void setBaseVol(float baseVol) {
        this.baseVol = baseVol;
    }

    public void setFullscreen(boolean fullscreen) {
        this.isFullscreen = fullscreen;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    /**
     * C'est une methode qui met tous les datas de config dans le fichier setting/config.
     */
    public void writeSetting(){
        FileHandle fileHandle = new FileHandle("setting/config");
        String temp = new String();
        temp+= baseVol;
        temp+="#";
        temp+=isFullscreen;
        temp+="#";
        temp+=width;
        temp+="#";
        temp+=height;
        temp+="#";
        temp+=fps;
        fileHandle.writeString(temp,false);
    }

    /**
     * C'est une methode qui lit le fichier setting/config et retire tous les datas de fichier a l'objet Setting.
     * Elle retourne true si reussi sinon false.
     * @return
     */
    public boolean readSetting(){
        FileHandle fileHandle = new FileHandle("setting/config");
        if(fileHandle.exists()){
            StringTokenizer st = new StringTokenizer(fileHandle.readString(),"#");
            this.baseVol = Float.parseFloat(st.nextToken());
            this.isFullscreen = Boolean.parseBoolean(st.nextToken());
            this.width = Integer.parseInt(st.nextToken());
            this.height = Integer.parseInt(st.nextToken());
            this.fps = Integer.parseInt(st.nextToken());
            return true;
        }
        System.out.println("file not found ! ");
        return false;
    }

    public float getBaseVol() {
        return baseVol;
    }

    public boolean getIsFullscreen(){
        return isFullscreen;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getFps() {
        return fps;
    }

	
}
