package chapter07.example25;

public class HTMLParser implements Parseable {
    @Override
    public void parse(String fileName) {
        System.out.println(fileName + " - HTMLParser parsing completed.");
    }
}
