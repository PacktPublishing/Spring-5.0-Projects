$(function(){
	$("#cancel-form").on('click', function(){
		history.back();
	});
	
	var formId = "#country-form";
	$(formId).validate({
		errorClass : "is-invalid",
		validClass : "is-valid"
	});

	$("#save-form").on('click', function(){
		if ( $(formId).valid()){
			var requestBody = {};
			$.each($(formId).serializeArray(), function(){
				var obj = $(this)[0];
				if ( obj.name.indexOf(".") >= 0){
					var nestedObj = obj.name.split(".")[0];
					var nestedObjProp = obj.name.split(".")[1];
					if ( !requestBody[nestedObj]){
						requestBody[nestedObj] = {};
					}
					requestBody[nestedObj][nestedObjProp] = obj.value;
				}else{
					requestBody[obj.name] = obj.value;
				}
				
			});
			$.ajax({
				method: "POST",
				url: $(formId).attr("action"),
				data: JSON.stringify(requestBody),
				contentType: "application/json",
				success : function(response) {
					toastr.success("Country edited successfully");
					//location.reload();
				},
				error : function(response) {
					if ( response.responseJSON){
						toastr.error(response.responseJSON.message);
					}else{
						toastr.error(response.responseText);
					}
				}
			});
		}
		return false;
	});
});