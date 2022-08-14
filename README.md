# Introduction

This is a sample application to fetch newsItems from newsapi.org source and display in the app.

# Highlights

* 2 Launchers in debug mode, `NewsCorpSampleApp` for main app and `NewsCorpAppConfig` for switching to mock mode. Mock mode doesn't require network connectivity. Once the mock mode is selected app is killed and launched again.
* Theming has been done and dark mode supported. The colors of dark mode is kept fancy, only to highlight the theming. These are not the right colors. (See the screenshots)
* App has bottom navigation bar with multiple backstacks. So each tab will have independent UI flows. 
* Once you click any news article it goes to news details page to show in web. The `save` button is present at top right in the toolbar.
* Currently, save and unsave article can only be done from news web page. At top right there is action button to do that. 
* For the scope of this app, only AU country has been selected for showing news. This can be extended to all countries later. `newsapi.org` API doesn't support mixing countries and sources.

# About the app

* This app uses MVVM with Hilt library for Dependency injection
* Used Retrofit and OkHttp libraries in the networking layer. Moshi is used for parsing json.
* RecyclerView adapter is based on https://github.com/google/android-ui-toolkit-demos/tree/master/DataBinding/DataBoundRecyclerView . This  recycler view adapter helps us to do databinding easily and no need to write separate viewHolders. Another good way is use to use, https://github.com/airbnb/epoxy.
* Using `liveData` to communicate between ViewModel to Fragment. StateFlow is the new alternative but , as per this article , `liveData` is still a good option. For the scope of this app, there won’t be any issues with `StateFlow`though.
  https://bladecoder.medium.com/kotlins-flow-in-viewmodels-it-s-complicated-556b472e281a
* NetworkModule has been kept for specific build variants to provide different implementation for mock mode and normal mode.
* Mock resources are available only in debug variant of the build and resources are kept in debug specific folder.
* The headlines is identified by its title and URL. This is because there is no unique id for an article. If there is another article with same title and URL it will not be considered for saving article. For the scope of the use case this should be logical.

# What needs to be done

* Haven’t done signing config for release build.
* Unit test has been done for headlines view model and further layers. Fragment is not unit tested (usually needs RoboElectric setup). Extensve unit test needs to be done for all the scenarios and do a code coverage for viewModels and models
* The network layer has a basic implementation to pass on the http error code based data to the viewModel. Ideally the repository or if there is an Uncle Bob like use case layer, we can avoid passing error code to the viewModel layer. But for now viewModel handles the error logic. The error body response also is not parsed now. This can be wrapped and passed by NetworkHub layer, when needed.
* `SharedPreferences` needs to be replaced by `DataStore`.
* Mock mode can be used for for running stable Instrumentation tests using Espresso. Need to add those UI tests.
* Network error for no connectivity and server error has not been handled separately. Currently network error is handled in generic way.
* Need to move all the dependency version to ext in build.gradle ( few has been done for demonstration)




