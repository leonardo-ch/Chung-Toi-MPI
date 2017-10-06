all:			ChungToiClient.class ChungToiServer.class \
			ChungToiImpl.class ChungToiInterface.class \
			ChungToi.class Usuario.class \
			Dados.class Vertex.class \
			

ChungToi.class:		ChungToi.java
			@javac ChungToi.java
			
Usuario.class:		Usuario.java
			@javac Usuario.java
			
Dados.class:		Dados.java
			@javac Dados.java

Vertex.class:		Vertex.java
			@javac Vertex.java
			
ChungToiImpl.class:		ChungToiImpl.java ChungToiInterface.class
			@javac ChungToiImpl.java

ChungToiInterface.class:	ChungToiInterface.java
			@javac ChungToiInterface.java

ChungToiClient.class:	ChungToiClient.java
			@javac ChungToiClient.java

ChungToiServer.class:	ChungToiServer.java
			@javac ChungToiServer.java

run:			all
			@java ChungToiServer &
			@sleep 1
			@java ChungToiClient

clean:
			@rm -f *.class *~

info:
			@echo "(c) Leonardo Chassot (19 set. 2017)"

