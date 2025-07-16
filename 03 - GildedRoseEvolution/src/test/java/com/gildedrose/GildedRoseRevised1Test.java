package com.gildedrose;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GildedRoseRevised1Test {

    GildedRoseRevised1 catalog;

    @BeforeEach
    void setUp() throws Exception {
        // Use concrete subclasses of Item (not abstract Item)
        Item[] items = new Item[] {
            new AgedBrieItem("Aged Brie", 2, 0), // Using concrete subclass AgedBrieItem
            new SulfurasItem("Sulfuras, Hand of Ragnaros", 0, 80), // Using concrete subclass SulfurasItem
            new SulfurasItem("Sulfuras, Hand of Ragnaros", -1, 80), // Using concrete subclass SulfurasItem
            new BackstagePassesItem(15, 20), // Using concrete subclass BackstagePassesItem
            new BackstagePassesItem(10, 49), // Using concrete subclass BackstagePassesItem
            new BackstagePassesItem(5, 49), // Using concrete subclass BackstagePassesItem
        };

        catalog = new GildedRoseRevised1(items);
    }

    @AfterEach
    void tearDown() throws Exception {
        catalog = null;
    }

    @Test
    /**
     * Verifies the increase in quality of Aged Brie even after the sellIn date.
     */
    void testAgedBrieActuallyIncreasesInQualityTheOlderItGets() {
        int oldQuality;
        int newQuality;
        int index = 0;
        Item currentItem = null;

        // find an Aged Brie Item
        while ((index < catalog.getItems().length) && 
               (!catalog.isAgedBrie(catalog.getItemAt(index)))) {
            index++;
        }
        if (index < catalog.getItems().length) { // we found an Aged Brie Item
            currentItem = catalog.getItemAt(index);
            while (currentItem.sellIn > -3) {
                oldQuality = currentItem.quality;
                catalog.updateQuality();
                newQuality = currentItem.quality;

                assertTrue(newQuality > oldQuality, "Quality of Aged Brie should increase!");
            }
        } else {
            fail("no Aged Brie items in the catalog");
        }
    }
}
