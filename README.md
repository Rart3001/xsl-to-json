# XSL to JSON

This project convert a .XSL file with specific structure into a .json file, that contains translations to use in Flutter projects.

### About version ###

It is the initial version of the development.

* Version: 1.0

## Installation

Use the gradle.

```bash
./gradlew build
```

## Usage

```
Create a i18n folder in the root of the project, the generated .json files are saved there.

Add .xsl file that contains the translations into the $HOME/xsl-to-json/src/main/resources folder and run the application.
```

## Generate constan using "easy_localization" plugin  

```bash
flutter pub run easy_localization:generate -f keys -S $HOME/FlutterProject/assets/i18n -s en.json -O $HOME/FlutterProject/lib/util -o locale_keys.g.dart
```

## About the .xsl file 

You can find a template example in folder $HOME/xsl-to-json/src/main/resources. Remove .example extension before open.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
