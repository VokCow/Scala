# MATCHING CASE CLASSES

This script is thought to work with "World" MySQL sample Database.

The code is composed by several classes. 
  * A class "dbMySQL" contains the methods that create and close the connection with the MySQL Database 
and the "select" method, which allow to retrieve information from two tables (city and country). 
  * Two "case classes" that modellize the two tables. These two inheritate from a "trait" (which is a sort of abstract
  class)
  * A class "transforms" in which the transformations that will be made to the info in the database are implemented
  * Finally, an "App" class, where the main thread resides, so from where the programme is executed.
  
 This script is in correspondance with which I stated in the Java-SQL folder of the Java repo, where I also retrieved info from a 
 MySQL database. In both cases (as Scala utilices the Java Virtual Machine), the odbc driver is used to connect with the MySQL database.
 Here, though, Scala case classes are used to retrieve statistical info. 
 
 Even though, given the size of "World" database tables, the same results could also be achieved with simple SQL queries, when it comes
 to the huge datasets, commonplace in Big Data, one can leverage this feature of Scala and the fact that the SPARK project has an
 API for Scala to perform this kind of computations on really huge datasets along clusters of (as much as needed) nodes.
 
 There is the outcome of the code:
  
      Population per Country: 

    (Argentina,16441940)
    (United Arab Emirates,1728336)
    (Antigua and Barbuda,24000)
    (Anguilla,1556)
    (Angola,2561600)
    (Andorra,21189)
    (American Samoa,7523)
    (Netherlands,5192179)
    (Albania,270000)
    (Netherlands Antilles,2345)
    (Netherlands,5180049)
    (Afghanistan,2332100)

    Life Expectance per Region: 

    (Western Africa,50)
    (Western Europe,77)
    (Eastern Africa,46)
    (Middle East,67)
    (Australia and New Zealand,79)
    (Caribbean,74)
    (Antarctica,Unknown)
    (Polynesia,75)
    (South America,75)
    (Southern Europe,77)
    (Central Africa,38)
    (Southern and Central Asia,45)

    Process finished with exit code 0
