package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TheaterTests {
	@Test
	void totalFeeForCustomer() {
		Theater theater = new Theater(LocalDateProvider.singleton());
		Customer john = new Customer("John Doe", "id-12345");
		Reservation reservation = theater.reserve(john, 2, 4, LocalDate.now());
//        System.out.println("You have to pay " + reservation.getTotalFee());
		assertEquals(reservation.totalFee(), 37.5);

		//Since there is no day2, the reservation is null
		reservation = theater.reserve(john, 2, 4, LocalDate.now().plusDays(2));
//      System.out.println("You have to pay " + reservation.getTotalFee());
		assertNull(reservation);

	}

	@Test
	void updateSchedule() {
		LocalDate datePlus2 = LocalDate.now().plusDays(2);
        Movie movie1 = new Movie("movie1", Duration.ofMinutes(90), 12.5, 1);
        Movie movie2 = new Movie("movie2", Duration.ofMinutes(85), 11, 0);
        Movie movie3 = new Movie("movie3", Duration.ofMinutes(95), 9, 0);
        List<Showing> schedule1 = new ArrayList<>(4); 
        schedule1.add(new Showing(movie1, 1, LocalDateTime.of(datePlus2, LocalTime.of(9, 0))));
        schedule1.add(new Showing(movie2, 2, LocalDateTime.of(datePlus2, LocalTime.of(11, 0))));
        schedule1.add(new Showing(movie3, 3, LocalDateTime.of(datePlus2, LocalTime.of(12, 50))));

        
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        theater.updateSchedule(datePlus2, schedule1);
        Reservation reservation = theater.reserve(john, 2, 4, LocalDate.now().plusDays(2));
        assertNotNull(reservation);
        
        Movie movie4 = new Movie("movie4", Duration.ofMinutes(95), 9, 0);
        schedule1.add(new Showing(movie4, 4, LocalDateTime.of(datePlus2, LocalTime.of(11, 0))));
        theater.updateSchedule(datePlus2, schedule1);
        reservation = theater.reserve(john, 2, 4, LocalDate.now().plusDays(2));
        assertNotNull(reservation);
	}
	
	@Test
	void printMovieSchedule() {
		Theater theater = new Theater(LocalDateProvider.singleton());
		theater.printSchedule();
		theater.printJsonFormat();
	}
}
