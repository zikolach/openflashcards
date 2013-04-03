package openflashcards;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.googlecode.objectify.ObjectifyService;

@Controller
public final class UserController {
	static {
		ObjectifyService.register(Flashcard.class);
		ObjectifyService.register(Translation.class);
		ObjectifyService.register(User.class);
		ObjectifyService.register(Language.class);
		ObjectifyService.register(UserLanguage.class);
		ObjectifyService.register(UserFlashcard.class);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		FlashcardsService.logout();
		return "redirect:/";
	}
	
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
	public String showUser(@PathVariable String userId) {
		return "users";
	}
	// http://localhost:8888/users/18580476422013912411/languages/RU/flashcards/%D0%BF%D0%B8%D1%82%D1%8C
	@RequestMapping(value = "/users/{user}/languages/{lang}/flashcards/{word}", method = RequestMethod.GET)
	public String toggleUserFlashcard(@PathVariable String user,
			@PathVariable String lang, @PathVariable String word, Model model) {
		User u = FlashcardsService.getCurrentUser();
		if (u != null && u.getId().equals(user)) {
			UserLanguage ul = u.addLanguage(lang);
			UserFlashcard uf = FlashcardsService.getUserLanguageFlashcard(ul,
					word);
			if (uf == null)
				ul.addFlashcard(word);
			else
				ul.removeFlashcard(word);
		}
		return "redirect:/users/" + u.getId();
	}

	@RequestMapping(value = "/users/{user}/languages/{lang}/flashcards/{word}", method = RequestMethod.PUT)
	@ResponseBody
	public String addUserFlashcard(@PathVariable String user,
			@PathVariable String lang, @PathVariable String word) {
		User u = FlashcardsService.getCurrentUser();
		if (u != null && u.getId().equals(user)) {
			UserLanguage ul = u.addLanguage(lang);
			UserFlashcard uf = FlashcardsService.getUserLanguageFlashcard(ul,
					word);
			if (uf == null)
				ul.addFlashcard(word);
		}
		return "Success";
	}

	@RequestMapping(value = "/users/{user}/languages/{lang}/flashcards/{word}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteUserFlashcard(@PathVariable String user,
			@PathVariable String lang, @PathVariable String word, Model model) {
		User u = FlashcardsService.getCurrentUser();
		if (u != null && u.getId().equals(user)) {
			UserLanguage ul = u.addLanguage(lang);
			UserFlashcard uf = FlashcardsService.getUserLanguageFlashcard(ul,
					word);
			if (uf != null)
				ul.removeFlashcard(word);
		}
		return "Success";
	}
}
