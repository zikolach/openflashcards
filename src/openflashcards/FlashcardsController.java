package openflashcards;

import java.util.Arrays;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
		ObjectifyService.register(Tag.class);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";
		//return "redirect:/flashcards";
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
		return "list";
	}
	
	@RequestMapping(value = "/flashcards", method = RequestMethod.POST)
	public String addFlashcard(String wordLanguage, String word,
			String translationLanguage, String translation, Model model) {
		return "forward:/languages/" + wordLanguage + "/flashcards/" + word + "/translations";
	}

}
