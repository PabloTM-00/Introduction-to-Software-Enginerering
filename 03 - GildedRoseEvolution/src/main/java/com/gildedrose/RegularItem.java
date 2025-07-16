package com.gildedrose;

public class RegularItem extends TypedItem implements ItemInterface {

    public RegularItem(int newSellIn, int newQuality) {
        super(REGULAR, newSellIn, newQuality);
    }

    @Override
    public void updateQuality() {
        // Decrease quality before the sell date
        decreaseQuality();

        sellIn--; // Decrease sellIn

        // After sell date, decrease quality again
        if (sellIn < 0) {
            decreaseQuality();
        }
    }

    public String getName() {
        return name;
    }

     public int getQuality() {
        return quality;
    }

     public int getSellIn() {
        return sellIn;
    }

     public Item getItem() {
        return this;
    }
}
