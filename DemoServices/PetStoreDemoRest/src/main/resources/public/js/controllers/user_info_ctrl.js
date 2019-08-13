// Create main controller
angular.module('petStoreDemoApp').controller('userInfoCtrl',
      function($scope, $http, $log) {

  $http.get("api/v1/user_info").then(function(res) {
    $scope.user_info = {
      mask: parseInt(res.data.mask,2),
      name: res.data.name
    };
    
    $scope.$parent.$broadcast('userMask', {
      user_mask: $scope.user_info.mask
    });

    $log.debug("User Mask: " + $scope.user_info.mask);
  }, function() {
  });
});
