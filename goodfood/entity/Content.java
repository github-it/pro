public class Content extends Entity {
	private Food food;
	private Property property;
	private int price;
	private int quantityAll;
	
	public Food getFood() {
		return food;
    }

    public void setFood(Food food) {
        this.food = food;
	
	public Property getProperty() {
		return property;
    }

    public void setProperty(Property property) {
        this.property = property;
	
	public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
  
	public int getQuantityAll() {
        return quantityAll;
    }

    public void setQuantityAll(int quantityAll) {
        this.quantityAll = quantityAll;
    }

}