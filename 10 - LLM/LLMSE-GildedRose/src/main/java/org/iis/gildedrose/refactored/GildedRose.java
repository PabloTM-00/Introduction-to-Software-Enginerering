package org.iis.gildedrose.refactored;

import java.util.HashMap;
import java.util.Map;

// Main GildedRose logic
class GildedRose {
  private final Item[] items;
  private final Map<String, ItemUpdater> updaterMap;

  public GildedRose(Item[] items) {
    this.items = items;
    this.updaterMap = new HashMap<>();

    // Register all item-specific updaters
    updaterMap.put("Aged Brie", new AgedBrieUpdater());
    updaterMap.put("Backstage passes to a TAFKAL80ETC concert", new BackstagePassUpdater());
    updaterMap.put("Sulfuras, Hand of Ragnaros", new SulfurasUpdater());
  }

  public void updateQuality() {
    for (Item item : items) {
      ItemUpdater updater = updaterMap.getOrDefault(item.name, new StandardItemUpdater());
      updater.update(item);
    }
  }
}

// Interface for item update strategy
interface ItemUpdater {
  void update(Item item);
}

// Handles normal items
class StandardItemUpdater implements ItemUpdater {
  @Override
  public void update(Item item) {
    decreaseQuality(item);
    item.sellIn--;

    if (item.sellIn < 0) {
      decreaseQuality(item);
    }
  }

  protected void decreaseQuality(Item item) {
    if (item.quality > 0) {
      item.quality--;
    }
  }
}

// Handles "Aged Brie" behavior
class AgedBrieUpdater implements ItemUpdater {
  @Override
  public void update(Item item) {
    increaseQuality(item);
    item.sellIn--;

    if (item.sellIn < 0) {
      increaseQuality(item);
    }
  }

  protected void increaseQuality(Item item) {
    if (item.quality < 50) {
      item.quality++;
    }
  }
}

// Handles "Backstage passes" behavior
class BackstagePassUpdater implements ItemUpdater {
  @Override
  public void update(Item item) {
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

  protected void increaseQuality(Item item) {
    if (item.quality < 50) {
      item.quality++;
    }
  }
}

// Handles "Sulfuras", which never changes
class SulfurasUpdater implements ItemUpdater {
  @Override
  public void update(Item item) {
    // Legendary item; do nothing
  }
}
