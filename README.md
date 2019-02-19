---
typora-copy-images-to: ipic
---

# Hire Me News

Hire Me News is a sample News reader project that showcases Android development best practices under a MVVM architecture while also making you informed of the latest news!

(If you think this app is cool, hire me! 😀 )

### Architecture

This project follows the MVVM architecture making use of most of Android Jetpack components and RxJava, to make a clean, easy to understand, and maintainable reactive structure.

##### Layers

In this architecture, the `Fragments` act as the **View layer**, while we use subclasses of the `ViewModel` from Google's architecture components as the **ViewModel layer**.

To deal with data sets, the `Repository` pattern is applied, which controls possible different data sources and maps the entities into domain objects. For now, the only data source is the network. Meaning that we use databases to store the retrieved data. 

Linking everything together is a reactive stream of `Observables` from RxJava. The API returns the events, which get mapped and transformed in the Repository layer, before being subscribed in the ViewModel and relayed to the View in the form of `ViewStates`. The UI renders the ViewStates also in a reactive approach.

##### Navigation

There is a single activity in the project, called `InitialActivity`. All the content is delegated to fragments which are configured using the Navigation Library to link between the different screens.

##### Dependency Injection

To keep things testable and maintainable, the project uses Dagger 2 as the dependency injection framework.

This allows the caller to use the callee without having to know how to construct them, separating reposibilities and leaving the code cleaner.

### Getting Started

This project uses the Gradle build system. To build this project, use the `gradlew build` command or use "Import Project" in Android Studio.

There are two Gradle tasks for testing the project:

- `connectedAndroidTest` - for running Espresso on a connected device
- `test` - for running unit tests

##### Coding Standards

Hire Me News uses [ktlint](https://ktlint.github.io/) to enforce Kotlin coding styles. Here's how to configure it for use with Android Studio (instructions adapted from the ktlint [README](https://github.com/shyiko/ktlint/blob/master/README.md)):

- Close Android Studio if it's open

- Download ktlint using these [installation instructions](https://github.com/shyiko/ktlint/blob/master/README.md#installation)

- Inside the project root directory run:

  `./ktlint --apply-to-idea-project --android`

- Start Android Studio

### Continuous Integration

This project is continuously build and checked for errors using Bitrise.

### Screenshots

![photo5140978744557676639](docs/photo5140978744557676639.jpg)

![photo5143521872003049545](docs/photo5143521872003049545.jpg)

### Libraries

##### Foundation

- AppCompat
- KTX
- Multidex

##### Reactive

- Live Data
- RxJava
- RxKotlin
- Traceur

##### Dependency Injection

- Dagger 2

##### UI

- Constraint Layout
- Glide

##### Networking

- Retrofit
- Moshi
- OkHttp

##### Testing

- JUnit
- Mockito
- Mockito-Kotlin
- RESTMock
- Espresso
- Hamcrest

##### Debugging tools

- Timber
- Leak Canary
- Chuck
