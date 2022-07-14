angular.module('gbank-front').controller('personalController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
    $scope.creditInfo = undefined;
    $scope.debitInfo = undefined;
    $scope.loadAccounts = function () {
        $http({
            url: contextPath + 'api/v1/account/',
            method: 'GET'})
            .then(function (response) {
            console.log(response.data)
            $scope.AccountsPage = response.data;
        });
    };
    $scope.blockAccount = function (accountNumber) {
        $http.put(contextPath + 'api/v1/account/blockAccount/' + accountNumber)
            .then(function (response) {
                $scope.loadAccounts();
            });
    }
    $scope.closeAccount = function (accountNumber) {
        $http.put(contextPath + 'api/v1/account/closeAccount/' + accountNumber)
            .then(function (response) {
                $scope.loadAccounts();
            });
    }

    $scope.createCreditAccount = function () {
        $http({
            url: contextPath + 'api/v1/account/createCredit',
            method: 'POST',
            data: $scope.creditInfo
        }).then(function (response) {
            $scope.creditInfo = null;
            $scope.loadAccounts();
        });
    };
    $scope.createDebitAccount = function () {
        $http({
            url: contextPath + 'api/v1/account/createDebit',
            method: 'POST',
            data: $scope.debitInfo
        }).then(function (response) {
            $scope.debitInfo = null;
            $scope.loadAccounts();
        });
    };


    $scope.loadAccounts();
});