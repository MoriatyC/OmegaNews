$('.category').on('click', function() {
	var id = $(this).attr("id");	
	$(this).attr("href",id + ".html");
	alert(id + ".html");
	console.log(this.attr("href"));
})

