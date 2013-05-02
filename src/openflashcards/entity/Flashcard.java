package openflashcards.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import lombok.Getter;
import openflashcards.service.FlashcardsService;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Flashcard {
	@Id @Index @Getter	String id;
	//@Index String word;
	//@Load @Index Ref<Language> language;
	@Load @Parent Ref<Language> language;

	private static final Logger log = Logger.getLogger(Flashcard.class.getName());

	public Flashcard() {}

	public Flashcard(String word, Language language) {
		//this.word = word;
		this.id = word;
		this.language = Ref.create(language);
	}

	public Language getLanguage() {
		return language == null ? null : language.get();
	}

	public List<Translation> getTranslations() {
		return FlashcardsService.getTranslations(this);
	}

	public List<Translation> getTranslations(List<String> filter) {
		List<Translation> list = FlashcardsService.getTranslations(this);
		ArrayList<Translation> result = new ArrayList<Translation>();
		for (Translation tr : list) {
			if (filter == null || filter.contains(tr.getLanguage().getId()))
				result.add(tr);
		}
		return result;
	}

	public List<Translation> getTranslations(List<String> filterLanguage, List<String> filterTags) {
		List<Translation> list = FlashcardsService.getTranslations(this);
		ArrayList<Translation> result = new ArrayList<Translation>();
		User u = FlashcardsService.getCurrentUser();
		for (Translation tr : list) {
			if (filterLanguage == null || filterLanguage.contains(tr.getLanguage().getId())) {
				boolean tagCheck = false;
				if (filterTags != null) {
					List<Tag> tags = tr.getUserTags(u);
					if (tags != null)
						for (Tag tag : tags)
							if (filterTags.contains(tag.name)) {
								tagCheck = true;
								break;
							}
				}
				else
					tagCheck = true;
				if (tagCheck)
					result.add(tr);
			}
		}
		return result;
	}
	
	public void addTranslation(String text, Language language) {
		if (text != null && !text.equals("")) {
			Translation translation = new Translation(this, text, language);
			FlashcardsService.saveTranslation(translation);
			log.info("new translation = " + translation + " for flashcard = "
					+ this);
		}
	}

	public String toString() {
		return new StringBuffer().append("(id = ").append(id)
				.append(" language = ")
				.append(language).append(")").toString();
	}

}
