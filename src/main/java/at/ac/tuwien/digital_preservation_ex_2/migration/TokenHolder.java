package at.ac.tuwien.digital_preservation_ex_2.migration;

public class TokenHolder {

    private static String token;

    public static void setToken(String token){
        TokenHolder.token = token;
    }

    public static String getToken(){
        return token;
    }

}
