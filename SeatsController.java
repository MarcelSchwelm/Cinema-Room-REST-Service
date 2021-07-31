package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SeatsController {
    private static final int TOTAL_ROWS = 9;
    private static final int TOTAL_COLUMNS = 9;
    private final Cinema cinema = new Cinema(TOTAL_ROWS, TOTAL_COLUMNS);

    @GetMapping("/seats")
    public Cinema getCinema() {
        cinema.setSeatList(cinema.getAvailable_seats());
        return cinema;
    }

    @PostMapping("/purchase")
    @ResponseBody
    public ResponseEntity<Object> purchase(@RequestBody Seat seat) {
        try {
            Seat s = cinema.purchase(seat.getRow(), seat.getColumn());
            return new ResponseEntity<>(Map.of("token", s.getToken(), "ticket", s), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/return")
    @ResponseBody
    public ResponseEntity<Object> returnTicket(@RequestBody Map<String, String> token) {
        String inputToken = token.get("token");
        Seat seat = cinema.getSeat(inputToken);
        if (seat == null) {
            return new ResponseEntity<>(Map.of("error", "Wrong token!"),
                    HttpStatus.BAD_REQUEST);
        }
        seat.setAvailable(true);
        return new ResponseEntity<>(Map.of("returned_ticket", seat), HttpStatus.OK);
    }

    @PostMapping("/stats")
    public ResponseEntity<Object> returnStats(@RequestParam(required = false) String password) {
        if (password == null) {
            return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        if (password.equals("super_secret")) {
            return new ResponseEntity<>(Map.of(
                    "current_income", cinema.getCurrentIncome(),
                    "number_of_available_seats", cinema.getNumberOfAvailableSeats(),
                    "number_of_purchased_tickets", cinema.getNumberOfPurchasedTickets()), HttpStatus.OK);
        }
        return new ResponseEntity<>(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
    }

}