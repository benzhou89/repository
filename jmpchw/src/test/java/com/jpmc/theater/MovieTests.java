package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    @Test
    void specialMovieWithDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1);
        LocalDate date = LocalDate.of(2022,7,22);
        LocalTime time = LocalTime.of(12, 00); //
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(date, time));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm

        time = LocalTime.of(16, 00); //
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm, 4pm included
        
        time = LocalTime.of(11, 00); //
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm, 11am included
        
        
        showing = new Showing(spiderMan, 1, LocalDateTime.of(date, time));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //the 25% discount > $3 discount
        
        showing = new Showing(spiderMan, 2, LocalDateTime.of(date, time));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm
        
        time = LocalTime.of(16, 10); //
        showing = new Showing(spiderMan, 2, LocalDateTime.of(date, time));
        assertEquals(16, spiderMan.calculateTicketPrice(showing));  //max discount is 20% due to special movie
        
        date = LocalDate.of(2022,7,7);
        showing = new Showing(spiderMan, 5, LocalDateTime.of(date, time));
        assertEquals(16, spiderMan.calculateTicketPrice(showing));  //max discount is 20% due to special movie
        
        Movie testMovie = new Movie("Test Moive", Duration.ofMinutes(90), 10, 0);
        
        date = LocalDate.of(2022,7,8);
        time = LocalTime.of(12, 00);
        showing = new Showing(testMovie, 5, LocalDateTime.of(date, time));
        assertEquals(7.5, testMovie.calculateTicketPrice(showing));  //there is 25% discount
        
        showing = new Showing(testMovie, 1, LocalDateTime.of(date, time));
        assertEquals(7, testMovie.calculateTicketPrice(showing));  //there is $3 > 25% discount
        
        date = LocalDate.of(2022,7,8);
        time = LocalTime.of(17, 00);
        showing = new Showing(testMovie, 5, LocalDateTime.of(date, time));
        assertEquals(10, testMovie.calculateTicketPrice(showing));  //there is no discount
        
        date = LocalDate.of(2022,7,7);
        showing = new Showing(testMovie, 5, LocalDateTime.of(date, time));
        assertEquals(9, testMovie.calculateTicketPrice(showing));  //there is $1 discount due to on 7th of each month 
        
        showing = new Showing(testMovie, 2, LocalDateTime.of(date, time));
        assertEquals(8, testMovie.calculateTicketPrice(showing));  //there is $2 discount due to on 7th of each month 
        
        System.out.println(Duration.ofMinutes(90));
    }
}
