angular.module('gbank-front').controller('registerController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:5555/auth/';
    $scope.tryToRegisterUserAccount = function () {
        $http({
            url: contextPath + 'user',
            method: 'POST',
            data: $scope.user
        }).then(function (response) {
            console.log(response);
            alert("Пользователь "+ response.data.username + " успешно зарегистрирован");
            $location.path('/');

        })
    };
});
