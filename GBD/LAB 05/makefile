NOME = arvoreB
DEST = /tmp/$(NOME)
COMP = gcc

all: $(DEST)/Main

$(DEST)/Main: $(DEST)/main.o $(DEST)/arvoreB.o $(DEST)/paginaB.o
	$(COMP) -o $(DEST)/Main $(DEST)/main.o $(DEST)/arvoreB.o $(DEST)/paginaB.o

$(DEST)/main.o: main.c arvoreB.h | $(DEST)
	$(COMP) -o $(DEST)/main.o -c main.c

$(DEST)/arvoreB.o: arvoreB.c arvoreB.h | $(DEST)
	$(COMP) -o $(DEST)/arvoreB.o -c arvoreB.c

$(DEST)/paginaB.o: paginaB.c paginaB.h | $(DEST)
	$(COMP) -o $(DEST)/paginaB.o -c paginaB.c

$(DEST):
	mkdir $(DEST)

run:$(DEST)/Main
	$(DEST)/Main

clean:
	rm $(DEST)/*
