package chapter12.ex04;

import java.util.Comparator;

public class GrapeComp implements Comparator<Grape> {
    @Override
    public int compare(Grape o1, Grape o2) {
        return o2.weight - o1.weight;
    }
}
