package kz.greetgo.diploma.controller.register.model;

public class Item {

	public int itemId;

	public String name;

	public String description;

	public String image;

	public float price;

	@Override
	public String toString() {

		return "Item{" +
			"itemId=" + itemId +
			", name='" + name + '\'' +
			", description='" + description + '\'' +
			", image='" + image + '\'' +
			", price=" + price +
			'}';
	}
}
