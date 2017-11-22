var category;
$(document).on('click', '.category',function() {
	category = $(this).attr("id");
	$.ajax({
		url : '/category',
		type : 'get',
		dataType : 'text',
		data : {
			"id" : category,
			"page":0,
			"size":20
		},
		success : function(data) {
			$('#replacement').html(data);
		}
	});
})

$(document).on('click', '.mypage',function() {
	var x = parseInt($(this).children("span").html());
	var page;
	if ($(this).html()=="»") {
		var cur = $('.active').children("span").html();
		page = cur;
	} else if ($(this).html()=="«") {
		var cur = $('.active').children("span").html();
		page = cur - 2;
	} else if($(this).html()=="← First") {
		page = 0;
	} else if ($(this).html()=="Last →") {
		page =  parseInt($(this).attr("size")) - 1;
	} else {
		page = x - 1;
	}
	$.ajax({
		url : '/category' ,
		type : 'get',
		dataType : 'text',
		data : {
			"id" : category,
			"page":page,
			"size":20
		},
		success : function(data) {
			$('#replacement').html(data);
		}
	});
})
$(document).on('click', '.article a',function() {
	var id = $(this).attr('id');
		$.ajax({
		url : '/article/' + id,
		type : 'get',
		dataType : 'text',
		success : function(data) {
			$('#replacement').html(data);
		}
	});
})