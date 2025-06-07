# Chromasync
- This is my ChromaSync application project, where I have used the modern  best practices  and framework/libraries offered by Android Jetpack. Where the user can add new theme profiles, use existing ones.
- The user can also delete some existing ones.
- The user can also sync the profiles with the server and store the local profiles in cloud.


# Architecture used is MVVM(Model View View Model)
![MVVMImage](https://github.com/siddhant123-geek/MyNewsApplicationProjectWithJetpackComposeAndDaggerHilt/assets/82453362/439310a1-bc05-4d6f-b6e1-fcf116b81eb2)


# Final project uses(Major libraries/frameworks)
- **Dagger Hilt** for Dependency injection
- **Jet pack compose** for UI
- **RoomDatabase** as the persistent library
- **Coroutine** - For performing asynchronous fetching of data from db or network
- **Flow** - Asynchronous data type used
- **StateFlow** - Obervable asynchronous data structure used


# Detailed summary of the screens implemented
# HomeScreenViews
- It has a bunch of different type of views namely **Card**, **Button**, **Floating Action Button** etc showcasing the currently applied theme to the application.
- With the floating action button cta, the user can create a new theme profile for the application and use it.
- The list of all the created theme profiles can be seen from the dropdown on the top bar.
- Each theme except the currently applied theme can be deleted.
# ListScreenViews 
- This screen has a list of names of few algorithms(Used algorithms's names for the listing purpose, anything else can also be used), each item also has certain fontstyle, color etc as per the applied theme.
- This screen has a sync button too where the user can sync the local profiles with those at the server.
- In case there exists a theme with the same name as in the database, then the theme profile with the latest modified time is given priority.
