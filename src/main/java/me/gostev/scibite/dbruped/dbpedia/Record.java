package me.gostev.scibite.dbruped.dbpedia;

/**
 * A container class that follows DBpedia response JSON structure
 */
public class Record {
	private String type;

	private String datatype;
	private String value;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
