package me.gostev.scibite.dbruped.dbpedia;

/**
 * A container class that follows DBpedia response JSON structure
 */
public class Response {
	private Result result;

	public void setResults(Result result) {
		this.result = result;
	}

	public Result getResult() {
		return result;
	}
}
