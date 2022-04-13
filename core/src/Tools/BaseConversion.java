package Tools;

/**
 * classe cree pour une meilleure gestion des datas pour l'utilisation de backup.
 */
public class BaseConversion {
    /**
     * C'est une methode qui transforme le nombre decimal en un nombre binaire.
     * @param number
     * @return
     */
    public static int decimalToBinary(int number){
        if(number<2) return number;
        return 10*(decimalToBinary(number/2))+number%2;
    }

    /**
     * C'est une methode qui transforme le nombre decimal en un nombre hexadecimal.
     * @param number
     * @return
     * @see Hexadecimal
     */
    public static Hexadecimal decimalTOHexadecimal(int number){
        String value;
        if((number>=0)&&(number<10)){
            value = (number+"");
        }
        else if(number<16){
            value = Character.toString(  (char) ( (int)'A' + (number-10)) );
        }
        else{
            value = decimalTOHexadecimal(number/16).getValue() + decimalTOHexadecimal(number%16).getValue();
        }

        return new Hexadecimal(value);
    }

    /**
     * C'est une methode qui transforme le nombre de base qu'on donne en un nombre decimal.
     * @param base
     * @param number
     * @return
     */
    public static int toDecimal(int base, String number){
        int res=0;
        for (int i=0;i<number.length();i++){
            char temp = Character.toUpperCase(number.charAt(i));
            if( ('0'<=temp) && (temp<='9') ){
                res+= (int)(((int)(temp)-(int)'0')*Math.pow(base,number.length()-i-1));
            }
            else{
                res+=(int)(((int)(temp)-(int)'A'+10)*Math.pow(base,number.length()-i-1));
            }
        }
        return res;
    }

    /**
     * C'est une methode qui transforme le nombre hexadecimal en un nombre decimal.
     * @param number
     * @return
     * @see BaseConversion#toDecimal(int, String)
     * @see Hexadecimal#getValue()
     */
    public static int hexadecimalToDecimal(Hexadecimal number){
        return toDecimal(16,number.getValue());
    }

}
