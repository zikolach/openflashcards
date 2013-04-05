
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

$(document).ready(function() {
	$(document).on("click", ".action.like,.action.dislike", function(e) {
		e.preventDefault();
		toggleLike($(this));
	});
	$(document).on("click", ".action.view", function(e) {
		e.preventDefault();
		loadFlashcard($(this));
	});
	$("#search-form").on("submit", function(e) {
		e.preventDefault();
		loadFlashcards($(this));
	});
	$("#add-form").on("submit", function(e) {
		e.preventDefault();
		addFlashcard($(this));
	});
	$(document).on("click", ".action.tags", function(e) {
		e.preventDefault();
		alert("aaaa");
		$("#tags-form").show();
	});
	$(document).on("click", ".translation", function(e) {
		//$("#translation-edit").toggle();
		e.preventDefault();
		$(this).toggleClass("closed");
		$(this).find(".panel-translation-edit ").slideToggle("fast");
		
	});
	$(document).on("click", ".btn-tag-remove", function(e) {
		e.stopPropagation();
		e.preventDefault();
		//alert("");
		var tag = $(this).parent(); 
		$.ajax(tag.data("url"), {
			type: "DELETE",
			success: function(result) {
				tag.remove();
			}
		});
		
	})
	$(document).on("click", ".tag", function(e) {
		e.stopPropagation();
		e.preventDefault();		
	});
	$(document).on("click", ".panel-translation-edit", function(e) {
		e.stopPropagation();
	});
	$(document).on("submit", ".panel-translation-edit form", function(e) {
		e.stopPropagation();
		e.preventDefault();
		var form = $(this);
		$.ajax(form.attr("action"), {
			type: form.attr("method"),
			data: form.serialize(),
			success: function(result) {
				//alert(result);
				form.closest(".panel-translation-edit ").find(".tags").append(result);
			},
			complete: function() {
				form.find("input[name='tagName']").val("");
			}
		});
		
	});
	
});

