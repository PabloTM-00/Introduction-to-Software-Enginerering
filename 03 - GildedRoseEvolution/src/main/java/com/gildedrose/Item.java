public class Item implements ItemInterface {
    private String name;
    private int sellIn;
    private int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuality() {
        return quality;
    }

    @Override
    public int getSellIn() {
        return sellIn;
    }

    @Override
    public void updateQuality() {
        // Implementa lógica para actualizar la calidad según las reglas del negocio
    }

    @Override
    public Item getItem() {
        return this;
    }
}
