package NotesMuscles.util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha256_Encoder {

    String hashableWord ;
    String resultString ;

    private byte[] getSHA() throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
 
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(hashableWord.getBytes(StandardCharsets.UTF_8));
    }
     
    private String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
 
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
 
        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }
 
        return hexString.toString();
    }

    public String getHashed(String input) throws NoSuchAlgorithmException{
        hashableWord = input ;
        byte[] temp =  this.getSHA() ;
        resultString = toHexString(temp) ; 
        return resultString;
    }
}