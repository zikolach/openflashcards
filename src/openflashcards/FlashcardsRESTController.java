package openflashcards;

import static com.googlecode.objectify.ObjectifyService.ofy;
import openflashcards.entity.Flashcard;
import openflashcards.entity.Language;
import openflashcards.entity.Translation;
import openflashcards.entity.User;
import openflashcards.entity.UserFlashcard;
import openflashcards.entity.UserLanguage;
import openflashcards.service.FlashcardsService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

@Controller
public class FlashcardsRESTController {
	static {
		ObjectifyService.register(Flashcard.class);
		ObjectifyService.register(Translation.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Language.class);
		ObjectifyService.register(UserLanguage.class);
		ObjectifyService.register(UserFlashcard.class);
	}
	
	@RequestMapping(value = "/languages/{lang}/flashcards/{word}", method = RequestMethod.GET)
	public String show(@PathVariable String lang, @PathVariable String word, Model model) {
		if (lang == null || word == null || lang.length() == 0 || word.length() == 0)
			return "redirect:/flashcards";
		Key<Language> languageKey = Key.create(Language.class, lang);
		Key<Flashcard> flashcardKey = Key.create(languageKey, Flashcard.class, word);
		Flashcard flashcard = ofy().load().type(Flashcard.class).filterKey(flashcardKey).first().get();
		model.addAttribute("flashcard", flashcard);
		return "card";
	}
	
	@RequestMapping(value = "/languages/{lang}/flashcards/{word}/translations", method = RequestMethod.POST)
	public String addFlashcardTranslation(@PathVariable String lang,
			@PathVariable String word,
			String translationLanguage, 
			String translation, Model model) {
		if (word != null && !word.equals("") && lang != null
				&& !lang.equals("")) {
			// find or add flashcard
			Flashcard flashcard = FlashcardsService.addFlashcard(word, lang);
			// add translation
			flashcard.addTranslation(translation, FlashcardsService.getLanguage(translationLanguage));
			// manage user data
			User u = FlashcardsService.getCurrentUser();
			if (u != null) {
				u.setLastWordLanguage(FlashcardsService.getLanguage(lang));
				u.setLastTranslationLanguage(FlashcardsService.getLanguage(translationLanguage));
				FlashcardsService.saveUser(u);

				// add to user references
				UserLanguage ul = u.addLanguage(lang);
				UserFlashcard uf = FlashcardsService.getUserLanguageFlashcard(ul, word);
				if (uf == null) ul.addFlashcard(word);
			}
		}
		return "redirect:/languages/" + lang + "/flashcards/" + word;
	}
	
}
