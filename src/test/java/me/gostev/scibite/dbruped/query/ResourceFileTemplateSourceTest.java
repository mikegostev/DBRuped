package me.gostev.scibite.dbruped.query;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import me.gostev.scibite.dbruped.template.ResourceFileTemplateSource;

public class ResourceFileTemplateSourceTest {
	@Test
	void loadTemplatesTest() {
		ResourceFileTemplateSource source = new ResourceFileTemplateSource();
		source.load();
		
		assertTrue(source.getTemplates().size() > 0);
	}
}
