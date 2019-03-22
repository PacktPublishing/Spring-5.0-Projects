var pageSize = 20;
$(function(){
	var count = $("#pagination").attr("count");
	$('#pagination').bootpag({				
	    total: Math.ceil(count/pageSize),
	    page: queryString['pageNo']?queryString['pageNo'] : 1 ,
	    maxVisible: 5,
	    leaps: true
	}).on("page", function(event, num){
	    //list(num); 
		queryString['pageNo'] = num;
		console.log(queryString);
		console.log( $.param( queryString ));
		reloadPage();
	});
	
	
	$(".filter").on('change', function(e){
		var key = $(e.target).attr("name");
		var value = $(e.target).val();
		handleSearch(key, value);
	});
	
});

function handleSearch(key, value){
	//console.log(key + ", " + value);
	if ( value){
		queryString[key] = value;
	}else{
		delete queryString[key];
	}
	reloadPage();
}

function reloadPage(){
	location.href = location.pathname + "?" + $.param(queryString);
}

