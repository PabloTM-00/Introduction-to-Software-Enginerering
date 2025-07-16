package com.gildedrose;

public class ItemAdapter implements ItemInterface {
    private final Item item;

    public ItemAdapter(Item item) {
        this.item = item;
    }

    @Override
    public String getName() {
        return item.name;
    }

    @Override
    public int getQuality() {
        return item.quality;
    }

    @Override
    public int getSellIn() {
        return item.sellIn;
    }

    @Override
    public void updateQuality() {
        item.updateQuality();
    }

    @Override
    public Item getItem() {
        return item;
    }
}
