## Au lieu de taper  toute cette commande ( On la tape dans le même repertoire que le pom.xml)

```
mvn exec:java -Dexec.mainClass="fr.univorleans.webapis.App" -Dexec.args="-n Doe"

```
pour faire notre test, on va créer un alias

``` 
alias myapp='mvn exec:java -Dexec.mainClass="fr.univorleans.webapis.App" -Dexec.args'
``` 
et on pourra ainsi taper juste : 

``` 
myapp "-lastname Doe"
```

mvn exec:java -Dexec.mainClass="fr.univorleans.webapis.App" -Dexec.args="-l Doe"

## Question 6 : 
mvn exec:java -Dexec.mainClass="fr.univorleans.webapis.App" -Dexec.args="-l Doe -f Lili -a 35"
mvn exec:java -Dexec.mainClass="fr.univorleans.webapis.App" -Dexec.args="-l Doe -f Lili"
mvn exec:java -Dexec.mainClass="fr.univorleans.webapis.App" -Dexec.args="-l Doe -a 35"

## Question 7 :
