
function toggleLike(elt) {
	var url = elt.data("url");
	var method = elt.hasClass("like") ? "PUT" : "DELETE"
	$.ajax(url, {
		type: method,
		success: function(result) {
			elt.toggle().queue(function(next) {
				elt.toggleClass("like").toggleClass("dislike").toggle();
				next();
			});
		}
	})
};

function loadFlashcard(elt) {
	var url = elt.data("url");
	$.ajax(url, {
		type: "GET",
		success : function(result) {
			$("#collapseFive").html(result);
			if (!$("#collapseFive").hasClass("in"))
				$(".accordion-heading button[data-target=#collapseFive]").trigger("click");
			var tags = $("input[name=\"filterTranslationTag\"]").map(function(index) {
				return $(this).val();
			}).get();
			$( "input[name=\"tagName\"]" ).autocomplete({
			  source: tags
			});
		}
	});
};

function loadFlashcards(f) {
	var url = $(f).attr("action");
	var method = $(f).attr("method");
	var form = $(f).serialize();
	var refresh = !$("#collapseFour").hasClass("in");
	//alert(form);
	$.ajax(url, {
		type: method,
		data: form,
		beforeSend: function() {
			//disable form
			$(f).find("button").attr("disabled",true);
		},
		success: function(result) {
			$("#collapseFour").html(result);
		},
		error: function() {
			$("#collapseFour").html("<div class=\"error\">Error occurred</div>");
		},
		complete: function() {
			if (refresh)
				$(".accordion-heading button[data-target=#collapseFour]").trigger("click");
			$(f).find("button").attr("disabled",false);
		}
	});
};

function addFlashcard(f) {
	//alert(e.serialize());
	var url = $(f).attr("action");
	var method = $(f).attr("method");
	var form = $(f).serialize();
	var refresh = !$("#collapseFive").hasClass("in");
	//alert(form);
	$.ajax(url, {
		type: method,
		data: form,
		beforeSend: function() {
			//disable form
			$(f).find("button").attr("disabled",true);
		},
		success: function(result) {
			$("#collapseFive").html(result);
				
		},
		error: function() {
			$("#collapseFive").html("<div class=\"error\">Error occurred</div>");
		},
		complete: function() {
			if (refresh)
				$(".accordion-heading button[data-target=#collapseFive]").trigger("click");
			$(f).find("button").attr("disabled",false);
		}
	});
};

function addTag(f) {
	if (f.find("input[name=\"tagName\"]").first().val().length == 0) return;
	$.ajax(f.attr("action"), {
		type: f.attr("method"),
		data: f.serialize(),
		success: function(result) {
			//alert(result);
			f.closest(".panel-translation-edit ").find(".tags").append(result);
		},
		complete: function() {
			var inputTag = f.find("input[name='tagName']");
			var check = $("#filterTags input[name=\"filterTranslationTag\"]").filter(function() {
				return $(this).val() == inputTag.val();
			});
			if (check.size() == 0)
				$("#filterTags").append("<label class=\"checkbox\"><input type=\"checkbox\" name=\"filterTag\" value=\"" + inputTag.val() + "\"> " + inputTag.val() + "</label>");
			inputTag.val("");
		}
	});
};

function removeTag(t) {
	$.ajax(t.data("url"), {
		type: "DELETE",
		success: function(result) {
			t.remove();
		}
	});
};

$(document).ready(function() {
	$(document).on("click", ".action.like,.action.dislike", function(e) {
		e.preventDefault();
		toggleLike($(this));
	});
	$(document).on("click", ".action.view", function(e) {
		e.preventDefault();
		loadFlashcard($(this));
	});
	// load flashcards
	$("#search-form").on("submit", function(e) {
		e.preventDefault();
		loadFlashcards($(this));
	});
	// add flashcard
	$("#add-form").on("submit", function(e) {
		e.preventDefault();
		addFlashcard($(this));
	});
	// translations edit button
	$(document).on("click", ".action.tags", function(e) {
		e.preventDefault();
		//$("#tags-form").show();
	});
	// expand translation edit form
	$(document).on("click", "#collapseFive .translation", function(e) {
		e.preventDefault();
		$(this).toggleClass("closed");
		$(this).find(".panel-translation-edit ").slideToggle("fast");
		
	});
	// tag remove button
	$(document).on("click", ".btn-tag-remove", function(e) {
		e.stopPropagation(); e.preventDefault();
		removeTag($(this).parent());
	});
	$(document).on("click", ".panel-translation-edit", function(e) {
		e.stopPropagation();
	});
	// add tag
	$(document).on("submit", ".panel-translation-edit form", function(e) {
		e.stopPropagation();
		e.preventDefault();
		addTag($(this));
	});
	
});

