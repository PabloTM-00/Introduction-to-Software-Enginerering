package com.gildedrose;

public class SulfurasItem extends Item {

    public SulfurasItem(int sellIn, int quality) {
    super("Sulfuras, Hand of Ragnaros", sellIn, quality);
}

    @Override
    public void updateQuality() {
        // Sulfuras never changes its quality
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
