
package model;

public class Enkripsi {
    public String encrypt(String text){
        return Integer.toHexString(text.hashCode());
    }
}
