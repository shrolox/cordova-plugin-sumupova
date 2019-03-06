var exec = require('cordova/exec');

exports.init = function (key) {
	exec(null, null, 'Sumupova', 'initPlugin', [key]);
}

exports.login = function () {
	exec(null, null, 'Sumupova', 'login', []);
}

exports.presentPreferences = function () {
	exec(null, null, 'Sumupova', 'presentPreferences', []);
}

exports.pay = function (price, title, currencyCode, success, error) {
	console.log("INSIDE SUMUPOVA")
	exec(success, error, 'Sumupova', 'paySumup', [price, title, currencyCode]);
}
