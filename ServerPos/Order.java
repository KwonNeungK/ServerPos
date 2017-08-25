package ServerPos;
import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
	

	private String menu;
	
	private int quantity;
	
	private int price;
	
	private ArrayList<Order> subMenu = new ArrayList<Order>();
	
	
	public Order(String menu, int quantity, int price) {
		this.menu = menu;
		this.quantity = quantity;
		this.price = price;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getTotalPrice() {
		return getPrice() * getQuantity();
	}
	
	public String printPrice() {
		return this.menu + " " + getPrice() + "¿ø";
	}

	public ArrayList<Order> getSubMenu() {
		return subMenu;
	}
	
	
}