@objc(Sumupova) class Sumupova : CDVPlugin {
    func initPlugin(_ command: CDVInvokedUrlCommand) {
        let key : String? = command.arguments[0] as? String
        
        if (key != nil) {
            SumUpSDK.setup(withAPIKey: key!)
        }
    }

    func login(_ command: CDVInvokedUrlCommand) {
        SumUpSDK.presentLogin(from: self.viewController, animated: true, completionBlock: nil);
    }

    func presentPreferences(_ command: CDVInvokedUrlCommand) {
        SumUpSDK.presentCheckoutPreferences(from: self.viewController, animated: true, completion: nil)
    }
    
    func paySumup(_ command: CDVInvokedUrlCommand) {
        let price : String? = (command.arguments[0] as? String)
        let title : String? = (command.arguments[1] as? String)
        let currency : String? = (command.arguments[2] as? String)
        let amount = NSDecimalNumber(string: (price))
        let request = CheckoutRequest(total: amount , title: (title ?? ""), currencyCode: (currency ?? "EUR"), paymentOptions: [])
        SumUpSDK.checkout(with: request, from: self.viewController, completion: { (result, error) in
            if ((result?.success)!) {
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_OK)
                self.commandDelegate!.send(pluginResult ,callbackId: command.callbackId)
            } else {
                let pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR)
                self.commandDelegate!.send(pluginResult ,callbackId: command.callbackId)
            }
        })
    }
}
