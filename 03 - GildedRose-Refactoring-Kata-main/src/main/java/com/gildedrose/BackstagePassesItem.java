package com.gildedrose;

public class BackstagePassesItem extends TypedItem {

    public BackstagePassesItem(int newSellIn, int newQuality) {
        super(BACKSTAGE_PASSES, newSellIn, newQuality);
    }

    @Override
    public void updateQuality() {
        if (sellIn > 10) {
            increaseQuality();
        } else if (sellIn > 5) {
            increaseQuality();
            increaseQuality();
        } else if (sellIn > 0) {
            increaseQuality();
            increaseQuality();
            increaseQuality();
        } else {
            quality = 0; // After the concert, quality becomes 0
        }

        sellIn--; // Decrease sellIn

        if (quality > 50) {
            quality = 50; // Ensure the quality does not exceed 50
        }
    }

    @Override
    public Item getItem() {
        return this; // Returning the current instance of BackstagePassesItem
    }

    @Override
    public String toString() {
        return "Backstage Passes, SellIn: " + sellIn + ", Quality: " + quality;
    }
}
