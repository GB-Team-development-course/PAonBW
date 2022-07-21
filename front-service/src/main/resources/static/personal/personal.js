angular.module('gbank-front').controller('personalController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    $scope.creditInfo = undefined;
    $scope.debitInfo = undefined;
    $scope.loadAccounts = function () {
        $http({
            url: contextPath + 'api/v1/account/',
            method: 'GET'})
            .then(function (response) {
            console.log(response.data.data)
            $scope.AccountsPage = response.data.data;
        });
    };
    $scope.blockAccount = function (accountNumber) {
        $http.put(contextPath + 'api/v1/account/block/' + accountNumber)
            .then(function (response) {
                $scope.loadAccounts();
            });
    }
    $scope.closeAccount = function (accountNumber) {
        $http.put(contextPath + 'api/v1/account/close/' + accountNumber)
            .then(function (response) {
                if (response.data.code===204){
                    alert("Счёт невозможно закрыть")
                }
                $scope.loadAccounts();
            });
    }

    $scope.createCreditAccount = function () {
        $http({
            url: contextPath + 'api/v1/account/credit',
            method: 'POST',
            data: $scope.creditInfo
        }).then(function (response) {
            $scope.creditInfo = null;
            $scope.loadAccounts();
        });
    };
    $scope.createDebitAccount = function () {
        $http({
            url: contextPath + 'api/v1/account/debit',
            method: 'POST',
            data: $scope.debitInfo
        }).then(function (response) {
            $scope.debitInfo = null;
            $scope.loadAccounts();
        });
    };


    $scope.loadAccounts();
});