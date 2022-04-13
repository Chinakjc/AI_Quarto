package Tools;

/**
 * Cette classe sert a convertir un nombre en hexadecimal
 */
public class Hexadecimal {
    private String value;
    public Hexadecimal(String number){
        this.value = new String(number);
    }

    public String getValue() {
        return new String(value);
    }

    public String getValue(int n){
        String res = new String(value);
        if(res.length()<n){
            res = "0"+res;
            return new Hexadecimal(res).getValue(n);
        }
        return res;
    }

    public void show(){
        System.out.println(value);
    }
}
