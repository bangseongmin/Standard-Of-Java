package chapter7.polyArgument3;

import java.util.Vector;

public class Buyer {

    int money = 1000;
    int bonusPoint = 0;
    Vector items = new Vector();

    void buy(Product p) {
        if (money < p.price) {
            System.out.println("잔액 부족");
            return;
        }

        money -= p.price;
        bonusPoint += p.bonusPoint;
        items.add(p);
        System.out.println(p + "을/를 구입");
    }

    void refund(Product p) {
        if (items.remove(p)) {
            money += p.price;
            bonusPoint -= p.bonusPoint;
            System.out.println(p + "반품 완료");
        } else {
            System.out.println("구입하신 제품 중 해당 제품이 존재하지 않습니다.");
        }
    }

    void summary() {
        int sum = 0;
        String itemList = "";

        if (items.isEmpty()) {
            System.out.println("구입하신 제품이 없습니다.");
            return;
        }

        for (int i = 0; i < items.size(); i++) {
            Product p = (Product) items.get(i);
            sum += p.price;
            itemList += (i == 0) ? p : ", " + p;
        }

        System.out.println("구입하신 물품의 총금액은 " + sum + "만원");
        System.out.println("구입하신 제품은 " + itemList);
    }
}
