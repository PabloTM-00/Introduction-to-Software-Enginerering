package com.gildedrose;

public class GildedRose implements GildedRoseInterface {
    private ItemInterface[] items;

    public GildedRose(ItemInterface[] items) {
        this.items = items;
    }

    @Override
    public ItemInterface[] getItems() {
        return items;
    }

    @Override
    public void setItems(ItemInterface[] items) {
        this.items = items;
    }

    public void updateQuality() {
    for (ItemInterface item : getItems()) {  
        item.updateQuality();
    }
}
}
