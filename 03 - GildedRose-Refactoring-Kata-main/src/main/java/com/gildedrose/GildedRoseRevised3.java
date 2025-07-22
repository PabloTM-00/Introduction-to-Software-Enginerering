package com.gildedrose;

public class GildedRoseRevised3 {
     static final String AGED_BRIE = "Aged Brie";
	private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
	private static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
	Item[] items;

    // Constructor
    public GildedRoseRevised3(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
		return items;
	}

	public void setItems(Item[] items) {
		this.items = items;
	}
    
    public void updateQuality() {
    for (Item currentItem : items) {
        switch (currentItem.name) {
            case AGED_BRIE:
                updateAgedBrieQuality(currentItem);
                break;
            case BACKSTAGE_PASSES:
                updateBackstagePassesQuality(currentItem);
                currentItem.sellIn--;
                break;
            case SULFURAS: //Nothing for sulfuras
                 break;
            default:
                decreaseQuality(currentItem);
                currentItem.sellIn--;
                if (currentItem.sellIn < 0) {
                    decreaseQuality(currentItem);
                }
                break;
        }
    }
}


	private void updateBackstagePassesQuality(Item item) {
		increaseQuality(item);
		if (item.sellIn < 11) {
		    increaseQuality(item);
		}
		if (item.sellIn < 6) {
		    increaseQuality(item);
		}
		// Decrease sellIn (for all items except Sulfuras)
		item.sellIn--;
		// Quality degradation after sellIn date
		// Backstage passes lose all quality after concert
		if (item.sellIn < 0) {
			item.quality = 0;
		}
	}

	private void updateAgedBrieQuality(Item item) {
		increaseQuality(item);
		// Decrease sellIn (done for all items except Sulfuras)
		item.sellIn--;
		// Quality degradation after sellIn date
		// Aged Brie increases quality even after sellIn
		if (item.sellIn < 0) {
		        increaseQuality(item);
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
