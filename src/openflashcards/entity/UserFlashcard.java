package openflashcards.entity;

import java.util.Date;

import lombok.Getter;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class UserFlashcard {
	@Id Long id;
	@Index @Load Ref<Flashcard> flashcard;
	@Parent @Load Ref<UserLanguage> userLanguage;
	@Index @Getter Date createdAt;

	public UserFlashcard() {
	};

	public UserFlashcard(Flashcard flashcard, UserLanguage userLanguage) {
		this.flashcard = Ref.create(flashcard);
		this.userLanguage = Ref.create(userLanguage);
		this.createdAt = new Date();
	}
	
	public Flashcard getFlashcard() {
		return flashcard == null ? null : flashcard.get();
	}

}
