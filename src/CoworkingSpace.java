import java.util.*;

class CoworkingSpace {
    private int id;
    private String type;
    private double price;
    private boolean isAvailable;

    public CoworkingSpace(int id, String type, double price, boolean isAvailable) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Price: $" + price + ", Available: " + (isAvailable ? "Yes" : "No");
    }
}

class Booking {
    private int id;
    private String customerName;
    private int workspaceId;
    private String date;
    private String time;

    public Booking(int id, String customerName, int workspaceId, String date, String time) {
        this.id = id;
        this.customerName = customerName;
        this.workspaceId = workspaceId;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    @Override
    public String toString() {
        return "Booking ID: " + id + ", Customer: " + customerName + ", Workspace ID: " + workspaceId + ", Date: " + date + ", Time: " + time;
    }
}

