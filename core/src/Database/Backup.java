package Database;

import Game.main.Piece;
import Tools.BaseConversion;
import com.badlogic.gdx.files.FileHandle;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 * La classe est une classe intermediaire entre le fichier de sauvegarde et la classe Data
 */
public class Backup {
    int mode;
    String[] players;
    int compteur;
    String[] infoPieces; // pour chaque piece, un nombre binaire de 8 bits est suffisant pour donner toutes les infos.
                     //select 1 bit, piece dispo 1bit, position 4bits (2 bits pour lignes et 2 bits pour colonnes)
                    //donc on peut utiliser un nombre de 2 bit de base 16. Donc une chaine de caractere comme "EF"

    public Backup(int mode,String player1,String player2,int compteur,String[] infoPieces){
        this.mode = mode;
        this.players = new String[2];
        this.players[0] = player1;
        this.players[1] = player2;
        this.compteur = compteur;
        this.infoPieces = new String[16];
        for (int i=0;i<16;i++){
            this.infoPieces[i] = infoPieces[i];
        }
    }

    public Backup(){ //definition initiale
        this.players = new String[2];
        this.infoPieces = new String[16];
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPlayers(String player1,  String player2){
        this.players = new String[2];
        this.players[0] = player1;
        this.players[1] = player2;
    }

    public void setCompteur(int compteur){
        this.compteur = compteur;
    }

    public void setInfoPieces(Piece[] pieces){
        infoPieces = new String[16];
        for (int i=0; i<16; i++){
            Piece piece = pieces[i];
            int temp = 0;
            temp = (int)(piece.est_selectionnee * Math.pow(2,5)
                    + piece.est_disponible * Math.pow(2,4)
                    + piece.ligne * Math.pow(4,1)
                    + piece.colonne * Math.pow(4,0));
            infoPieces[i] = BaseConversion.decimalTOHexadecimal(temp).getValue(2);
        }

    }

    /**
     *C'est une methode qui peut transformer un objet de Backup a Data
     * @return
     * @see BaseConversion#toDecimal(int, String)
     * @see BaseConversion#decimalToBinary(int)
     * @see Data
     * @see Piece
     */
    public Data toData(){
        Data res = new Data(mode);
        for (int i=0;i<16;i++){
            String info = infoPieces[i];
            char info1 = info.charAt(0); // informations pour select et disponible
            char info2 = info.charAt(1); // informations pour position
            int temp = BaseConversion.toDecimal(16,Character.toString(info1));
            temp = BaseConversion.decimalToBinary(temp);
            res.piece[i].est_selectionnee = temp/10;
            res.piece[i].est_disponible = temp%10;

            temp = BaseConversion.toDecimal(16,Character.toString(info2));
            temp = BaseConversion.decimalToBinary(temp);
            int a,b,c,d;
            a = temp/1000;
            b = (temp-1000*a)/100;
            c = (temp-1000*a-100*b)/10;
            d = temp%10;
            res.piece[i].ligne = 2*a+b;
            res.piece[i].colonne = 2*c+d;
        }
        res.liste_joueur[0] = players[0];
        res.liste_joueur[1] = players[1];

        res.compteur = compteur;
        for (int i=0;i<16;i++){
            if(res.piece[i].est_disponible==0){
                int l = res.piece[i].ligne;
                int c = res.piece[i].colonne;
                res.indice_piece_sur_case_de_plateau[l][c] = i;
            }
        }
        return res;
    }

    /**
     *C'est une methode qui met les datas d'un objet de classe Backup dans un fichier du disque dur
     * @param fileName
     */
    public void writeData(String fileName){
        FileHandle player1 = new FileHandle("data/"+fileName+".player1");
        FileHandle player2 = new FileHandle("data/"+fileName+".player2");
        FileHandle others = new FileHandle("data/"+fileName+".data");

        player1.writeString(players[0],false );
        player2.writeString(players[1],false );
        String temp = new String();
        temp += mode;
        temp += ("#"+compteur);
        for (int i=0;i<16;i++){
            temp += ("#"+infoPieces[i]);
        }
        others.writeString(temp,false);

    }

    /**
     *C'est une methode qui lit le fichier data et qui transmet son contenu a l'objet Backup.
     * @param fileName
     * @return
     */
    public boolean readData(String fileName){
        FileHandle player1 = new FileHandle("data/"+fileName+".player1");
        FileHandle player2 = new FileHandle("data/"+fileName+".player2");
        FileHandle others = new FileHandle("data/"+fileName+".data");
        if((player1.exists())&&(player2.exists())&&(others.exists())){
            this.players[0] = player1.readString();
            this.players[1] = player2.readString();
            StringTokenizer st = new StringTokenizer(others.readString(),"#");
            this.mode = Integer.parseInt(st.nextToken());
            this.compteur = Integer.parseInt(st.nextToken());

            for (int i=0;i<16;i++){
                this.infoPieces[i] = st.nextToken();
            }
            return true;
        }
        System.out.println("file not found ! ");
        return false;
    }

    /**
     * Cette methode permet de comparer 1 objet Backup avec un autre objet
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Backup backup = (Backup) o;
        return mode == backup.mode && compteur == backup.compteur && Arrays.equals(players, backup.players) && Arrays.equals(infoPieces, backup.infoPieces);
    }

    //methode genere automatiquement
    @Override
    public int hashCode() {
        int result = Objects.hash(mode, compteur);
        result = 31 * result + Arrays.hashCode(players);
        result = 31 * result + Arrays.hashCode(infoPieces);
        return result;
    }
}