package openflashcards.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import openflashcards.service.FlashcardsService;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

@Entity
@Data
public class User {
	@Id
	String id;
	String name;
	String email;
	@Load List<Ref<Flashcard>> lastAddedFlashcards = new ArrayList<Ref<Flashcard>>();
	@Load Ref<Language> lastWordLanguage;
	@Load Ref<Language> lastTranslationLanguage;

	

	public User() {
	}

	public User(com.google.appengine.api.users.User u) {
		this.id = u.getUserId();
		this.name = u.getNickname();
		this.email = u.getEmail();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public List<Flashcard> getLastAddedFlashcards() {
		List<Flashcard> list = new ArrayList<Flashcard>();
		for (Ref<Flashcard> rf : lastAddedFlashcards)
			list.add(rf.get());
		return list;
	}

	public void addFlashcard(Flashcard f) {
		Ref<Flashcard> rf = Ref.create(f);
		if (!lastAddedFlashcards.contains(rf))
			lastAddedFlashcards.add(rf);
	}

	public Language getLastWordLanguage() {
		return lastWordLanguage == null ? null : lastWordLanguage.get();
	}

	public void setLastWordLanguage(Language lastWordLanguage) {
		this.lastWordLanguage = Ref.create(lastWordLanguage);
	}

	public Language getLastTranslationLanguage() {
		return lastTranslationLanguage == null ? null : lastTranslationLanguage.get();
	}

	public void setLastTranslationLanguage(Language lastTranslationLanguage) {
		this.lastTranslationLanguage = Ref.create(lastTranslationLanguage);
	}

	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
	
	public List<UserLanguage> getLanguages() {
		return FlashcardsService.getUserLanguages(this);
	}
	
	public UserLanguage addLanguage(String lang) {
		Language language = FlashcardsService.getLanguage(lang);
		UserLanguage userLanguage = FlashcardsService.getUserLanguage(this, language);
		if (userLanguage == null) {
			userLanguage = new UserLanguage(language, this);
			FlashcardsService.saveUserLanguage(userLanguage);
		}
		return userLanguage;
	}
	
	public UserFlashcard findFlashcard(String word, String lang) {
		Language l = FlashcardsService.getLanguage(lang);
		UserLanguage ul = FlashcardsService.getUserLanguage (this, l);
		if (ul == null) return null;
		return FlashcardsService.getUserLanguageFlashcard(ul, word);
	}

}
