package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CustomerTest {

	@Test
	void test_equal() {
		Customer john = new Customer("John Doe", "id-12345");
		Customer john2 = new Customer("John Doe", "id-12345");
		assertTrue(john.equals(john2));
		assertTrue(john.equals(john));
		assertTrue(john.hashCode()==john2.hashCode());
		
		assertNotNull(john.getName());
		assertNotNull(john.toString());
		
		john2 = new Customer("John Doe", "id-123452");
		assertFalse(john.equals(john2));
		assertFalse(john.hashCode()==john2.hashCode());
		
    	assertFalse(john.equals(null));
    	assertFalse(john.equals(new Object()));
	}
}
