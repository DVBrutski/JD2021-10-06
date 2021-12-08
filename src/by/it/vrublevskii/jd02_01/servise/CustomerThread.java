package by.it.vrublevskii.jd02_01.servise;


import by.it.vrublevskii.jd02_01.entity.Customer;
import by.it.vrublevskii.jd02_01.entity.Good;
import by.it.vrublevskii.jd02_01.entity.PriceListRepo;
import by.it.vrublevskii.jd02_01.helper.GoodGenerator;
import by.it.vrublevskii.jd02_01.helper.NumberCustomersInStore;
import by.it.vrublevskii.jd02_01.helper.RandomGenerator;
import by.it.vrublevskii.jd02_01.helper.Timeout;

public class CustomerThread extends Thread implements CustomerAction, ShoppingCardAction {
    private final Customer customer;
    private final PriceListRepo priceListRepo;
    private int goodsInCard = 0;

    public CustomerThread(Customer customer, PriceListRepo priceListRepo) {
        this.customer = customer;
        this.priceListRepo = priceListRepo;
    }

    @Override
    public void run() {
        NumberCustomersInStore.customerCame();
        enteredStore();
        takeCart();
        int customerNeedsGoodsNumber;
        if (customer.getType().equals("Student")) {
            customerNeedsGoodsNumber = RandomGenerator.get(0, 2);
        } else {
            customerNeedsGoodsNumber = RandomGenerator.get(2, 5);
        }
        if (customerNeedsGoodsNumber != 0) {
            for (int j = 0; j < customerNeedsGoodsNumber; j++) {
                Good good = chooseGood();
                putToCart(good);
            }
        }
        goOut();
        NumberCustomersInStore.customerLeft();
    }

    @Override
    public void enteredStore() {
        System.out.println(customer + " entered the store");
    }

    @Override
    public void takeCart() {
        double coefficient = 1;
        if (customer.getType().equals("Pensioner")) {
            coefficient = 1.5;
        }
        int timeout = (int) (RandomGenerator.get(100, 300) * coefficient);
        Timeout.sleep(timeout);
        System.out.println(customer + " takes a cart");
    }

    @Override
    public Good chooseGood() {
        double coefficient = 1;
        if (customer.getType().equals("Pensioner")) {
            coefficient = 1.5;
        }
        System.out.println(customer + " is choosing goods");
        int timeout = (int) (RandomGenerator.get(500, 2000) * coefficient);
        Timeout.sleep(timeout);
        Good good = GoodGenerator.get(priceListRepo);
        System.out.println(customer + " chose " + good);
        return good;
    }

    @Override
    public int putToCart(Good good) {
        goodsInCard++;
        double coefficient = 1;
        if (customer.getType().equals("Pensioner")) {
            coefficient = 1.5;
        }
        int timeout = (int) (RandomGenerator.get(100, 300) * coefficient);
        Timeout.sleep(timeout);
        System.out.println(customer + " puts " + good + " in the cart");
        return goodsInCard;
    }

    @Override
    public void goOut() {
        System.out.printf("%s leaves the store. %20s Goods bought: %d%n", customer, "", goodsInCard);
    }
}
