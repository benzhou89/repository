package com.jpmc.theater;

import java.time.LocalDate;

public class LocalDateProvider {

    private LocalDateProvider() {
    	
    }

    //previous one not thread safe and this make it thread safe
    private static class SingletonClassHolder
    {
      private static LocalDateProvider instance= new LocalDateProvider();
    }
    
    public static LocalDateProvider singleton()
    {
      return SingletonClassHolder.instance;
    }

    public LocalDate currentDate() {
            return LocalDate.now();
    }
}
