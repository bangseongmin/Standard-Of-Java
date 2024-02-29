package chapter07.example01;

public class CaptionTV extends TV {

   boolean caption;     // 캡션상태(on/off)

    void displayCaption(String text) {
        // 캡션상태가 on일때만 text를 보여 준다.
        if(caption) {
            System.out.println(text);
        }
    }
}
