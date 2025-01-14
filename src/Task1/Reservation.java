package Task1;

class Reservation {
    int reservationId;
    int spaceId;
    String customerName;
    String startTime;
    String endTime;

    public Reservation(int reservationId, int spaceId, String customerName, String startTime, String endTime) {
        this.reservationId = reservationId;
        this.spaceId = spaceId;
        this.customerName = customerName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Space ID: " + spaceId + ", Customer: " + customerName + ", Start: " + startTime + ", End: " + endTime;
    }
}
