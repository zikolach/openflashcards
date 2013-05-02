package openflashcards.entity;

import lombok.Getter;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;

@Entity
public class Tag {
	@Id @Getter Long id;
	@Index @Load Ref<User> user;
	@Index @Load Ref<Translation> translation;
	@Index @Getter String name;
	
	public Tag() {}
	
	public Tag(User user, Translation translation, String name) {
		this.user = Ref.create(user);
		this.translation = Ref.create(translation);
		this.name = name;
	}
	
	public User getUser() {
		if (user == null) return null;
		return user.get();
	}
	
	public Translation getTranslation() {
		if (translation == null) return null;
		return translation.get();
	}
	
	public String toString() {
		return "Tag [id=" + id + ", name=" + name + "]";
	}

	public boolean equals(Object that) {
		if (that == null)
			return false;
		if (that.getClass() != this.getClass())
			return false;
		Tag l = (Tag) that;
		if (this.id.equals(l.id))
			return true;
		return false;
	}
}
