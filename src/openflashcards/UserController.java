package openflashcards;

import openflashcards.entity.Flashcard;
import openflashcards.entity.Language;
import openflashcards.entity.Tag;
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
		ObjectifyService.register(Tag.class);
	}
	
	@RequestMapping(value = "/users/logout", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/users/{user}/tags", method = RequestMethod.POST)
	public String addTag(@PathVariable String user, String language, String flashcard, Long translation, String tagName, Model model) {
		if (user == null || translation == null || tagName == null ||
		    user.length() == 0 || tagName.length() == 0)
			return "error";
		User currentUser = FlashcardsService.getCurrentUser();
		if (!currentUser.getId().equals(user))
			return "error";
		
		Tag tag = currentUser.addTag(language, flashcard, translation, tagName);
		model.addAttribute("tag", tag);
		
		return "tag";
	}
	
	@RequestMapping(value = "/users/{user}/tags/{tag}", method = RequestMethod.DELETE)
	@ResponseBody public String deleteTag(@PathVariable String user, @PathVariable Long tag) {
		if (user == null || tag == null || user.length() == 0)
			return "error";
		User currentUser = FlashcardsService.getCurrentUser();
		if (!currentUser.getId().equals(user))
			return "error";
		
		FlashcardsService.deleteTag(currentUser, tag);
		
		return "Success";
	}
	
}
