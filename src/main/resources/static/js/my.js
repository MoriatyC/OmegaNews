$('.category').on('click', function() {	
	$.ajax({
		url : '/category',
		type : 'get',
		dataType : 'text',
		data : {
			"id" : $(this).attr("id")
		},
		success : function(data) {
			$('#replacement').html(data);
		}
	});
})

