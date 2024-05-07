package me.gostev.scibite.dbruped.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import me.gostev.scibite.dbruped.text.SimpleSubstitutorAndEvaluator;

class SimpleSubstitutorAndEvaluatorTest {

	@Test
	void test_date2age() {
		assertEquals("57", SimpleSubstitutorAndEvaluator.date2age("1966-10-05", LocalDate.parse("2024-05-04")));
	}

}
