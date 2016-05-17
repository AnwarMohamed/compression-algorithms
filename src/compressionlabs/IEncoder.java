package compressionlabs;

public interface IEncoder {
    String encode(String plain);
    String decode(String compressed);
}
