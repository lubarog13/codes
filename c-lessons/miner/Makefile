LIBS=-lsfml-graphics -lsfml-window -lsfml-system

all: lab_4_3

main.o: main.cpp functions.h header.h objects.h
	g++ -c main.cpp

objects.o: objects.cpp functions.h header.h objects.h
	g++ -c objects.cpp

functions.o: functions.cpp functions.h header.h
	g++ -c objects.cpp

main: main.o objects.o functions.o
	@echo "** Building the module"
	g++ -o main main.o functions.o objects.o $(LIBS)

clean:
	rm -rf *.o main
