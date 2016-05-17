package compressionlabs;

import java.util.ArrayList;
import java.util.Scanner;

public class LZWEncoding implements IEncoder {
 public static void main(String[] args) {        
        IEncoder compressor = new LZWEncoding();
        
        //System.out.println("ababababa");
        //System.out.println(compressor.encode("ababababa"));              
        //System.out.println(compressor.decode("0 1 2 2 3 3 5 8 8 9 9"));
    }    

    private final ArrayList<String> digitMap;
    
    public LZWEncoding() {
        digitMap = new ArrayList<String>();
    }
 
    @Override
    public String encode(String plain) {
        StringBuilder output = new StringBuilder();
        String sDigit, cDigit;
        
        digitMap.clear();
        
        sDigit = Character.toString(plain.charAt(0));
        
        //for (int i=0; i<0xff; i++) {            
        //    digitMap.add(Character.toString((char) i));            
        //}
        
        digitMap.add(Character.toString('x'));
        digitMap.add(Character.toString('z'));
        
        for (int i=1; i<plain.length(); i++) {
            cDigit = Character.toString(plain.charAt(i));
            
            if (digitMap.contains(sDigit.concat(cDigit))) {
                sDigit = sDigit.concat(cDigit);
            } else {
                output.append(digitMap.indexOf(sDigit));
                output.append(" ");
                
                digitMap.add(sDigit.concat(cDigit));
                sDigit = cDigit;
            }
        }
        
        output.append(digitMap.indexOf(sDigit));                
        
        return output.toString();
    }

    @Override
    public String decode(String compressed) {
        StringBuilder output = new StringBuilder();
        Scanner scanner = new Scanner(compressed);
        
        int oCode, nCode;
        String cDigit = "", sDigit;
        
        digitMap.clear();
        
        //for (int i=0; i<0xff; i++) {            
        //    digitMap.add(Character.toString((char) i));            
        //}
        
        digitMap.add(Character.toString('x'));
        digitMap.add(Character.toString('z'));
        
        oCode = scanner.nextInt();
        output.append(digitMap.get(oCode));
        
        while (scanner.hasNext()) {
            nCode = scanner.nextInt();
            
            if (nCode < digitMap.size()) {
                sDigit = digitMap.get(nCode);
            } else {
                sDigit = digitMap.get(oCode);
                sDigit = sDigit.concat(cDigit);
            }
            
            output.append(sDigit);
            
            cDigit = Character.toString(sDigit.charAt(0));
            
            //digitMap.add(Character.toString((char)oCode).concat(cDigit));
            digitMap.add(digitMap.get(oCode).concat(cDigit));
            
            oCode = nCode;
        }
        
        return output.toString();
    }
}
