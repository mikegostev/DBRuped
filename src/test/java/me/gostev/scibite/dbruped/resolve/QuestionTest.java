package me.gostev.scibite.dbruped.resolve;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.gostev.scibite.dbruped.resolve.DefaultQuestionResolver;
import me.gostev.scibite.dbruped.resolve.Question;

@SpringBootTest
public class QuestionTest {

	@Autowired
	DefaultQuestionResolver resolver;

	@Test
	public void test_me() {
		assertEquals(57, Question.ask("How old is David Cameron"));
		assertEquals(71, Question.ask("How old is Tony Blair"));
		assertEquals("Anthony Charles Lynton Blair",
				Question.ask("What is the birth name of Tony Blair ?"));

	}
}
