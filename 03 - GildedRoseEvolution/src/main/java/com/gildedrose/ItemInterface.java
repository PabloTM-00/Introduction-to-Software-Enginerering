package com.gildedrose;

public interface ItemInterface {

	String getName();

	int getQuality();
	
	int getSellIn();

	void updateQuality();

	Item getItem();
}