const app = angular.module('invoiceApp', []);

app.controller('controller', function ($scope) {
    $scope.invoice =
        {
            invoiceCity:" ",
            invoiceDate:" ",
            invoiceShippingDate:" ",
            paymentDate:" ",
            paymentMethod:" ",
            id:" ",
            provider:{
                providerName:" ",
                providerStreet:" ",
                providerHouse:" ",
                providerApartment:" ",
                providerCity:" ",
                providerPostalCode:" ",
                providerNIP:" ",
                providerBankNumber:" ",
                providerPhoneNumber:" ",
                providerEmail:" ",
                providerBank:" "
            },
            buyer:{
                buyerName:" ",
                buyerStreet:" ",
                buyerHouse:" ",
                buyerAppartment:" ",
                buyerCity:" ",
                buyerPostalCode:" "
            },
            goods:[
                {
                    name:" ",
                    quantity:0.0,
                    unit:" ",
                    priceNetOfUnit:0.0,
                    discount:0.0,
                    priceNetOfUnitAfterDiscount:0.0,
                    priceNetto:0.0,
                    priceGross:0.0,
                    vatRate:0,
                    vat:0.0
                }
            ],
            gross:0.0,
            vat:0.0,
            netto:0.0,
            bruttoWords:" ",
            vat_0:0.0,
            vat_5:0.0,
            vat_8:0.0,
            vat_23:0.0,
            net_0:0.0,
            net_5:0.0,
            net_8:0.0,
            net_23:0.0,
            gross_0:0.0,
            gross_5:0.0,
            gross_8:0.0,
            gross_23:0.0
        }

});
