var vueJs;
$(function(){
	$('#linksModal').on('shown.bs.modal', function (event) {
		vueJs.newLink.title = "";
		vueJs.newLink.url = "";
		vueJs.newLink.description = "";
		vueJs.newLink.category = "";
	});
	
	vueJs = new Vue({
		"el" : "#page_content",
		"data": {
			newLink: {
				title: "",
				url: "",
				description: "",
				category: ""
			}
		}, 
		"methods":{
			"saveLink": function(){
				var formValid = $("#new-link-form").valid();
				if ( formValid){
					console.log(this.newLink);
					$.ajax({
						type: "POST",
						url: "/api/links",
						data: JSON.stringify(this.newLink),
						contentType: "application/json",
						success: function(){
							success("Link created successfully");
						},
						error: function(){
							error("Unable to save the link details");
						}
					});
				}
				
			}
		}, 
		"mounted": function(){
			$("#new-link-form").validate({
				errorClass : "is-invalid",
				validClass : "is-valid"
			});
		}
	});
});