package com.gildedrose;

public class GildedRoseRevised4 implements GildedRoseInterface {
	ItemInterface[] items;

    // Constructor
    public GildedRoseRevised4(ItemInterface[] items2) {
        this.items = items2;
    }
	@Override
    public ItemInterface[] getItems() {
		return items;
	}
	@Override
	public void setItems(ItemInterface[] items) {
		this.items = items;
	}
	
    public ItemInterface getItemAt(int index) {
    	if (index<items.length) {
    		return items[index];
    	} else {
    		return null;
    	}
	}
    
    public void updateQuality() {
    if (items == null) return; 
    for (ItemInterface currentItem : items) {  
        currentItem.updateQuality();
    }
}

}


