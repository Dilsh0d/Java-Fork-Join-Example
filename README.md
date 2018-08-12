[![N|Solid](https://cldup.com/dTxpPi9lDf.thumb.png)](https://nodesource.com/products/nsolid)

# Fork/join framework use select&inset from big table
> Task Description ETL (Extract Transform Load). <br/>
**Task consist of 3 steps.**

1. extract - during this step data loaded from source table.

2. transform - during this step data is processed (removed duplicates, added/merged missing information from <br/>
existing records, removed unused records, parse/correct existing data). After transform source table should contain <br/>
one cleaned record which contains all information of previous records.

3. load - load processed information into destination table.<br/>
Application should store actual state of processing, in case of stop or death should be able to restart <br/>
process from stopped location and state.Additional resources/tables for processing can be created.

### JAVA
#### Technology
- JAVA 8
- Framework: Spring / Hibernate
- Maven
- PostgreSQL 10
- Multithreading (Fork/Join) framework

#### Let's Get Started
* [Click this link and download backup file](http://www.dropwizard.io/1.0.2/docs/)  
* [Create new database](#create-new-database) - Postgresql 10 open and create new **gmbh** database
* [Restore sql file](#restore-sql-file)
* [Configuration](#configuration) - Database connection configuration on the application
* [Run main class](#run-main-class)
 
 
