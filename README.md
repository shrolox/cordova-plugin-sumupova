# cordova-plugin-sumupova

This plugin integrates the Sumup SDK into Ionic apps. It works with both Android and iOS

## Installation
Install using the ionic CLI
```
ionic cordova plugin add cordova-plugin-sumupova
```

## Usage
Initialize the plugin with your Sumup merchant key
```
Sumupova.init([YOUR_KEY])
```

Display the login page for the cashier
```
Sumupova.login()
```

Initialize the payment
```
Sumupova.pay([PAYMENT_AMOUNT_AS_STRING], [COMPANY_NAME], [CURRENCY_NAME], function () {
  // PAYMENT SUCCESS
}, function (error) {
  // ERROR DURING PAYMENT
});
```
Display Sumup settings page
```
Sumupova.presentPreferences()
```
