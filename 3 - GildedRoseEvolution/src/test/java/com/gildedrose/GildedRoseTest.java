package com.gildedrose;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The Gilded Rose")
class GildedRoseTest {

    GildedRose catalog;

    @BeforeEach
    void setUp() throws Exception {
        ItemInterface[] items = new ItemInterface[]{
                new Item(GildedRose.AGED_BRIE, 2, 0),
                new Item(GildedRose.SULFURAS, 0, 80),
                new Item(GildedRose.SULFURAS, -1, 80),
                new Item(GildedRose.BACKSTAGE_PASS, 15, 20),
                new Item(GildedRose.BACKSTAGE_PASS, 10, 49),
                new Item(GildedRose.BACKSTAGE_PASS, 5, 49),
        };
        catalog = new GildedRose(items);
    }

    @AfterEach
    void tearDown() throws Exception {
        catalog = null;
    }

    @Nested
    @DisplayName("When updating Aged Brie")
    class TestCasesForAgedBrie {

        @DisplayName("Increases its quality by one if sellIn is greater than zero")
        @Test
        void givenAQualityValueAndPositiveSellInWhenUpdateThenQualityIncreasesByOne() {
            int index = 0;
            ItemInterface currentItem = null;

            while (index < catalog.getItems().length && !catalog.getItems()[index].getName().equals(GildedRose.AGED_BRIE)) {
                index++;
            }

            if (index < catalog.getItems().length) {
                currentItem = catalog.getItems()[index];
                while (currentItem.getSellIn() > -3) {
                    int oldQuality = currentItem.getQuality();
                    catalog.updateQuality();
                    int newQuality = currentItem.getQuality();

                    assertTrue(newQuality > oldQuality, "Quality of Aged Brie should increase!");
                }
            } else {
                fail("No Aged Brie items in the catalog");
            }
        }

        @DisplayName("Increases its quality by two if sellIn is zero")
        @Test
        void givenAQualityValueAndSellInIsZeroWhenUpdateThenQualityIncreasesByTwo() {
            int quality = 10;
            int sellIn = 0;
            Item item = new Item(GildedRose.AGED_BRIE, sellIn, quality);
            GildedRose gildedRose = new GildedRose(new ItemInterface[]{item});

            gildedRose.updateQuality();

            int newQuality = item.getQuality();
            assertEquals(quality + 2, newQuality);
        }

        @DisplayName("Does not increase its quality if it is 50 and sellIn is 0")
        @Test
        void givenAQualityValueOf50AndSellInIsZeroWhenUpdateThenQualityRemainsTheSame() {
            int quality = 50;
            int sellIn = 0;
            Item item = new Item(GildedRose.AGED_BRIE, sellIn, quality);
            GildedRose gildedRose = new GildedRose(new ItemInterface[]{item});

            gildedRose.updateQuality();

            int newQuality = item.getQuality();
            assertEquals(quality, newQuality);
        }

        @DisplayName("Decreases sellIn when it's positive")
        @Test
        void givenAPositiveSellInValueWhenUpdateThenItsValueDecreasesByOne() {
            int quality = 50;
            int sellIn = 2;
            Item item = new Item(GildedRose.AGED_BRIE, sellIn, quality);
            GildedRose gildedRose = new GildedRose(new ItemInterface[]{item});

            gildedRose.updateQuality();

            int newSellIn = item.getSellIn();
            assertEquals(sellIn - 1, newSellIn);
        }
    }
}
