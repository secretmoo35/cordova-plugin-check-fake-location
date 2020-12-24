var exec = require('cordova/exec');

module.exports.checkAllowMockLacation = function (success, error) {
  exec(success, error, 'DetectFakeLocationPlugin', 'checkIsMockLocation', []);
};
