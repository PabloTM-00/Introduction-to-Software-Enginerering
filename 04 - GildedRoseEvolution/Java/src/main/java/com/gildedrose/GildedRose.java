package com.gildedrose;

class GildedRose {
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert"; //Create constants for the strings
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String AGED_BRIE = "Aged Brie";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    // Helper methods for strings, quality etc
    private boolean isSulfuras(Item item) {
        return item.name.equals(SULFURAS);
    }

    private boolean isBackstage(Item item) {
        return item.name.equals(BACKSTAGE_PASSES);
    }

    private boolean isAgedBrie(Item item) {
        return item.name.equals(AGED_BRIE);
    }

    private boolean hasExpired(Item item) {
        return item.sellIn < 0;
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    public void updateQuality() {
        for (Item item : items) {
            if (isSulfuras(item)) {
                continue; // Sulfuras doesn't change
            }

            handleItemUpdate(item);
            item.sellIn--;

            if (hasExpired(item)) {
                handleExpiredItem(item);
            }
        }
    }

    private void handleItemUpdate(Item item) {
        if (isAgedBrie(item)) {
            increaseQuality(item);
        } else if (isBackstage(item)) {
            handleBackstageUpdate(item);
        } else {
            decreaseQuality(item); // normal item
        }
    }

    private void handleBackstageUpdate(Item item) {
        increaseQuality(item);

        if (item.sellIn < 11) {
            increaseQuality(item);
        }
        if (item.sellIn < 6) {
            increaseQuality(item);
        }
    }

    private void handleExpiredItem(Item item) {
        if (isAgedBrie(item)) {
            increaseQuality(item);
        } else if (isBackstage(item)) {
            item.quality = 0;
        } else {
            decreaseQuality(item);
        }
    }

}
