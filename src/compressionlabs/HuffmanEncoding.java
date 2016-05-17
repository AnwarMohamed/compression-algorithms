package compressionlabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class HuffmanEncoding implements IEncoder {
    public static void main(String[] args) {        
        IEncoder compressor = new HuffmanEncoding();
        
        System.out.println("BACADAEAFABBAAAGAH");
        System.out.println(compressor.encode("BACADAEAFABBAAAGAH"));                
    }

    private final HashMap<Character, Integer> digitFrequency; 
    private final HashMap<Character, Integer> digitProbability;
    private final HashMap<Character, String> digitMap;
    private final SortedArrayList<Node> digitTree;
    
    public HuffmanEncoding() {
        digitFrequency = new HashMap<Character, Integer>();
        digitProbability = new HashMap<Character, Integer>();
        digitMap = new HashMap<Character, String>();
        digitTree = new SortedArrayList<Node>();
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
                    (int) Math.rint((digitFrequency.get(digit))/(plain.length()*1.0) * 100));            
        } 

        for (Map.Entry<Character, Integer> entries: digitProbability.entrySet()) {
            Node childNode = new Node();

            childNode.setName(Character.toString(entries.getKey()));
            childNode.setValue(entries.getValue());

            digitTree.insertSorted(childNode);
        }        

        buildTree(digitTree);
        buildMap(digitTree.get(0), digitMap, "");
        
        for (int i=0; i<plain.length(); i++) { 
            output.append(digitMap.get(plain.charAt(i)));
        }
        
//        for (Map.Entry<Character, String> entries: digitMap.entrySet()) {
//            output.append(entries.getKey()).append("\t");
//            output.append(entries.getValue()).append("\n");
//        }
        
        return output.toString();
    }

    private void buildMap(Node root, Map<Character, String> map, String path) {
        for (Node node: root.getChildren()) {
            if (node.getName() != null) {
                map.put(
                        node.getName().charAt(0),
                        path.concat(node.getSymbol()));
            } else {
                buildMap(node, digitMap, path.concat(node.getSymbol()));
            }
        }
    }
    
    private void buildTree(SortedArrayList<Node> tree) {
        if (tree.size() >= 2) {
            tree.get(0).setSymbol("0");
            tree.get(1).setSymbol("1");
            
            Node childNode = new Node();
            childNode.addChild(tree.get(0));
            childNode.addChild(tree.get(1));
            
            tree.remove(0);
            tree.remove(0);
            
            tree.insertSorted(childNode);
            
            buildTree(tree);
        }
    }
    
    @Override
    public String decode(String compressed) {
        return null;
    }
    
    private class SortedArrayList<T> extends ArrayList<T> {        
        public void insertSorted(T value) {
            add(value);
            Comparable<T> cmp = (Comparable<T>) value;
            for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1)) < 0; i--)
                Collections.swap(this, i, i-1);
        }
    }
    
    private class Node implements Comparable {
        private String name, symbol;
        private int value = 0;
  
        private final SortedArrayList<Node> children;

        private Node() {
            this.children = new SortedArrayList<Node>();
        }
        
        public String getName() {
            return name;
        }        
        
        public void setName(String name) {
            this.name = name;
        }         
        
        public void addChild(Node node) {
            children.add(node);            
            value += node.getValue();
        }
        
        public ArrayList<Node> getChildren() {
            return children;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
        
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
        
        @Override
        public int compareTo(Object t) {
            return getValue() - ((Node)(t)).getValue();
        }
    }
}
