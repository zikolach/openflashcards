package openflashcards.entity;

import java.util.List;

import openflashcards.service.FlashcardsService;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Translation {
	@Id Long id;
	String text;
	@Load @Index Ref<Language> language;
	@Load @Parent Ref<Flashcard> flashcard;
	
	public Translation() {}
	
	public Translation(Flashcard flashcard, String text, Language language) {
		this.flashcard = Ref.create(flashcard);
		this.text = text;
		this.language = Ref.create(language);
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public Language getLanguage() {
		return language.get();
	}
	
	public Flashcard getFlashcard() {
		return flashcard.get();
	}

	public String toString() {
		return new StringBuilder().append("(id = ").append(id)
								  .append(" flashcard = ").append(flashcard)
								  .append(" text = ").append(text)
								  .append(")").toString();
	}
	
	public List<Tag> getUserTags(User u) {
		return FlashcardsService.getUserTranslationTags(u, this);
	}
	
}
