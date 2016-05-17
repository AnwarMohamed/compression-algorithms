package compressionlabs;

import java.util.Scanner;


public class DifferentialEncoding implements IEncoder {
    public static void main(String[] args) {        
        IEncoder compressor = new DifferentialEncoding();
        
        System.out.println("ABCCCCCCCCCDEFFFFGGG");
        System.out.println(compressor.encode("ABCCCCCCCCCDEFFFFGGG"));
        System.out.println(compressor.decode("ABC!9DEF!4GGG"));        
    }

    @Override
    public String encode(String plain) {
        StringBuilder output = new StringBuilder();
        Scanner scanner = new Scanner(plain);
        
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        
        int a=0, b;
        
        for (int i=0; i<x; i++) {
            for (int j=0; j<y; j++) {                
                b = scanner.nextInt();                
                output.append(b-a);
                output.append(" ");
                a = b;
            }
        }
        
        return output.toString();
    }

    @Override
    public String decode(String compressed) {
        StringBuilder output = new StringBuilder();
        
        
        
        return output.toString();
    }
}
