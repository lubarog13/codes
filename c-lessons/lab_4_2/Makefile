LIBS=-lsfml-graphics -lsfml-window -lsfml-system

all: lab_4_2

main.o: main.cpp
	g++ -c "main.cpp" -o main.o

lab_4_2: main.o
	@echo "** Building the module"
	g++ -o lab_4_2 main.o $(LIBS)
