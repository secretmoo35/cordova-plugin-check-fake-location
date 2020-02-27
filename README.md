# cordova-plugin-check-fake-location

This plugin is used to detect fake location for Android.

Test on Android 5+


## Use

```sh

declare var cordova: any;


checkFakeLocation(){

    cordova.plugins.DetectFakeLocationPlugin.checkAllowMockLacation({}, (res: any) => {
        // alert(JSON.stringify(res));
        if (res.status) {
            alert('Fake GPS');
            setTimeout(() => {
            this.checkFakeLocation();
            }, 3000);
        }
    }, (err: any) => {
        // alert('Error : ' + err)
        console.log(err);
    });

}


```