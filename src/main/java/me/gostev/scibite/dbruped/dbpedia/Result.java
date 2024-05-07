package me.gostev.scibite.dbruped.dbpedia;

import java.util.List;
import java.util.Map;

/**
 * A container class that follows DBpedia response JSON structure
 */
public class Result {
	private List<Map<String, Record>> bindings;

	public List<Map<String, Record>> getBindings() {
		return bindings;
	}

	public void setBindings(List<Map<String, Record>> bindings) {
		this.bindings = bindings;
	}
}
