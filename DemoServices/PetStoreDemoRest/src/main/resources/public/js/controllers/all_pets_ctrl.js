angular.module('AddPetServices', ['ngResource']).factory('addPet', ['$resource',
function($resource) {
  return $resource('api/v1/pet', {
    pet : '@pet'
  }, {
    save : {
      method : 'POST'
    }
  });
}]);

angular.module('BasicPetServices', ['ngResource']).factory('pets', ['$resource',
  function($resource){
    return $resource('api/v1/pet/:petId', {petId:'@id'});
  }]);

// Create main controller
angular.module('petStoreDemoApp', ['AddPetServices', 'BasicPetServices', 'ui.bootstrap']).controller('allPetsCtrl',
      function($scope, $http, $uibModal, $log, addPet, pets) {

  // Regex for Pet Name
  $scope.id_regex = "[0-9]+";
    
  // Number of selected pets
  $scope.selected_cnt = 0;

  // Map of selected only pet in format pet.id => pet
  $scope.selected = {};

  // Status of each selected pet in format pet.id => true/false
  $scope.checked = {};

  $scope.$on('userMask', function (event, data) {
     $scope.user_mask = data.user_mask;
  });

  $scope.reset_selection = function() {
    $scope.selected = {};
    $scope.checked = [];
    $scope.selected_cnt = 0;
  };
  
  $scope.find = function() {
    pets.query({petId: $scope.pet_id}, function(pet) {
      $scope.all_pets = new Array(pet);
      reset_selection();
    });
  };
  
  $scope.reset = function() {
    $http.get("api/v1/pets").then(function(res) {
      $scope.all_pets = res.data;
    }, function() {});
  };
  
  $scope.reset();
  
  // Visibility function for buttons since AngularJs doesn't support bitwise operators
  $scope.is_visible = function(mask) {
    return $scope.user_mask !== undefined 
      ? ($scope.user_mask & mask) > 0
      : false;
  };
   
  $scope.toggle_selection = function(pet) {
    if ($scope.checked[pet.id]) {
      $scope.selected[pet.id] = pet;
      $scope.selected_cnt++;
    } else {
      delete $scope.selected[pet.id];
      $scope.selected_cnt--;
    }
  };

  $scope.delete_pets = function() {
    if (!confirm("Please confirm deleteion of " +
        $scope.selected_cnt + " pet" + ($scope.selected_cnt > 1 ? "s" : "")))
      return;
    
    // Collect pet id's
    for (key in $scope.selected) {
      var pet = $scope.selected[key];
      
      (function(pet){
        pets.delete({petId: pet.id}, function() {
          var idx = $scope.all_pets.indexOf(pet);
        
          $scope.all_pets.splice(idx, 1);
          delete $scope.selected[pet.id];
          $scope.checked[pet.id] = false;
          $scope.selected_cnt--;
        });
      })(pet);
    }
  };

  $scope.add_pet_form = function(pet) {
    var modalInstance = $uibModal.open({
      animation : false,
      templateUrl : 'new_pet_form.html',
      controller : 'ModalInstanceCtrl',
      resolve : {
        new_pet : pet,
      }
    });

    modalInstance.result.then(function(pet) {
      // Finally adding a pet
      addPet.save(pet, function(id) {
        pet.id = Number(id[0]);
        $scope.all_pets.push(pet);
        $log.info('Pet ' + pet.name + ' saved at: ' + new Date() + " with id: " + pet.id);
      });

      $log.debug('Pet ' + pet.name + ' sent at: ' + new Date());
    }, function() {
      $log.debug('Modal dismissed at: ' + new Date());
    });
  };
});
