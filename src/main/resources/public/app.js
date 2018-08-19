const app = angular.module('invoiceApp', []);

app.controller('controller', function ($scope, $window, $http, $location) {

    $scope.id = null;
    $scope.docUrl = null;
    $scope.url = $location.absUrl();
    $scope.userId = null;
    $scope.save = false;
    $scope.logged = false;

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
                "buyerPostalCode": "",
                "buyerNIP": ""
            },
            "goods": [
                {
                    "name": "",
                    "quantity": null,
                    "unit": "",
                    "priceNetOfUnit": null,
                    "discount": 0.0,
                    "priceNetOfUnitAfterDiscount": 0.0,
                    "priceNetto": 0.0,
                    "priceGross": 0.0,
                    "vatRate": 0.0,
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
        $http({
            url: $scope.url + "/api?save="+$scope.save,
            dataType: "json",
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify($scope.invoice)
        }).then(function (response) {
            $scope.id = response.data;
            $scope.docUrl = $scope.url + "files/" + response.data;
        })
    };

    $scope.loadFromDB = function () {
        $http(
            {
                url: $scope.url + "/api/keys?key="+$scope.userId,
                method: "GET"
            }
        ).then(function (response) {
            $scope.invoice=response.data;
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
    };

    $scope.loadBankNameFromApi = function () {
        console.log("http://jakitobank.pl/api/?numer=PL"+$scope.invoice.provider.providerBankNumber);
      $http({
          method: "GET",
          url: "http://jakitobank.pl/api/?numer=PL"+$scope.invoice.provider.providerBankNumber,
          crossOrigin: true
      }).then(function (response) {
          $scope.providerBank = response.data["nazwa_banku"]
      })
    };

    $scope.updateProviderDataFromApi = function () {

        $http({
            method: "GET",
            url: "https://api-v3.mojepanstwo.pl/dane/krs_podmioty.json?conditions[krs_podmioty.nip]=" + $scope.invoice.provider.providerNIP
        }).then(function (response) {
            const json = response.data.Dataobject[0].data;
            $scope.invoice.provider.providerApartment = json["krs_podmioty.adres_lokal"];
            $scope.invoice.provider.providerStreet = json["krs_podmioty.adres_ulica"];
            $scope.invoice.provider.providerCity = json["krs_podmioty.adres_miejscowosc"];
            $scope.invoice.provider.providerName = json["krs_podmioty.nazwa"];
            $scope.invoice.provider.providerHouse = json["krs_podmioty.adres_numer"];
            $scope.invoice.provider.providerPostalCode = json["krs_podmioty.adres_kod_pocztowy"];
        })
    }

});
