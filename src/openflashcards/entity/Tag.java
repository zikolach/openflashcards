package openflashcards.entity;

import lombok.Getter;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Tag {
	@Id @Getter Long id;
	@Load @Parent Ref<User> user;
	@Index @Load Ref<Translation> translation;
	@Index @Getter String tagName;
	
	public Tag() {}
	
	public Tag(User user, Translation translation, String tagName) {
		this.user = Ref.create(user);
		this.translation = Ref.create(translation);
		this.tagName = tagName;
	}
	
	public User getUser() {
		if (user == null) return null;
		return user.get();
	}
	
	public Translation getTranslation() {
		if (translation == null) return null;
		return translation.get();
	}
}
