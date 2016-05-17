package compressionlabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class TunstallEncoding implements IEncoder {    
    public static void main(String[] args) {        
        IEncoder compressor = new TunstallEncoding();
        
        System.out.println("abbcdssdfadaaadsb");
        System.out.println(compressor.encode("abbcdssdfadaaadsb"));        
    }

    private final HashMap<Character, Integer> digitFrequency;
    private final HashMap<Character, Float> digitProbability;
    private int selectedN;

    public TunstallEncoding() {
        digitFrequency = new HashMap<Character, Integer>();
        digitProbability = new HashMap<Character, Float>();
    }
    
    @Override
    public String encode(String plain) {
        StringBuilder output = new StringBuilder();
        
        Character digit;
        
        for (int i=0; i<plain.length(); i++) {            
            digit =  plain.charAt(i);
            
            if (digitFrequency.containsKey(digit)) {
                digitFrequency.put(digit, digitFrequency.get(digit) + 1);
            } else {
                digitFrequency.put(digit, 1);                
            }
            
            digitProbability.put(
                    digit,
                    (float)(digitFrequency.get(digit)/(plain.length()*1.0)));
        }                      
        
        selectedN = (int) Math.ceil(Math.log(digitFrequency.size()) / Math.log(2));
        
        if (Math.pow(2, selectedN) <= digitFrequency.size()) {
            selectedN++;
        }
        
        Node rootNode = new Node();        
        rootNode.setName("root");
        rootNode.setProbability(1);
        
        buildTree(rootNode, 1);
        
        float averageBitRate = selectedN/getAverageLength(rootNode, 1);
        float entropy = getEntropy(rootNode);
        
        output.append("ABR: ").append(averageBitRate).append("\n");
        output.append("Entropy: ").append(entropy);
        
        return output.toString();
    }    
    
    private float getEntropy(Node root) {
        float sum = 0;        
        for (Node node: root.getChildren()) {
            if (node.getChildren().isEmpty()) {
                sum += (-1.0)*((node.getProbability() + 
                        (Math.log(node.getProbability())/Math.log(2))));
            } else {
                sum += getEntropy(node);
            }
        }
        
        return sum;
    }
    
    private float getAverageLength(Node root, int n) {
        float average = 0;       
        for (Node node: root.getChildren()) {
            if (node.getChildren().isEmpty()) {
                average += node.getProbability();
            } else {
                average += getAverageLength(node, n+1);
            }
        }
        
        return n*average;
    }
    
    private void buildTree(Node root, int n) {
        float hProbability = 0;
        Node hNode = null;       
        
        for (Entry<Character, Float> entries: digitProbability.entrySet()) {
            Node childNode = new Node();
            
            childNode.setName(Character.toString(entries.getKey()));
            childNode.setProbability(
                    entries.getValue() * root.getProbability());
            
            if (childNode.getProbability() > hProbability) {
                hProbability = childNode.getProbability();
                hNode = childNode;
            }
            
//            System.out.println(
//                    "n:"+n +"\t"+
//                            childNode.getName()+"\t"+
//                            childNode.getProbability());
            
            root.addChild(childNode);
        }
        
        if (n*(digitProbability.size() -1)+1 > 
                Math.pow(2, selectedN)-digitProbability.size()) {
            
            for (Entry<Character, Float> entries: digitProbability.entrySet()) {
                Node childNode = new Node();

                childNode.setName(Character.toString(entries.getKey()));
                childNode.setProbability(
                        entries.getValue() * hNode.getProbability());
                                            
//                System.out.println(
//                        "n:"+(n+1) +"\t"+
//                                childNode.getName()+"\t"+
//                                childNode.getProbability());
                
                hNode.addChild(childNode);
            }
            
        } else {
            buildTree(hNode, n+1);
        }               
    }
    
    @Override
    public String decode(String compressed) {
        return null;
    }
    
    private class Node {
        private String name;        
        private float probability;        
        private final ArrayList<Node> children;

        private Node() {
            this.children = new ArrayList();
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getProbability() {
            return probability;
        }

        public void setProbability(float probability) {
            this.probability = probability;
        }                
        
        public void addChild(Node node) {
            children.add(node);
        }
        
        public ArrayList<Node> getChildren() {
            return children;
        }
    }
}
