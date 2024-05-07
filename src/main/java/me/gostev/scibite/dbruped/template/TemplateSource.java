package me.gostev.scibite.dbruped.template;

import java.util.List;

/**
 * An interface for services that provides templates.
 */
public interface TemplateSource {
	/**
	 * Signal the source to load templates from a back storage.
	 */
	void load();

	/**
	 * Once {@link #load()} was successfully called templates can be acquired by
	 * this method.
	 * 
	 * @return a list of templates.
	 */
	List<Template> getTemplates();
}
