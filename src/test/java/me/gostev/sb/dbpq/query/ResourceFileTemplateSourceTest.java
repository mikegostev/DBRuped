package me.gostev.sb.dbpq.query;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class ResourceFileTemplateSourceTest {
	@Test
	void loadTemplatesTest() throws IOException {
		ResourceFileTemplateSource source = new ResourceFileTemplateSource();
		source.load();
		
		assertTrue(source.getTemplates().size() > 0);
	}
}
