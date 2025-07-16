package com.gildedrose;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Gilded Rose project
 */
@DisplayName("The Gilded Rose")
class GildedRoseRevised4Test {

    GildedRoseRevised4 catalog;

    @BeforeEach
    void setUp() throws Exception {
        Item[] items = new Item[]{
                new AgedBrieItem(2, 0),  // Constructor matches (sellIn, quality)
                new SulfurasItem(0, 80),  // Constructor matches (sellIn, quality)
                new SulfurasItem(-1, 80), // Constructor matches (sellIn, quality)
                new BackstagePassesItem(15, 20),  // Constructor matches (sellIn, quality)
                new BackstagePassesItem(10, 49),  // Constructor matches (sellIn, quality)
                new BackstagePassesItem(5, 49),   // Constructor matches (sellIn, quality)
        };
        catalog = new GildedRoseRevised4(items);
    }

    @AfterEach
    void tearDown() throws Exception {
        catalog = null;
    }

    @Nested
    @DisplayName("When update Aged Brie")
    class TestCasesForBrie {

        @DisplayName("Aged Brie always increases its quality")
        @Test
        void givenAQualityValueAndPositiveSellInWhenUpdateThenQualityIncreasesByOne() {
            // Arrange
            int oldQuality;
            int newQuality;
            Item currentItem = null;
            // Find the first Aged Brie Item
            for (int index = 0; index < catalog.getItems().length; index++) {
                if (catalog.getItemAt(index) instanceof AgedBrieItem) {
                    currentItem = catalog.getItemAt(index);
                    break;
                }
            }

            // Act
            if (currentItem != null) {
                oldQuality = currentItem.getQuality();
                catalog.updateQuality();
                newQuality = currentItem.getQuality();

                // Assert
                assertTrue(newQuality > oldQuality, "Quality of Aged Brie should increase!");
            } else {
                fail("No Aged Brie items in the catalog");
            }
        }

        @DisplayName("Increases its quality by two if sellIn is zero")
        @Test
        void givenAQualityValueAndSellInIsZeroWhenUpdateThenQualityIncreasesByTwo() {
            // Arrange
            int quality = 10;
            int sellIn = 0;
            Item item = new AgedBrieItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality + 2, newQuality);
        }

        @DisplayName("Does not increase its quality if it is 50 and sellIn is 0 or positive")
        @Test
        void givenAQualityValueOf50WhenUpdateThenQualityRemains() {
            // Arrange
            int quality = 50;
            int sellIn = 0;
            Item item = new AgedBrieItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality, newQuality);
        }

        @DisplayName("Decreases sellIn by 1 when positive")
        @Test
        void givenAPositiveSellInValueWhenUpdateThenItsValueDecreasesByOne() {
            // Arrange
            int quality = 50;
            int sellIn = 2;
            Item item = new AgedBrieItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newSellIn = item.getSellIn();
            assertEquals(sellIn - 1, newSellIn);
        }

        @DisplayName("Decreases sellIn by 1 when zero")
        @Test
        void givenAZeroSellInValueWhenUpdateThenItsValueDecreasesByOne() {
            // Arrange
            int quality = 50;
            int sellIn = 0;
            Item item = new AgedBrieItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newSellIn = item.getSellIn();
            assertEquals(sellIn - 1, newSellIn);
        }
    }

    @Nested
    @DisplayName("When update a backstage pass")
    class TestCasesForBackstagePass {

        @DisplayName("Increases its quality by 1 when sellIn is greater than 10")
        @Test
        void givenSellInIsGreaterThan10WhenUpdateThenItsQualityIncreasesByOne() {
            // Arrange
            int quality = 20;
            int sellIn = 11;
            Item item = new BackstagePassesItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality + 1, newQuality);
        }

        @DisplayName("Increases its quality by 2 when sellIn is 10 or less, but greater than 5")
        @Test
        void givenSellInLowerThan10AndHigherThan5WhenUpdateThenItsQualityIncreasesByTwo() {
            // Arrange
            int quality = 20;
            int sellIn = 8;
            Item item = new BackstagePassesItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality + 2, newQuality);
        }

        @DisplayName("Increases its quality by 3 when sellIn is 5")
        @Test
        void givenSellInIs5WhenUpdateThenItsQualityIncreasesByThree() {
            // Arrange
            int quality = 20;
            int sellIn = 5;
            Item item = new BackstagePassesItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality + 3, newQuality);
        }

        @DisplayName("Its quality is zero when sellIn is zero")
        @Test
        void givenSellInIsZeroWhenUpdateThenItsQualityIsZero() {
            // Arrange
            int quality = 20;
            int sellIn = 0;
            Item item = new BackstagePassesItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(0, newQuality);
        }
    }

    @Nested
    @DisplayName("When update Sulfuras")
    class TestCasesForSulfuras {

        @DisplayName("Its quality does not decrease")
        @Test
        void whenUpdateThenItsQualityRemainsTheSame() {
            // Arrange
            int quality = 80;
            int sellIn = 4;
            Item item = new SulfurasItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality, newQuality);
        }

        @DisplayName("Its sellIn value remains the same")
        @Test
        void whenUpdateThenItsSellInValueRemainsTheSame() {
            // Arrange
            int quality = 80;
            int sellIn = 4;
            Item item = new SulfurasItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newSellIn = item.getSellIn();
            assertEquals(sellIn, newSellIn);
        }
    }

    @Nested
    @DisplayName("When update a regular item")
    class TestCasesForRegularItems {

        @DisplayName("Decreases its quality by 1 when sellIn is greater than zero")
        @Test
        void givenAQualityValueAndPositiveSellInWhenUpdateThenQualityDecreasesByOne() {
            // Arrange
            int quality = 10;
            int sellIn = 2;
            Item item = new RegularItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality - 1, newQuality);
        }

        @DisplayName("Decreases its quality by 2 when sellIn is zero")
        @Test
        void givenAQualityValueAndSellInIsZeroWhenUpdateThenQualityDecreasesByTwo() {
            // Arrange
            int quality = 10;
            int sellIn = 0;
            Item item = new RegularItem(sellIn, quality);  // Corrected constructor usage
            GildedRoseRevised4 gildedRose = new GildedRoseRevised4(new Item[]{item});

            // Act
            gildedRose.updateQuality();

            // Assert
            int newQuality = item.getQuality();
            assertEquals(quality - 2, newQuality);
        }
    }
}
