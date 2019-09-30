# MoviesApp

A simple native Android app that can fetch movies from themoviedb.org and save them as favorites, in a local database.

## Android Architecture Components Used:

- ViewModel
- Databinding
- LiveData
- Room

## Design Patterns Used:

- MVVM
- Repository
- Singleton
- Factory
- Dependency Injection

## Features, Libraries and Techniques Used:

- Network requests to fetch Movies and Genres (with Retrofit)
- Paginated Movies requests.
- Sorting of Movies list by release date.
- Database querying to save and retrieve favorite Movies (with Room)
- Unit and Instrumented testing with (Mockito and Mockito-Kotlin)
- Used ThreeTenABP library for LocalDates
- Kotlin Coroutines
- Movies images loaded on demand from the web (with Picasso)
- Gson
- Constraint Layout
