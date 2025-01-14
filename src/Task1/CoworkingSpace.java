package Task1;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

class CoworkingSpace {
    int id;
    String type;
    double price;
    boolean isAvailable;
    Map<LocalDate, List<String>> availableDateTimeSlots;

    public CoworkingSpace(int id, String type, double price, Map<LocalDate, List<String>> availableDateTimeSlots) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.availableDateTimeSlots = availableDateTimeSlots;
        this.isAvailable = !availableDateTimeSlots.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder slotsInfo = new StringBuilder();
        availableDateTimeSlots.forEach((date, slots) ->
                slotsInfo.append(date).append(": ").append(slots).append("\n")
        );

        return "ID: " + id + ", Type: " + type + ", Price: " + price + ", Available: " + isAvailable +
                "\nAvailable Slots:\n" + slotsInfo;
    }

}
