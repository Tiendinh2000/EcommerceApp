package hanu.a2_1801040189.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Product {
    private int id;
    private String name;
    private String image;
    private int price;
private int quantity;

public Product(){}

    public Product(int id, String name, String image,  int price) {
        this.name = name;
        this.id=id;
        this.price=price;
        this.image=image;

    }

    public Product(int id, String name, String image,  int price,int quantity){
        this.name = name;
        this.id=id;
        this.price=price;
        this.image=image;

        this.quantity= quantity;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
