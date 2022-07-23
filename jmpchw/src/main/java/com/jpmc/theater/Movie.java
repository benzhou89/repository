package com.jpmc.theater;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Movie {
    private static int MOVIE_CODE_SPECIAL = 1;
    private static LocalTime START_DISCOUNT_TIME = LocalTime.of(11, 0);  //the start time to discount included 11am
    private static LocalTime END_DISCOUNT_TIME = LocalTime.of(16, 0);  //the end time to discount included 16pm

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;  //since for movie ticket, the price can be integer or half dollar, so it is fine to store the price as double. Otherwise, need to consider use BigDecimal
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing);
    }

    private double getDiscount(Showing showing) {
    	int showSequence = showing.getSequenceOfTheDay();
    	LocalTime startTime = showing.getStartTime().toLocalTime();
 
        double specialDiscount = 0;
        if ((START_DISCOUNT_TIME.isBefore(startTime) || START_DISCOUNT_TIME.equals(startTime)) 
        		&& END_DISCOUNT_TIME.isAfter(startTime) || END_DISCOUNT_TIME.equals(startTime)) {
        	specialDiscount = ticketPrice * 0.25; // 25% discount when start time between 11am(included) to 4pm(included)
        } else if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {

            sequenceDiscount = 2; // $2 discount for 2nd show
        }
        if (sequenceDiscount == 0 && showing.getStartTime().getDayOfMonth()==7) { //if there is no showSequence discount and the showing date is 7th day of each month, apply $1 discount
        	sequenceDiscount = 1;
        }

        // biggest discount wins
        return specialDiscount > sequenceDiscount ? specialDiscount : sequenceDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}