function success(message) {
	toastr.success(message);
}

function error(message) {
	toastr.error(message);
}

function warning(message) {
	toastr.warning(message);
}

var queryString = (function(a) {
	if (a == "")
		return {};
	var b = {};
	for (var i = 0; i < a.length; ++i) {
		var p = a[i].split('=');
		if (p.length != 2)
			continue;
		b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
	}
	return b;
})(window.location.search.substr(1).split('&'));