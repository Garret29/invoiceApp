const app = angular.module('invoiceApp', []);

app.controller('controller', function ($scope, $window, $http, $location) {

    $scope.id = null;
    $scope.docUrl=null;
    $scope.url = $location.absUrl();


    $scope.invoice =
        {
            "invoiceCity": "",
            "invoiceDate": "",
            "invoiceShippingDate": "",
            "paymentDate": "",
            "paymentMethod": "",
            "id": "",
            "provider": {
                "providerName": "",
                "providerStreet": "",
                "providerHouse": "",
                "providerApartment": "",
                "providerCity": "",
                "providerPostalCode": "",
                "providerNIP": "",
                "providerBankNumber": "",
                "providerPhoneNumber": "",
                "providerEmail": "",
                "providerBank": ""
            },
            "buyer": {
                "buyerName": "",
                "buyerStreet": "",
                "buyerHouse": "",
                "buyerApartment": "",
                "buyerCity": "",
                "buyerPostalCode": ""
            },
            "goods": [
                {
                    "name": "",
                    "quantity": 0.0,
                    "unit": "",
                    "priceNetOfUnit": 0.0,
                    "discount": 0.0,
                    "priceNetOfUnitAfterDiscount": 0.0,
                    "priceNetto": 0.0,
                    "priceGross": 0.0,
                    "vatRate": 0,
                    "vat": 0.0
                }
            ],
            "gross": 0.0,
            "vat": 0.0,
            "netto": 0.0,
            "bruttoWords": "",
            "vat_0": 0.0,
            "vat_5": 0.0,
            "vat_8": 0.0,
            "vat_23": 0.0,
            "net_0": 0.0,
            "net_5": 0.0,
            "net_8": 0.0,
            "net_23": 0.0,
            "gross_0": 0.0,
            "gross_5": 0.0,
            "gross_8": 0.0,
            "gross_23": 0.0
        };

    $scope.getPdfInvoice = function () {
        /*

        $.ajax({
            url: "http://localhost:8080/api",
            method: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "JSON",
            data: JSON.stringify($scope.invoice),
            success: function () {
                alert("hello")
            }
        });
        */

        $http({
            url: $scope.url+"/api",
            dataType: "json",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify($scope.invoice)
        }).then(function (response) {
            $scope.docUrl=$scope.url+"/api?name="+$scope.invoice.id+".pdf"
        })
    };

    $scope.loadFromDB = function () {
        $.ajax({
            url: "",
            method: "post",
            contentType: "text/plain",
            data: $scope.id
        })
    };

    $scope.addGood = function () {
        $scope.invoice.goods.push({
            name: "",
            quantity: 0.0,
            unit: "",
            priceNetOfUnit: 0.0,
            discount: 0.0,
            priceNetOfUnitAfterDiscount: 0.0,
            priceNetto: 0.0,
            priceGross: 0.0,
            vatRate: 0,
            vat: 0.0
        })
    };

    $scope.removeGood = function (index) {
        $scope.invoice.goods.splice(index, 1)
    }


});
