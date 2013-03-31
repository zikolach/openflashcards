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
public class UserLanguage {
	@Id Long id;
	@Load @Index Ref<Language> language;
	@Parent @Load Ref<User> user;

	public UserLanguage() {}

	public UserLanguage(Language language, User user) {
		this.language = Ref.create(language);
		this.user = Ref.create(user);
	}
	
	public Language getLanguage() {
		return language == null ? null : language.get(); 
	}
	
	public User getUser() {
		return user == null ? null : user.get(); 
	}
	
	public List<UserFlashcard> getFlashcards() {
		return FlashcardsService.getUserLanguageFlashcards(this);
	}
	
	public void addFlashcard(String word) {
		Flashcard fc = FlashcardsService.addFlashcard(word, getLanguage().getId());
		UserFlashcard uf = FlashcardsService.getUserLanguageFlashcard(this, word);
		if (uf == null)
			uf = new UserFlashcard(fc, this);
		FlashcardsService.saveUserFlashcard(uf);
	}
	
	public void removeFlashcard(String word) {
		UserFlashcard uf = FlashcardsService.getUserLanguageFlashcard(this, word);
		FlashcardsService.deleteUserFlashcard(uf);
	}

}
