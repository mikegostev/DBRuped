package me.gostev.scibite.dbruped.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import me.gostev.scibite.dbruped.text.RegexSubstituror;

class RegexSubstiturorTest {
	private static Function<String, String> processor = String::toUpperCase;

	@Test
	void test_SimpleSubstitute() {
		RegexSubstituror subst = new RegexSubstituror("${", "}", processor);

		assertEquals("abcDEFgh", subst.process("abc${def}gh"));
	}

	@Test
	void test_SubstituteAtBegin() {
		RegexSubstituror subst = new RegexSubstituror("${", "}", processor);

		assertEquals("ABcdefgh", subst.process("${ab}cdefgh"));
	}

	@Test
	void test_SubstituteAtEnd() {
		RegexSubstituror subst = new RegexSubstituror("${", "}", processor);

		assertEquals("abcdefGH", subst.process("abcdef${gh}"));
	}

}
