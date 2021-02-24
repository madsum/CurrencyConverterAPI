# Currency Converter API

This is implemented by open JDK 15 and spring boot 2.4.3

This demo API is hosted at Heroku. Swagger UI is added to test API easily.

https://demo-currency-converter.herokuapp.com/swagger-ui/#/

There are 2 GET RESTful API exposed. Please try it by the above Swagger UI or simply by curl

curl -X
GET "https://demo-currency-converter.herokuapp.com:443/api/v1/revisions/exchange-currency?localCurrency=eur&exCurrency=usd&amount=100.50"
-H "accept: application/json"

curl -X GET "https://demo-currency-converter.herokuapp.com:443/api/v1/revisions/all-supported-currency?baseCurrency=eur"
-H "accept: application/json"  
