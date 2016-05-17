package compressionlabs;

import java.util.HashMap;

public class CompressionLabs {
    public static void main(String[] args) {
        
        String input = "7ot hna el code bta3ak";
        
        IEncoder huffman = new HuffmanEncoding();
        IEncoder rle = new RunLengthEncoding();
        
        String huffmanOut = huffman.encode(input);
        String rleOut = rle.encode(huffmanOut);
        
        System.out.println(rleOut);
        
//        HashMap<String, Integer> digitMap = new HashMap<String, Integer>();
//        
//        for (int i=0; i<16; i++) {
//            digitMap.put(toBinary(i), i);
//        }
//        
//        String input = "00000001001000100011001101011000100010011001";
//        StringBuilder cInput = new StringBuilder();
//        
//        for (int i=0; i<input.length(); i+=4) {
//            cInput.append(digitMap.get(input.substring(i, i+4))).append(" ");
//        }
//        
//        IEncoder compressor = new LZWEncoding();
//        
//        System.out.println(compressor.decode(cInput.toString()));                
    }
    
    private static String toBinary(int n) {        
        StringBuilder output = new StringBuilder();
        
        while (n >= 1) {
            if (n%2 == 0) {
                output.append("0");
            } else {
                output.append("1");
            }
            
            n /= 2;
        }
        
        while (output.length() < 4) {
            output.append("0");
        }
        
        return output.reverse().toString();
    }
}
