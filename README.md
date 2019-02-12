# Hire Me News [WIP]

Hire Me News is a sample project that showcases Android development best practices under a MVVM architecture while also making you informed of the latest news!

(If you think this app is cool, hire me! 😀 )

### Architecture

This project follows the MVVM architecture making use of most of Android Jetpack components and RxJava, to make a clean, easy to understand, and maintainable reactive structure.

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

### Screenshots

// TBD

### Libraries

##### Foundation

- AppCompat
- KTX
- Multidex

##### Database

- Room
- SQLBrite

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

### Continuous Integration

// TBD

### Points to notice

// TBD