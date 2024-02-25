package chapter7.example25;

public class XMLParser implements Parseable {
    @Override
    public void parse(String fileName) {
        System.out.println(fileName + " - XML parsing completed.");
    }
}
