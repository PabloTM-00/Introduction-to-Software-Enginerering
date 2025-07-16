package com.gildedrose;

public class Item implements ItemInterface {
    String name;
    int sellIn;
    int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

     public String getName() {
        return name;
    }

     public int getQuality() {
        return quality;
    }

     public void setQuality(int quality) {
        this.quality = quality;
    }

     public int getSellIn() {
        return sellIn;
    }

     public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

     public void updateQuality() {
     }

     public void increaseQuality(ItemInterface item) {
    if (item.getQuality() < 50) {
        item.setQuality(item.getQuality() + 1);
    }
}

public void decreaseQuality(ItemInterface item) {
    if (item.getQuality() > 0) {
        item.setQuality(item.getQuality() - 1);
    }
}

}
