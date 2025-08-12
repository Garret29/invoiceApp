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
        let url = $scope.url + "api/invoices";
        $http.post(url, $scope.invoice, {
            responseType: 'arraybuffer', // tu
            headers: {
                "Content-Type": "application/json"
            },
            transformResponse: []
        }).then(function (response) {
            var file = new Blob([response.data], { type: 'application/pdf' });
            var fileURL = URL.createObjectURL(file);
            var a = document.createElement('a');
            a.href = fileURL;
            a.download = $scope.invoice.id + ".pdf";
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(fileURL);
        }, function (error) {
            console.error("Błąd pobierania PDF:", error);
        });
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

    this.$onInit = ()=>{
        $scope.url =$scope.url.split("?")[0]
    }

});
