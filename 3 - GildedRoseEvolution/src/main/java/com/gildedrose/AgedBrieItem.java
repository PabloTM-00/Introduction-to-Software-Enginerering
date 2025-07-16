package com.gildedrose;

public class AgedBrieItem extends Item {

     public AgedBrieItem(String name, int sellIn, int quality) {
    super(name, sellIn, quality);
}

    @Override
    public void updateQuality() {
        if (sellIn > 0) {
            quality++;
        } else {
            quality += 2;
        }

         if (quality > 50) {
            quality = 50;
        }
        sellIn--;  
    }

    @Override
    public String toString() {
        return "Aged Brie, SellIn: " + sellIn + ", Quality: " + quality;
    }
}
