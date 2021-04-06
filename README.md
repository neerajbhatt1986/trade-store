This Trade Store application is SpringBoot Application. For the application and testcase H2 database is used.   

This application contains two api

1) Get Trades: http://localhost:8080/trades
2) Save Trade (Add or update the Trade): http://localhost:8080/trade/save

Payload

    {
        "tradeId": "T2",
        "tradeVersion": 9,
        "counterPartyId": "CP-9",
        "bookingId": "B2",
        "maturityDate": "12/12/2021"
    }
    


How to run the application

1) Clone the project
2) run maven clean install. It will generate a jar file in the target dir
3) To run the application run below command
    java -jar target/tradeStore-0.0.1-SNAPSHOT.jar
   
   
  