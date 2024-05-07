package me.gostev.scibite.dbruped.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class SubstAndProcessEvaluatorTest {

	private static final Map<String, Function<String, String>> functions = Map.of(
			"Af", s -> s + "A",
			"Bf", s -> s + "B",
			"Cf", s -> s + "C");

	private static final Map<String, String> variables = Map.of(
			"Var1", "Value1",
			"Var2", "Value2",
			"Var3", "Value3");

	private static SubstAndProcessEvaluator proc = new SubstAndProcessEvaluator(variables, functions);

	@Test
	void test_VarSubstitute() {
		assertEquals("Value2", proc.apply("Var2"));
	}

	@Test
	void test_MissingVarSubstitute() {
		assertEquals("", proc.apply("VarX"));
	}

	@Test
	void test_VarSubstituteAndProcess() {
		assertEquals("Value1A", proc.apply("Var1:Af"));
	}

	@Test
	void test_VarSubstituteAndMultiProcess() {
		assertEquals("Value1AC", proc.apply("Var1:Af:Cf"));
	}

	@Test
	void test_VarSubstituteAndMissingProcess() {
		assertTrue(proc.apply("Var1:Af:Cxxx").startsWith("ERROR"));
	}

}
