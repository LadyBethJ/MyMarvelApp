
# Marvel API Test application:

Application which use the Marvel API, showing a list of characters and a detail view.

## Structure

The structure of the project follows the CLEAN architecture, whose main directories are the following:
- __data__: Contains the repositories Implementations and one or multiple Data Sources.
- __domain__: Collection of entity objects and related business logic that is designed to represent the enterprise business model.
- __infrastructure__: contains the dependency injector package, and other like exceptions, utils, etc.
- __presentation__: which contains all the classes related to the UI of the app (activities, fragments, vm, etc)

## IMPORTANT!! apikey.properties configuration

In order to run the application, you need to include in the root directory a new "apikey.properties" file with the following values:

        BASE_URL="https://gateway.marvel.com/v1/public/"
        PUBLIC_KEY="your public api"
        TS="MyMarvelApp"
        HASH="your API hash"

For more information, check the [Marvel's developer site](https://developer.marvel.com/documentation/getting_started)
