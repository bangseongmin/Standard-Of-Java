package chapter07.example22;

public class Buyer {

    int money = 1000;
    int bonusPoint = 0;
    Product[] items = new Product[10];
    int i = 0;

    void buy(Product p) {
        if(money < p.price) {
            System.out.println("잔액 부족");
            return;
        }

        money -= p.price;
        bonusPoint += p.bonusPoint;
        items[i++] = p;
        System.out.println(p + "을/를 구입");
    }

    void summary() {
        int sum = 0;
        String itemList = "";

        for(int i = 0; i < items.length; i++) {
            if(items[i] == null) {
                break;
            }

            sum += items[i].price;
            itemList += items[i] + ", ";
        }

        System.out.println("구입하신 물품의 총금액은 " + sum + "만원");
        System.out.println("구입하신 제품은 " + itemList);
    }
}
