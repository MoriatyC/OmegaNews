var category;
$(document).on('click', '.category',function() {
	category = $(this).attr('id');
	window.location.hash='category-' + $(this).attr('id');
})

$(document).on('click', '.article a',function() {
	window.location.hash='article-' + $(this).attr('id');
})



window.onhashchange=function(){
			var hash = window.location.hash;	
			hashList = hash.substr(1).split('-')
			if(hashList[0] == 'category'){
				var id = hashList[1]
				$.ajax({
					url : '/category/',
					type : 'get',
					dataType : 'text',
					data : {
						"id" : id,
						"page":0,
						"size":20
					},
					success : function(data) {
						$('#replacement').html(data);
					}
				});
			} else if (hashList[0] == 'article') {
				var id = hashList[1]
				$.ajax({
					url : '/article/' + id,
					type : 'get',
					dataType : 'text',
					success : function(data) {
						$('#replacement').html(data);
					}
				});
				
			} else if (hashList[0] == 'page') {
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
			}
		};


		$(document).on('click', '.glyphicon-thumbs-up',function() {
			var id = $(this).attr('id');
			$.ajax({
				url : '/upvote/' + id + "/",
				type : 'get',
				dataType : 'text',
				success : function(data) {
					
				}
			});
		})
//分页器
var page;
$(document).on('click', '.mypage',function() {
	var x = parseInt($(this).children("span").html());

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
	window.location.hash='page-' + page;
})
