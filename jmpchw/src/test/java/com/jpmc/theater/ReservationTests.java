package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {

    @Test
    void totalFee() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.of(2022,7,22,15,00)
        );
        System.out.println(new Reservation(customer, showing, 3).totalFee());
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 28.13);  //save 25% and round to 28.13
        
        showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                3,
                LocalDateTime.of(2022,7,22,17,00)
        );
        System.out.println(new Reservation(customer, showing, 3).totalFee());
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 37.5);
    }
}
