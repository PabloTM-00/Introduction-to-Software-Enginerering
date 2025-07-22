package com.gildedrose;

public class GildedRoseRevised2 {
    private static final String AGED_BRIE = "Aged Brie";
	private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
	private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
	Item[] items;

    // Constructor
    public GildedRoseRevised2(Item[] items) {
        this.items = items;
    }
    
    public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}

    public void updateQuality() {
    for (Item item : items) {
        switch (item.name) {
            case AGED_BRIE:
                updateAgedBrie(item);
                break;
            case BACKSTAGE_PASSES:
                updateBackstagePasses(item);
                break;
            case SULFURAS:
                updateSulfuras(item);
                break;
            default:
                updateDefaultItem(item);
                break;
        }
    }
}


private void updateAgedBrie(Item item) {
    increaseQuality(item);
    item.sellIn--;
    if (item.sellIn < 0) {
        increaseQuality(item);
    }
}

private void updateBackstagePasses(Item item) {
    increaseQuality(item);
    if (item.sellIn < 11) {
        increaseQuality(item);
    }
    if (item.sellIn < 6) {
        increaseQuality(item);
    }
    item.sellIn--;
    if (item.sellIn < 0) {
        item.quality = 0;
    }
}

private void updateSulfuras(Item item) {
    // Sulfuras doesn't change in quality, so no need to alter it
    item.sellIn--;
}

private void updateDefaultItem(Item item) {
    decreaseQuality(item);
    item.sellIn--;
    if (item.sellIn < 0) {
        decreaseQuality(item);
    }
}


    // Increase quality ensuring it doesn't go beyond 50
    public void increaseQuality(Item item) {
        if (item.quality < 50) {
            item.quality++;
        }
    }

    // Decrease quality ensuring it doesn't go below 0
    public void decreaseQuality(Item item) {
        if (item.quality > 0) {
            item.quality--;
        }
    }

    public Item getItemAt(int index) {
    	if (index<items.length) {
    		return items[index];    		
    	} else {
    		return null;    		
    	}
	}
	
    // Check if the item is Aged Brie
	public boolean isAgedBrie(Item item) {
        return item.name.equals(AGED_BRIE);
    }
}
