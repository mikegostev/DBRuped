package me.gostev.sb.dbpq.qtemplate;

import java.util.Objects;

public sealed class TemplateElement permits WordElement, ResourceElement, PropertyElement {
	public enum ElementType {
		WORD,
		PROPERTY,
		RESOURCE
	}
	
	public static class Builder {
		Builder setCaptureElement() {
			return this;
		}
		
		Builder setElementType(ElementType type) {
			return this;
		}

		Builder setProperty(boolean isProperty) {
			return this;
		}

		Builder addReference(String ref) {
			Objects.nonNull(ref);
			return this;
		}

		Builder addWord(String word) {
			return this;
		}

		TemplateElement build() {
			return new WordElement();
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	public static TemplateElement buildWordElement(String word) {
		Builder builder = builder();
		builder.addWord(word);
		return builder.build();
	}
}