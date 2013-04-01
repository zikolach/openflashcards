package openflashcards;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
//import java.util.logging.Logger;

@Controller
public final class FlashcardsController {
	static {
		ObjectifyService.register(Flashcard.class);
		ObjectifyService.register(Translation.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Language.class);
		ObjectifyService.register(UserLanguage.class);
		ObjectifyService.register(UserFlashcard.class);
		
	}
	//private static final Logger log = Logger
	//		.getLogger(FlashcardsController.class.getName());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		//return "index";
		return "redirect:/flashcards";
	}
	
	@RequestMapping(value = "/flashcards", method = RequestMethod.GET)
	public String list(@RequestParam(required=false) String filterWord, 
			@RequestParam(required=false) String[] filterWordLanguage, 
			@RequestParam(required=false) String[] filterTranslationLanguage,
			Model model) {
		if (filterWord != null)
			model.addAttribute("filterWord", filterWord);
		if (filterWordLanguage != null)
			model.addAttribute("filterWordLanguage", Arrays.asList(filterWordLanguage));
		if (filterTranslationLanguage != null)
			model.addAttribute("filterTranslationLanguage", Arrays.asList(filterTranslationLanguage));
		
		model.addAttribute("flashcards", FlashcardsService.findFlashcards(filterWord,  filterWordLanguage));
		return "index";
	}

	@RequestMapping(value = "/languages/{languageId}/flashcards/{word}", method = RequestMethod.GET)
	public String show(@PathVariable String languageId, @PathVariable String word, Model model) {
		if (languageId == null || word == null || languageId.length() == 0 || word.length() == 0)
			return "redirect:/flashcards";
		Key<Language> languageKey = Key.create(Language.class, languageId);
		Key<Flashcard> flashcardKey = Key.create(languageKey, Flashcard.class, word);
		Flashcard flashcard = ofy().load().type(Flashcard.class).filterKey(flashcardKey).first().get();
		model.addAttribute("flashcard", flashcard);
		return "card";
	}

	@RequestMapping(value = "/flashcards", method = RequestMethod.POST)
	public String addFlashcard(String wordLanguage, String word,
			String translationLanguage, String translation, Model model) {
		if (word != null && !word.equals("") && wordLanguage != null
				&& !wordLanguage.equals("")) {
			Flashcard flashcard = FlashcardsService.addFlashcard(word, wordLanguage);
			
			flashcard.addTranslation(translation, FlashcardsService.getLanguage(translationLanguage));

			User u = FlashcardsService.getCurrentUser();
			if (u != null) {
				u.setLastWordLanguage(FlashcardsService.getLanguage(wordLanguage));
				u.setLastTranslationLanguage(FlashcardsService.getLanguage(translationLanguage));
				u.addFlashcard(flashcard);
				
				UserLanguage ul = u.addLanguage(wordLanguage);
				ul.addFlashcard(word);
				
				FlashcardsService.saveUser(u);
			}

			//return "redirect:/flashcards/" + flashcard.getId();
		}
		return "redirect:/flashcards";
	}

	@RequestMapping(value = "/users/logout", method = RequestMethod.GET)
	public String logout() {
		FlashcardsService.logout();
		return "redirect:/flashcards";
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public String viewProfile(@PathVariable String userId) {
		return "users";
	}
	
	@RequestMapping(value = "/users/{userId}/languages/{languageId}/flashcards/{word}", method = RequestMethod.GET)
	public String addUserFlashcard(@PathVariable String userId, 
								   @PathVariable String languageId, 
								   @PathVariable String word,
								   Model model)  {
		User u = FlashcardsService.getCurrentUser();
		if (u != null && u.getId().equals(userId)) {
			UserLanguage ul = u.addLanguage(languageId);
			UserFlashcard uf =  FlashcardsService.getUserLanguageFlashcard(ul, word);
			if (uf == null)
				ul.addFlashcard(word);
			else
				ul.removeFlashcard(word);
		}
		return "redirect:/flashcards";
	}

}
