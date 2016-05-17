package compressionlabs;


public class RunLengthEncoding implements IEncoder {
    public static void main(String[] args) {        
        IEncoder compressor = new RunLengthEncoding();
        
        System.out.println("ABCCCCCCCCCDEFFFFGGG");
        System.out.println(compressor.encode("ABCCCCCCCCCDEFFFFGGG"));
        System.out.println(compressor.decode("ABC!9DEF!4GGG"));        
    }
            
    @Override
    public String encode(String plain) {
        StringBuilder output = new StringBuilder();
        
        int digitCount = 0, i = 0;                        
        char digit = plain.charAt(i);
        
        for (; i<plain.length(); i++) {
            if (plain.charAt(i) == digit) {
                digitCount++;
                
                if (plain.length() == i+1) {
                    if (digitCount >= 4) {
                        output.append(digit);
                        output.append("!");
                        output.append(digitCount);                        
                    } else {
                        while (digitCount-- > 0) {
                            output.append(digit);
                        }
                    }
                }
            } else {
                if (digitCount >= 4) {
                    output.append(digit);
                    output.append("!");
                    output.append(digitCount);                    
                } else {
                    while (digitCount-- > 0) {
                        output.append(digit);
                    }
                }
                
                digitCount = 1;
                digit = plain.charAt(i);
            }
        }
        
        return output.toString();
    }
    
    @Override
    public String decode(String compressed) {
        StringBuilder output = new StringBuilder(); 
        
        char digit;
        String digitCount;

        for (int i=0; i<compressed.length(); i++) {            
            if (compressed.charAt(i) == '!') {
                digit = compressed.charAt(i-1);
                digitCount = "";               
                
                i++;
                
                while (i<compressed.length() && 
                    Character.isDigit(compressed.charAt(i))) {
                    digitCount = digitCount.concat(
                            Character.toString(compressed.charAt(i++)));                                
                }
                
                for (int j=0; i<compressed.length() 
                        && j<Integer.parseInt(digitCount)-1; j++) {
                    output.append(digit);
                }
                
                i--;
                
            } else {
                output.append(compressed.charAt(i));
            }
        }
        
        return output.toString();
    }
}
