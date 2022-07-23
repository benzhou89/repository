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
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 37.5);  //there is no any discount
        
        showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                1,
                LocalDateTime.of(2022,7,22,17,00)
        );
        System.out.println(new Reservation(customer, showing, 3).totalFee());
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 28.5);  //there is $3  discount
        
        showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                2,
                LocalDateTime.of(2022,7,22,17,00)
        );
        System.out.println(new Reservation(customer, showing, 3).totalFee());
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 31.5);  //there is $2  discount
        
        showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                2,
                LocalDateTime.of(2022,7,22,17,00)
        );
        System.out.println(new Reservation(customer, showing, 3).totalFee());
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 30);  //there is 20%  discount
        
        showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 0),
                4,
                LocalDateTime.of(2022,7,7,17,00)
        );
        System.out.println(new Reservation(customer, showing, 3).totalFee());
        assertTrue(new Reservation(customer, showing, 3).totalFee() == 34.5);  //there is $1  discount
        
    }
}
