package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

public class Seat {

    private int row;
    private int column;
    private int price;
    @JsonIgnore
    private boolean available;
    @JsonIgnore
    private String token;

    public Seat(){}

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = this.row <= 4 ? 10 : 8;
        this.available = true;
        this.token = getTokenAtInit();
    }

    private String getTokenAtInit() {
        return UUID.randomUUID().toString();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}