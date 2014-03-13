.PHONY: all run clean

MAIN = edu.drexel.tm.cs338.textacular.Main

all: compile run

run: compile
	@java -cp 'bin:lib/*' $(MAIN)

compile: bin
	@javac -d bin -cp 'lib/*' src/edu/drexel/tm/cs338/textacular/*.java

bin:
	@mkdir bin

clean:
	@rm -rf bin/*
	@rm -rf res/output/*
