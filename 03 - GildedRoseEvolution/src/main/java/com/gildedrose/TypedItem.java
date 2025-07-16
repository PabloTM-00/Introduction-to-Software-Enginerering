package com.gildedrose;

import java.util.logging.Logger;

public abstract class TypedItem extends Item implements ItemInterface {

    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String REGULAR = "Regular";

    private static final Logger logger = Logger.getLogger(TypedItem.class.getName());

    // Constructor for TypedItem
    protected TypedItem(String newName, int newSellIn, int newQuality) {
        super(newName, newSellIn, newQuality); // Calling the constructor of Item
        logger.info("TypedItem Constructor: " + newName + ", SellIn: " + newSellIn + ", Quality: " + newQuality);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuality() {
        return quality;
    }

    @Override
    public int getSellIn() {
        return sellIn;
    }

    public void increaseQuality() {
        if (quality < 50) {
            quality++;
        }
    }

    public void decreaseQuality() {
        if (quality > 0) {
            quality--;
        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
}
