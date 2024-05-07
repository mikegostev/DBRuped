package me.gostev.scibite.dbruped.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import me.gostev.scibite.dbruped.util.StringUtils;

class StringUtilsTest {

	@Test
	void test_normilizeQuestion_extraSpaces() {
		assertEquals("Are you OK?", StringUtils.normalizeQuestion("  Are  you OK ? "));
	}

	@Test
	void test_normilizeQuestion_removeComma() {
		assertEquals("Are you OK dude?", StringUtils.normalizeQuestion("Are you OK, dude?"));
	}

	@Test
	void test_normilizeQuestion_missingQMark() {
		assertEquals("Are you OK?", StringUtils.normalizeQuestion("  Are you OK"));
	}
	
	@Test
	void match_test() {
		Pattern pat = Pattern.compile("Who is (.*?)\\?");
		Matcher mtch = pat.matcher("Who is Mr. Who?");
		
		assertTrue(mtch.matches());
		assertEquals("Mr. Who", mtch.group(1));
	}
}
