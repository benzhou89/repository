package com.jpmc.theater;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.getTicketPrice();
    }

    public double getMovieFee(int audienceCount) {
        return calculateFee(audienceCount);
    }
    
    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    //Here, round the price due to have % saving
    private double calculateFee(int audienceCount) {
    	BigDecimal bdUp= new BigDecimal(movie.calculateTicketPrice(this) * audienceCount).setScale(2, RoundingMode.UP);
        return bdUp.doubleValue();
    }
}
