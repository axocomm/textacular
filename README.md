# TeXtacular
TeXtacular is an application that provides a simple graphical interface for creating documents with LaTeX using familiar input controls.

## How it works
The program displays a panel containing a number of templates. When a template is selected, the relevant inputs are shown, and upon filling them out and clicking the 'Compile' button, the contents of these inputs are placed inside of the respective TeX source located at `res/templates`. Finally, the resulting file is compiled. If all goes well, a PDF is generated and can be viewed in the preview pane or opened in your default document viewer.

## Requirements
All Java libraries are located in the `lib/` directory and are automatically included by `make`.

This program (for now, hopefully) requires a Unix-based operating system, a modern LaTeX distribution (I use TeX Live 2013) and the Perl script `latexmk`. Unfortunately, I did not come across any better way to compile LaTeX from Java other than sending it off to a web service.

This script is usually included with TeX Live (and MacTeX).

## Compilation
You may use the included `Makefile` to compile and run this program if you are running from the command line. Otherwise, create a project in Eclipse at this location and add the JAR files inside `lib/` to the project build path.

## Notes
Output from `latexmk` and other status messages will be printed to the console. If something goes horribly wrong and an error message does not appear, the console will likely have bad news and you should have a look.

## Third party libraries
As mentioned above, a few libraries are included with this project:

+ [Swinglabs PDF-renderer](https://java.net/projects/pdf-renderer)
+ [MigLayout](http://www.miglayout.com/)
+ [PgsLookAndFeel](http://www.pagosoft.com/projects/pgslookandfeel/)
