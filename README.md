
<h1 align="center">Notes App</h1>

<p align="center"> 🗒 The primary goal of building this Notes app is to practice dependency injection, fetching data from an API, applying Clean Architecture with feature-based structure, and implementing comprehensive testing (Unit Tests, Integration Tests, UI Tests). </p>



## Architecture
I implemented Clean Architecture with feature-based in my project. There's a core that contains shared components between features. Inside the core, I have a data layer, which includes both local and remote data sources , mapper , repository. Additionally, there's a di module for managing dependency injection with the app module. The domain layer in the core holds models and repositories that are implemented in the data layer. In the presentation layer of the core, I have shared components used across different features. The core also includes a util package for constants and utility functions.

The project includes two features: 1- Add Note and 2- Note List, and each feature is divided into data, domain, and presentation layers based on its specific requirements.

Presentation patterns : MVI architecture


## Testing
I utilize Hilt for dependency injection in testing, covering various types of tests throughout the project:

### Unit Tests
- The domain layer is tested to ensure business logic correctness.
- The ViewModel is thoroughly tested for proper state management and data handling.

### Integration Tests
- The interaction between the Room database and the repository is tested to ensure seamless data flow.
- Integration of various components, including the data and domain layers, is verified to confirm they function correctly together.

### UI Tests
- The UI layer is tested for navigation, UI component rendering, and user interactions to ensure a smooth user experience.
  
In total, Unit Tests, Integration Tests, and UI Tests are implemented to ensure the app is robust and functions correctly across all layers.




### Structure
```
 📂core
    ┗ 📂data
      ┣ 📂local
      ┣ 📂mapper
      ┣ 📂remote
      ┗ 📂repository
    ┣ 📂di
    ┗ 📂domain
      ┣ 📂model
      ┣ 📂use_case
      ┗ 📂repository
    ┣ 📂presentation
    ┗ 📂util
 📂feature
    ┣ 📂data
    ┣ 📂domain
    ┗ 📂presentation

```

## Techniques 🛠️
- Kotlin (for Android development)
- Jetpack compose
- Dialog
- Navigation component
- Testing
- Coroutines
- Flow
- Hilt (for dependency injection)
- Retrofit (for networking)
- Coil (for image loading)
- Room Database
