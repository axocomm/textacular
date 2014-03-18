# TeXtacular
### Not to be confused with Textacular
TeXtacular is an application that provides a simple interface for creating documents with LaTeX.

## Requirements
All Java libraries are located in the `lib/` directory and are automatically included by `make`.

This program (for now) requires a Unix-based operating system, a modern LaTeX distribution (I use TeX Live 2013) and the Perl script `latexmk`. Unfortunately, I did not come across any better way to compile LaTeX from Java other than sending it off to a web service.
This script is usually included with TeX Live (and MacTeX). Due to the inability to really consider all possible configurations, the compile script located at `res/bin/compile` will look for in `/usr/bin/latexmk` and `/usr/texbin/latexmk`. Please update this file with the correct location of `latexmk` if it is somewhere else.

## Compilation
You may use the included `Makefile` to compile and run this program if you are running from the command line. Otherwise, create a project in Eclipse at this location and add the JAR files inside `lib/` to the project build path.

## Notes
Output from `latexmk` and other status messages will be printed to the console. If something goes horribly wrong and an error message does not appear, the console will likely have bad news.
