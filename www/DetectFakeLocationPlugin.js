var exec = require('cordova/exec');

module.exports.checkAllowMockLacation = function (arg0, success, error) {
    exec(success, error, 'DetectFakeLocationPlugin', 'checkIsMockLocation', [arg0]);
};