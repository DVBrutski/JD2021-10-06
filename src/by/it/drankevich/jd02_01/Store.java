package by.it.drankevich.jd02_01;

import by.it.drankevich.jd02_01.entity.Customer;
import by.it.drankevich.jd02_01.entity.ShoppingCart;
import by.it.drankevich.jd02_01.helper.RandomGenerator;
import by.it.drankevich.jd02_01.helper.Timeout;
import by.it.drankevich.jd02_01.service.CustomerWorker;

import java.util.ArrayList;

public class Store extends Thread {

    @Override
    public void run() {
        System.out.println("Store opened");
        int customerCounter = 0;

        ArrayList<CustomerWorker> customerWorkers = new ArrayList<>();

        for (int second = 0; second < 120; second++) {
            int count = RandomGenerator.get(0, 2);


            for (int i = 0; i < count; i++) {

                Customer customer = new Customer(++customerCounter);
                ShoppingCart shoppingCart = new ShoppingCart();
                CustomerWorker customerWorker = new CustomerWorker(customer, shoppingCart);
                customerWorker.start();
                Timeout.sleep(1000);
                customerWorkers.add(customerWorker);

            }
        }
            for (CustomerWorker customerWorker : customerWorkers) {
                try {
                    customerWorker.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



        }
        System.out.println("Store closed");
    }
}
