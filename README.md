# Welcome to DBRuped!

## What it is

**DBRuped** is an online service that enables communication with **DBpedia** using natural languages. You can type your questions in English (or other languages) and get required data from DBpedia. Of course the right answer is not guaranteed. It depends on existence of required information in DBpedia and also ability of the system to convert you question to the right SPARQL query that highly depend on the question itself. 

## Why DBRuped was created

**DBRuped** is an attempt of creative expansion of a challenge task for a candidate in SciBite company. No business ideas are connected to this project.

## How DBRuped works

**DBRuped** converts incoming NL (natural language) question into SPARQL query and that submit this query to DBpedia. With some chance it can return a reasonable answer. **DBRuped** converts NL queries with two ways. At first it utilises a set of predefined templates. It tries to find a template that matches an NL sentence and that build a SPARQL query according to the selected template. Apart from a query a template defines a one or more pattern for output formatting. If no pattern found for the sentence **DBRuped** switches to the second way. It connects to ChatGPT and send a prompt that requests converting NL sentence to DBpedia SPARQL query. With some chance (not so bad in general) ChatGPT provides a reasonable query that then is executed by DBpedia.

## How template definition looks like 

Templates are currently defined in a file */src/main/resources/templates/QuestionTemplates.txt* within the source tree. One file can hold multiple templates. Here is an example:

```## Age related questions ##
#QUESTION: How old is ${PERSON}?
#QUESTION: What is the age of ${PERSON}?
#QUESTION: What is ${PERSON}'s age?
#QUERY:
prefix dbr: <http://dbpedia.org/resource/>
prefix dbp: <http://dbpedia.org/property/>
prefix dbo: <http://dbpedia.org/ontology/>

SELECT ?dob
FROM <http://dbpedia.org>
WHERE {
dbr:${PERSON:space2underscore} dbo:birthDate ?dob .
} LIMIT 1
#RETURN: ${dob:date2age}
#RETURN[LONG]: The age of ${PERSON} is ${dob:date2age} (DoB: ${dob})
```

## Template definition structure

Template definition consists of three section: QUESTION, QUERY and RETURN. QUESTION section may have multiple entries. Any entry can match to let the template be selected. A question can have variable capturing place holders that match any substring and  capture such substring to a correspondent variable. Such variables can be used then in QUERY and RETURN sections.
QUERY section contains a SPARQL query that serves the above questions. This section can also have place holders but not for capturing but rather for substitution. It can be direct variable substitution like `${PERSON}` and also substitution with pre-processing `${PERSON:space2underscore}` where `space2underscore` is one (or more) of predefined functions.
When query is executed all names from the SELECT statement will form the new variables that will have values return by a query. These variables will be combined with variables from the QUESTION section. Only one entry in QUERY section is possible.
The RETURN section contains a text that can have substitution place holders like in QUERY section. RETURN section may have multiple entries but each entry must have a unique tag in square brackets. Absence of bracket means an empty tag so RETURN and RETURN[] are equivalent definitions. One or another form of output can be selected by tags. By default Web UI tries to find an output with the LONG tag and if not found takes the first entry regardless it's tag. Pre-processing functions are also allowed in the RETURN section. 

## Pre-processing functions

Pre-processing functions convert a value of a variable before substitution. Multiple functions can be applied like ${VAR:FUNC1:FUNC2}. Currently the following functions are defined (refer */src/main/java/me/gostev/scibite/dbruped/text/SimpleSubstitutorAndEvaluator.java*)
 - **space2underscore**
 - **date2year**
 - **date2age**
 More functions can be easily defined.

# Installation

To build **DBRuped** run:
```
mvn package
```
To build a Docker image run:
```
docker build -t dbruped .
```
To run: make sure that you defined an env variable **CHATGPT_API_KEY** with API key. To run with Docker run:
```
docker run -p 8080:8080 -eCHATGPT_API_KEY=abcdefg  dbruped
```
## Live instance
The live instance is available at [http://scibite.gostev.me/index.html](http://scibite.gostev.me/index.html)
![DBRuped in action](doc/DBRuped.png)
