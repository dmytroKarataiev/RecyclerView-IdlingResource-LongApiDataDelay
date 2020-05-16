# RecyclerView-IdlingResource-LongApiDataDelay
Shows a simple example of a UI test that waits for the API to return data and then starts the test

## Why?
https://stackoverflow.com/questions/50628219/is-it-possible-to-use-espressos-idlingresource-to-wait-until-a-certain-view-app/60753790#60753790
Shows a simple example how it can be used to test a RecyclerView with an API that has a very long delay (e.g. 35 seconds before it returns any data). 
