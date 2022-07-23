package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * this Threater assume there are two days movie schedule can be available and reserved.
 * if want more day, we can either add Threater construtor by adding the days parameter, and initial map with that kind of map key.
 * @author 
 *
 */
public class Theater {

    LocalDateProvider provider;
    private final Map<LocalDate, List<Showing>> schedule = new ConcurrentHashMap<>();  //can reserve 2 days

    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        Movie theBatMan2 = new Movie("The Batman2", Duration.ofMinutes(150), 12.5, 1);
        LocalDate date22 = provider.currentDate();
        LocalDate date23 = provider.currentDate().plusDays(1);
        
        List<Showing> schedule1 = List.of(
            new Showing(turningRed, 1, LocalDateTime.of(date22, LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(date22, LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(date22, LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(date22, LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(date22, LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(date22, LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(date22, LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(date22, LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(date22, LocalTime.of(23, 0))),
            new Showing(theBatMan2, 10, LocalDateTime.of(date22, LocalTime.of(23, 0)))
        );
        
        schedule.put(date22, schedule1);
        List<Showing> schedule2 = List.of(
                new Showing(spiderMan, 1, LocalDateTime.of(date23, LocalTime.of(9, 0))),
                new Showing(turningRed, 2, LocalDateTime.of(date23, LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(date23, LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(date23, LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(date23, LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(date23, LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(date23, LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(date23, LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(date23, LocalTime.of(23, 0))),
                new Showing(theBatMan2, 10, LocalDateTime.of(date23, LocalTime.of(23, 0)))
            );
        schedule.put(date23, schedule1);
    }

    //this method is used to update the schedule map. This one should only be called by some one has authorization to do 
    public void updateSchedule(LocalDate dateKey, List<Showing> showings) {
        for (;;) {

        	List<Showing> local = schedule.get(dateKey);
            if(local == null){
                local = schedule.putIfAbsent(dateKey, showings);
                if(local == null)
                    return;
            }
            if (local.equals(showings)) {
                return;
            }

            if (schedule.replace(dateKey, local, showings))
                return;
        }
    }
    
    public Reservation reserve(Customer customer, int sequence, int howManyTickets, LocalDate localDate) {
        Showing showing;
        try {
        	if (schedule.get(localDate) != null) {
        		showing = schedule.get(localDate).get(sequence - 1);
        	} else {
        		System.out.println("Can not reserve for date: "+localDate);
        		return null;
        	}
        	
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public void printSchedule() {
        System.out.println(provider.currentDate());
        System.out.println("===================================================");
        for (Map.Entry<LocalDate,List<Showing>> entry : schedule.entrySet()) {
        	System.out.println("Schedule for "+entry.getKey());
        	entry.getValue().forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovieFee())
        );
        }
        System.out.println("===================================================");
    }
    
    public void printJsonFormat() {
    	 ObjectMapper mapper = new ObjectMapper();
    	 ObjectWriter writer =  mapper.writerWithDefaultPrettyPrinter();
    	 System.out.println("JSON===================================================Start");
    	 for (Map.Entry<LocalDate,List<Showing>> entry : schedule.entrySet()) {
    		 System.out.println("Schedule for "+entry.getKey());
    		 entry.getValue().forEach(s -> {
			try {
				System.out.println(writer.writeValueAsString(s));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
    	 }
    	System.out.println("JSON===================================================Done");
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printSchedule();
        theater.printJsonFormat();
    }
}
