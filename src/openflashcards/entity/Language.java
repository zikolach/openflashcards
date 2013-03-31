package openflashcards.entity;

import lombok.Getter;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Language {
	@Id @Getter String id;
	@Getter String name;

	public Language() {
	}

	public Language(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String toString() {
		return "Language [id=" + id + ", name=" + name + "]";
	}

	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (that.getClass() != this.getClass())
			return false;
		Language l = (Language) that;
		if (this.id.equals(l.id))
			return true;
		return false;
	}
}
