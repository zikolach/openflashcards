package openflashcards.service;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import openflashcards.entity.Flashcard;
import openflashcards.entity.Language;
import openflashcards.entity.Tag;
import openflashcards.entity.Translation;
import openflashcards.entity.User;
import openflashcards.entity.UserFlashcard;
import openflashcards.entity.UserLanguage;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;

public class FlashcardsService {
	
	static User currentUser;
	
	public static User getCurrentUser() {
		if (currentUser == null) {
			UserService us = UserServiceFactory.getUserService();
			com.google.appengine.api.users.User u = us.getCurrentUser();

			if (u != null) {
				currentUser = FlashcardsService.getUser(u);
				if (currentUser == null) {
					currentUser = new User(u);
					FlashcardsService.saveUser(currentUser);
				}
			}
		}
		return currentUser;
	}
	
	public static void logout() {
		currentUser = null;
	}
	
	public static void saveFlashcard(Flashcard flashcard) {
		ofy().save().entity(flashcard).now();
	}

	public static Flashcard addFlashcard(String word, String language) {
		Flashcard flashcard = getFlashcard(word, language);
		if (flashcard == null) {
			flashcard = new Flashcard(word, FlashcardsService.getLanguage(language));
			saveFlashcard(flashcard);
		}
		return flashcard;
	}
	
	public static Flashcard getFlashcard(String word, String lang) {
		if (word == null || lang == null || word.length() == 0 || lang.length() == 0)
			return null;
		Language language = ofy().load().type(Language.class).id(lang).get();
		Key<Flashcard> key = Key.create(Key.create(language), Flashcard.class, word);
		return ofy().load().type(Flashcard.class).ancestor(language).filterKey(key).first().get();
	}
	
	// looking for a word by text and filter translations by languages and tags
	public static List<Flashcard> findFlashcards(String word, String[] langs) {
		List<Language> languages = null;
		// load languages
		if (langs == null || langs.length == 0)
			languages = getLanguages();
		else {
			languages = new ArrayList<Language>(langs.length);
			for (String s : langs)
				languages.add(ofy().load().type(Language.class).id(s).get());
		}
		// load words
		List<Flashcard> flashcards = new ArrayList<Flashcard>();
		Key<Flashcard> flashcardKey = null, flashcardKeyZ = null; 
		for (Language language : languages) {
			if (word != null && word.length() > 0) {
				flashcardKey = Key.create(Key.create(language), Flashcard.class, word);
				flashcardKeyZ = Key.create(Key.create(language), Flashcard.class, word + "\uFFFD");
				flashcards.addAll(ofy().load().type(Flashcard.class)
						.ancestor(language)
						.filterKey(">=", flashcardKey)
						.filterKey("<", flashcardKeyZ)
						.list());
			}
			else
				flashcards.addAll(ofy().load().type(Flashcard.class).ancestor(language).list());
		}
		return flashcards;
	}
	
	public static void saveTranslation(Translation translation) {
		ofy().save().entity(translation).now();
	}
	
	public static List<Translation> getTranslations(Flashcard flashcard) {
		return ofy().load().type(Translation.class).ancestor(flashcard).list();
	}
	
	public static Translation getTranslation(String language, String flashcard, Long translation) {
		Flashcard f = getFlashcard(flashcard, language);
		Key<Translation> key = Key.create(Key.create(f), Translation.class, translation);
		return ofy().load().key(key).get();
	}
	
	public static void saveUser(User user) {
		ofy().save().entities(user).now();
	}
	
	public static User getUser(com.google.appengine.api.users.User u) {
		return ofy().load().type(User.class).id(u.getUserId()).get();
	}
	
	public static List<Language> getLanguages() {
		if (ofy().load().type(Language.class).count() < 3) {
			List<Language> ls = Arrays.asList(new Language[] {
					new Language("DE", "Deutsch"),
					new Language("EN", "English"),
					new Language("RU", "Russian") });
			ofy().save().entities(ls).now();
			return ls;
		}
		return ofy().load().type(Language.class).list();
	}
	
	public static void saveLanguage(Language language) {
		ofy().save().entity(language).now();
	}
	
	public static Language getLanguage(String id) {
		return ofy().load().type(Language.class).filterKey(Key.create(Language.class, id)).first().get();
	}
	
	public static List<UserLanguage> getUserLanguages(User user) {
		return ofy().load().type(UserLanguage.class).ancestor(user).list();
	}
	
	public static UserLanguage getUserLanguage(User user, Language language) {
		UserLanguage ul = ofy().load().type(UserLanguage.class).ancestor(user).filter("language", language).first().get();
		return ul;
	}
	
	public static void saveUserLanguage(UserLanguage userLanguage) {
		ofy().save().entity(userLanguage).now();
	}
	
	public static List<UserFlashcard> getUserLanguageFlashcards(UserLanguage userLanguage) {
		return ofy().load().type(UserFlashcard.class).ancestor(userLanguage).list();
	}
	
	public static UserFlashcard getUserLanguageFlashcard(UserLanguage userLanguage, String word) {
		Flashcard f = getFlashcard(word, userLanguage.getLanguage().getId());
		return ofy().load().type(UserFlashcard.class).ancestor(userLanguage).filter("flashcard", f).first().get();
	}
	
	public static void saveUserFlashcard(UserFlashcard userFlashcard) {
		ofy().save().entity(userFlashcard).now();
	}
	
	public static void deleteUserFlashcard(UserFlashcard userFlashcard) {
		ofy().delete().entity(userFlashcard).now();
	}
	
	public static List<Tag> getUserTranslationTags(User u, Translation t) {
		if (u == null) return null;
		return ofy().load().type(Tag.class).filter("user", u).filter("translation", t).list();
	}
	
	public static Tag getTag(Long t) {
		return ofy().load().key(Key.create(Tag.class, t)).get();
	}
	
	public static void saveTag(Tag tag) {
		ofy().save().entity(tag).now();
	}
	
	public static void deleteTag(Long t) {
		ofy().delete().key(Key.create(Tag.class, t)).now();
	}
	
	public static Collection<String> getUniqueTagNames() {
		List<Tag> tags = ofy().load().type(Tag.class).list();
		TreeSet<String> names = new TreeSet<String>();
		for (Tag tag : tags) {
			if (tag.getName() != null)
				names.add(tag.getName());
		}
		return names;
	}
	

}
