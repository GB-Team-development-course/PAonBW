angular.module('gbank-front').controller('personalController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/'

    $scope.creditInfo = undefined;
    $scope.debitInfo = undefined;
    $scope.transferInfo = undefined;

    $scope.loadCurrencies = function () {
        $http({
            url: contextPath + 'api/v1/currency/',
            method: 'GET'})
            .then(function (response) {
                console.log(response.data.data)

                $scope.creditInfo = {
                    currency: null,
                    currenciesList: response.data
                };
                $scope.debitInfo = {
                    currency: null,
                    currenciesList: response.data
                };

                $scope.transferInfo = {
                    currency: null,
                    currenciesList: response.data
                };
            });

    };

    $scope.loadProducts = function () {
        $http({
            url: contextPath + 'api/v1/product/credit',
            method: 'GET'})
            .then(function (response) {
                console.log(response.data.data)
                $scope.creditInfo.productId = null;
                $scope.creditInfo.productList = response.data

            });

        $http({
            url: contextPath + 'api/v1/product/debit',
            method: 'GET'
        })
            .then(function (response) {
                console.log(response.data.data)
                $scope.debitInfo.productId = null;
                $scope.debitInfo.productList = response.data
            });
    };

    $scope.loadAccounts = function () {
        $http({
            url: contextPath + 'api/v1/account/',
            method: 'GET'})
            .then(function (response) {
            console.log(response.data.data)
            $scope.AccountsPage = response.data.data;
        });
    }

    $scope.blockAccount = function (accountNumber) {
        $http({
            url: contextPath + 'api/v1/account/block/' + accountNumber,
            method: 'PUT'
        })
            .then(function (response) {
                alert("Счёт " + response.data.data.accountNumber + " заблокирован")
                $scope.loadAccounts();
            })
            .catch(function (response) {
                    alert(response.data.data)
                }
            );
    }

    $scope.closeAccount = function (accountNumber) {
        $http.put(contextPath + 'api/v1/account/close/' + accountNumber)
            .then(function (response) {
                alert("Счёт " + response.data.data.accountNumber + " закрыт");
                $scope.loadAccounts();
            })
            .catch(function (response) {
                alert(response.data.data)
            });
    }

    $scope.createCreditAccount = function () {
        $http({
            url: contextPath + 'api/v1/account/credit',
            method: 'POST',
            data: $scope.creditInfo
        }).then(function (response) {
            $scope.creditInfo.productId = null;
            $scope.creditInfo.currency = null;
            $scope.loadAccounts();
        });
    };

    $scope.createDebitAccount = function () {
        $http({
            url: contextPath + 'api/v1/account/debit',
            method: 'POST',
            data: $scope.debitInfo
        }).then(function (response) {
            $scope.debitInfo.productId = null;
            $scope.debitInfo.currency = null;
            $scope.loadAccounts();
        });
    };

    $scope.transferMoney = function () {
        $http({
            url: contextPath + 'api/v1/order',
            method: 'POST',
            data: $scope.transferInfo
        }).then(function (response) {
            $scope.transferInfo = null;
            $scope.loadAccounts();
        }).catch(function (response) {
            alert(response.data.data)}
        )
    };

    $scope.loadCurrencies();

    $scope.loadProducts();

    $scope.loadAccounts();


});
