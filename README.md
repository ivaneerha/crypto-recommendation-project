CryptoService class:

methods:

Optional<CryptoData> getOldest(CryptoEnum symbol):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if there are values with this symbol it calculates the oldest value for this symbol

Optional<CryptoData> getNewest(CryptoEnum symbol):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if there are values with this symbol it calculates the newest value for this symbol

Optional<CryptoData> getCryptoWithMinPrice(CryptoEnum symbol):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if there are values with this symbol it calculates and returns the value of the object with the lowest price

Optional<CryptoData> getCryptoWithMinPriceForGivenMonth(CryptoEnum symbol, LocalDateTime date):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if the date is null or there are not values with this date the method returns an empty optional
- if there are values with this symbol it calculates and returns the value of the object with the lowest price for that year+month+symbol relation

Optional<CryptoData> getCryptoWithMaxPrice(CryptoEnum symbol):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if there are values with this symbol it calculates and returns the value of the object with the highest price

Optional<CryptoData> getCryptoWithMaxPriceForGivenMonth(CryptoEnum symbol, LocalDateTime date):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if the date is null or there are not values with this date the method returns an empty optional
- if there are values with this symbol it calculates and returns the value of the object with the highest price for that year+month+symbol relation

List<CryptoData> getNormalized():
- if there is loaded data with cryptos it returns a descending sorted list of all the cryptos, compared by the normalized range
- if there isn't loaded data with cryptos it returns an empty list

Optional<CryptoData> getHighestNormalizedRangeForASpecificDay(LocalDate date):
- if there is loaded data with cryptos with this date the it returns the crypto with the highest normalized range
- if there isn't loaded data with cryptos this such date it returns an empty optional

List<CryptoData> getAllCryptosFromDateToNow(CryptoEnum symbol, LocalDateTime date):
- if the symbol is null or there are not values with this symbol the method returns an empty optional
- if the date is null or there are not values with this date or after this date the method returns an empty optional
- if there are values with this symbol and with this date or after this date the method returns all that values


CsvParser class:

parse(String filePath):
- reads csv files and produces a list of crypto objects