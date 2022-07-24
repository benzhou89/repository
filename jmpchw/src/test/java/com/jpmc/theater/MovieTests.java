package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTests {
    @Test
    void specialMovieWithDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1);
        LocalDate date = LocalDate.of(2022,7,22);
        LocalTime time = LocalTime.of(12, 00); //
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(date, time));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm  (has 25% and 20% rules, apply 25% rule)

        time = LocalTime.of(16, 00); //
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm, 4pm included (has 25% and 20% rules, apply 25% rule)
        
        time = LocalTime.of(11, 00); //
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% due to between 11-4pm, 11am included (has 25% and 20% rules, apply 25% rule)
        
        
        showing = new Showing(spiderMan, 1, LocalDateTime.of(date, time));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //the 25% discount > $3 discount  (has 25% , 20% and $3 rules, apply 25% rule)
        
        showing = new Showing(spiderMan, 2, LocalDateTime.of(date, time));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));  //max discount is 25% > $2 due to between 11-4pm (has 25% , 20% and $2 rules, apply 25% rule)
        
        time = LocalTime.of(16, 10); //
        showing = new Showing(spiderMan, 2, LocalDateTime.of(date, time));
        assertEquals(16, spiderMan.calculateTicketPrice(showing));  //max discount is 20% > $2 due to special movie
        
        date = LocalDate.of(2022,7,7);
        showing = new Showing(spiderMan, 5, LocalDateTime.of(date, time));
        assertEquals(16, spiderMan.calculateTicketPrice(showing));  //max discount is 20% > $1 due to special movie, have 2 saving 20% and $1
        
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
        assertEquals(8, testMovie.calculateTicketPrice(showing));  //there is $2 discount due to on 7th of each month (has $2 and $1 discount, since $2 >$1, apply $2)
        
        Movie testMovie2 = new Movie("Test Moive2", Duration.ofMinutes(90), 5, 0);
        date = LocalDate.of(2022,7,8);
        time = LocalTime.of(12, 00);
        showing = new Showing(testMovie2, 1, LocalDateTime.of(date, time));
        assertEquals(2, testMovie2.calculateTicketPrice(showing));  //there is $3 discount (25% rule and $3 rules, apply $3 one)
        
        showing = new Showing(testMovie2, 2, LocalDateTime.of(date, time));
        assertEquals(3, testMovie2.calculateTicketPrice(showing));  //there is $3 discount (25% rule and $2 rules, apply $2 one)
        
        date = LocalDate.of(2022,7,7);
        showing = new Showing(testMovie2, 2, LocalDateTime.of(date, time));
        assertEquals(3, testMovie2.calculateTicketPrice(showing));  //there is $1 discount (25% , $2 and $1 rules, apply $2 one)
        
        date = LocalDate.of(2022,7,7);
        showing = new Showing(testMovie2, 3, LocalDateTime.of(date, time));
        assertEquals(3.75, testMovie2.calculateTicketPrice(showing));  //there is $1 discount (25% and $1 rules, apply 25% one)
        
        time = LocalTime.of(10, 50);
        showing = new Showing(testMovie2, 3, LocalDateTime.of(date, time));
        assertEquals(4, testMovie2.calculateTicketPrice(showing));  //there is $1 discount (no 25% discount due to before 11am
        
        System.out.println(Duration.ofMinutes(90));
    }
    
    @Test
    void test_equal() {
    	Movie spiderMan1 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1);
    	Movie spiderMan2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1);
    	assertTrue(spiderMan1.equals(spiderMan1));
    	assertTrue(spiderMan1.equals(spiderMan2));
    	
		assertTrue(spiderMan1.hashCode()==spiderMan2.hashCode());
		
    	
    	spiderMan2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 2);
    	assertFalse(spiderMan1.equals(spiderMan2));
    	
    	spiderMan2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 18, 1);
    	assertFalse(spiderMan1.equals(spiderMan2));
    	assertFalse(spiderMan1.hashCode()==spiderMan2.hashCode());
    	
    	spiderMan2 = new Movie("Spider-Man: No Way Home2", Duration.ofMinutes(90), 20, 1);
    	assertFalse(spiderMan1.equals(spiderMan2));
    	
    	spiderMan2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(100), 20, 1);
    	assertFalse(spiderMan1.equals(spiderMan2));
    	
    	spiderMan2 = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 20, 1, "this is spider man");
    	assertFalse(spiderMan1.equals(spiderMan2));
    	
    	assertFalse(spiderMan1.equals(null));
    	assertFalse(spiderMan1.equals(new Object()));
    }
}
