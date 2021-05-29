package hanu.a2_1801040189.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String image;
    private int price;
    private String category;
    private int quantity;
    private String description;

    public Product(int id, String name, String image, int price) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.image = image;
    }

    public Product(int id, String name, String image, int price, int quantity) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
    }

    public Product(int id, String name, String image,String category, int price, String description) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.image = image;
        this.category=category;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
