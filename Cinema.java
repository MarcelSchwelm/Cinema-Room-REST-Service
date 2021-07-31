package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    @JsonProperty("total_rows")
    private int totalRows;
    @JsonProperty("total_columns")
    private int totalColumns;

    List<Seat> available_seats;
    @JsonIgnore
    private List<Seat> seatList;
    private static final String ERROR_INVALID_SEAT_PARAMETERS = "The number of a row or a column is out of bounds!";
    private static final String ERROR_TICKET_PURCHASED = "The ticket has been already purchased!";

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        seatList = new ArrayList<>(totalRows * totalColumns);
        for (int row = 1; row <= totalRows; row++)
            for (int column = 1; column <= totalColumns; column++) {
                seatList.add(new Seat(row, column));
            }
    }

    public Seat getSeat(String inputToken) {
        for (Seat seat : seatList) {
            if (seat.getToken().equals(inputToken)) {
                return seat;
            }
        }
        return null;
    }

    public Seat purchase(int row, int column) throws IllegalArgumentException {
        if ((row < 1) || (row > totalRows) || (column < 1) || (column > totalColumns))
            throw new IllegalArgumentException(ERROR_INVALID_SEAT_PARAMETERS);

        for (Seat s : seatList) {
            if ((s.getRow() == row) && (s.getColumn() == column) && (s.isAvailable())) {
                s.setAvailable(false);
                return s;
            }
        }
        throw new IllegalArgumentException(ERROR_TICKET_PURCHASED);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<Seat> getAvailable_seats() {

        available_seats = new ArrayList<>();
        for (Seat s : seatList) {
            if (s.isAvailable()) {
                available_seats.add(s);
            }
        }
        return available_seats;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    @JsonIgnore
    public int getCurrentIncome() {
        int income = 0;
        for (Seat seat : seatList) {
            if (!seat.isAvailable()) {
                income += seat.getPrice();
            }
        }
        return income;
    }

    @JsonIgnore
    public int getNumberOfAvailableSeats() {
        return getAvailable_seats().size();
    }


    @JsonIgnore
    public int getNumberOfPurchasedTickets() {
        return seatList.size() - getNumberOfAvailableSeats();
    }
}