package by.it.konon.jd02_02.service;

import by.it.konon.jd02_02.entity.Goods;
import by.it.konon.jd02_02.entity.PriceList;
import by.it.konon.jd02_02.helper.RandomGenerator;

public class PriceListRepo {

    private final PriceList priceList;

    public PriceListRepo() {
        this.priceList = new PriceList();
    }


    public double getGoodsPrice(String nameSelectedGood) {
        return priceList.getPriceList().get(nameSelectedGood);
    }

    public String chooseGoodFromPriceList() {
        String[] arrayNamesGoods = new Goods().getGoods();
        return arrayNamesGoods[RandomGenerator.get(0, arrayNamesGoods.length - 1)];
    }

}