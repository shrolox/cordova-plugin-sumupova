function init(success, error, opts) {
	console.log("Sumupova.init() : Sumupova is not available on Browser, but calls are tolerated")
}

function login(success, error, opts) {
	console.log("Sumupova.login() : Sumupova is not available on Browser, but calls are tolerated")
}

function pay(success, error, opts) {
	console.log("Sumupova.pay() : Sumupova is not available on Browser, but calls are tolerated")
}

function coolMethod(success, error, opts) {
    console.log("Sumupova.coolMethod() : Sumupova is not available on Browser, but calls are tolerated")
};

module.exports = {
  init: init,
  login: login,
  pay: pay,
  coolMethod: coolMethod
};

require('cordova/exec/proxy').add('Sumupova', module.exports);