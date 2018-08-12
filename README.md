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

```
Task have been strictly wrote what kind technologies use and therefore I used core Java Multithreading (Fork/Join) framework.
Otherwise, I could use Akka, Speedment, Spark or Hadoop frameworks. These frameworks would fit perfectly on the task, but I used it for parallel  
read/write from the database Fork/Join framework.The task was described to read the first table and convert the other table to this given.

```  

### JAVA
#### Technology
- JAVA 8
- Framework: Spring / Hibernate
- Maven
- PostgreSQL 10
- Multithreading (Fork/Join) framework

#### Let's Get Started
* [Click this link and download](https://drive.google.com/open?id=1yaFiD9RdBk5oFXg8UKczsVVAtkIxfwp9)- Download test data  
* [Configuration](#configuration) - Database connection configuration on the application
* [Explain application and run main class](#explain-application-and-run-main-class)

## Click this link and download

1. Download test data  [click this link](https://drive.google.com/open?id=1yaFiD9RdBk5oFXg8UKczsVVAtkIxfwp9)
2. Open **Postgresql 10** and create new database with name **gmbh**
3. Extract downloaded **aena_test_data.tar.gz** file and restore recently created **gmbh** database   
```
psql -h localhost -p 5434 -U postgres gmbh < /you_local_where_lacated_extractfile_path/aenaflight.sql
``` 
After restore data to **gmbh** database you can see one table with name **aenaflight_2017_01**. This contains nearly 10 000 000 rows.

**aenaflight_2017_01** table. Hibernate entity class name  ***AenaflightSource2017Entity***

| column | type | description |
| --------------------------|------------------|----------------------------|
| id | bigint | primary key, unique identifier |
| act_arr_date_time_lt | character varying(64) | actual arrival timestamp |
| aircraft_name_scheduled | text | scheduled aircraft name |
| arr_apt_name_es | character varying(128) | Arrival airport name in Spanish |
| arr_apt_code_iata | character varying(8)| IATA/ICAO code of arrival airport |
| baggage_info | character varying(128) | Baggage information of flight (LIFO) |
| carrier_airline_name_en | character varying(128) | Carrier airline name English |
| carrier_icao_code | character varying(8) | Carrier IATA/ICAO code |
| carrier_number | character varying(8) | Carrier number |
| counter | character varying(64) | Registration counter (LIFO) |
| dep_apt_name_es | character varying(128) | Departure airport name Spanish |
| dep_apt_code_iata | character varying(8) | IATA/ICAO code of departure airport |
| est_arr_date_time_lt | character varying(64) | estimated arrival timestamp |
| est_dep_date_time_lt | character varying(64) | estimated departure timestamp |
| flight_airline_name_en | character varying(128) | Flight airline name English |
| flight_airline_name | character varying(128) | Flight Airline name |
| flight_icao_code | character varying(8) | IATA/ICAO flight airline code |
| flight_number | character varying(8) | flight number |
| flt_leg_seq_no | character varying(8) | flight leg sequence id |
| gate_info | character varying(128) | gate information (LIFO) |
| lounge_info | character varying(128) | lounge information (LIFO) |
| schd_arr_only_date_lt | character varying(32) | scheduled arrival date |
| schd_arr_only_time_lt | character varying(32) | scheduled arrival time |
| source_data | text | source of data |
| status_info | character varying(128) | flight status |
| terminal_info | character varying(128) | terminal information (LIFO) |
| arr_terminal_info | character varying(128) | arrival terminal information (LIFO) |
| act_dep_date_time_lt | character varying(64) | actual departure timestamp |
| schd_dep_only_date_lt | character varying(32) | scheduled departure date |
| schd_dep_only_time_lt | character varying(32) | scheduled departure time |
| created_at | bigint unix timestamp | when record was created |

Final record should contain comma-separated aggregation of previous values without duplicates (LIFO) <br/>
LIFO(last in first out) - latest record will be first in list. Example 1,1,2,2,2,3,1 should be be transformed to 1,3,2,1


Transform **aenaflight_source** table definition description. Hibernate entity class name ***AenaflightDestinationEntity***

| column | type | description |
| --------------------------|------------------|----------------------------|
| id | bigint | primary key, unique identifier |
| adep | character varying(8) | IATA/ICAO code of departure airport |
| ades | character varying(8) | IATA/ICAO code of destination airport |
| flight_code | character varying(8) | IATA/ICAO airline code of flight |
| flight_number | character varying(8) | flight number |
| carrier_code | character varying(8) | IATA/ICAO airline code of carrier |
| carrier_number | character varying(8) | carrier number |
| status_info | character varying(256) | flight status |
| schd_dep_lt | timestamp without time zone | scheduled departure timestamp |
| schd_arr_lt | timestamp without time zone | scheduled arrival timestamp |
| est_dep_lt | timestamp without time zone | estimated departure timestamp |
| est_arr_lt | timestamp without time zone | estimated arrival timestamp |
| act_dep_lt | timestamp without time zone | actual departure timestamp |
| act_arr_lt | timestamp without time zone | actual arrival timestamp |
| flt_leg_seq_no | integer | flight leg sequence number |
| aircraft_name_scheduled | text | scheduled aircraft name |
| baggage_info | character varying(128) | baggage information |
| counter | character varying(128) | counter information |
| gate_info | character varying(128) | gate information |
| lounge_info | character varying(128) | lounge information |
| terminal_info | character varying(128) | terminal information |
| arr_terminal_info | character varying(128) | arrival terminal information |
| source_data | text | source of data |
| created_at | timestamp without time zone | record creation timestamp |


## Configuration
Open application from IntellijIdea and open database connection configuration file **resources/persistence-pgsql.properties**

```
jdbc.url=jdbc:postgresql://localhost:5434/gmbh
jdbc.user=postgres
jdbc.pass=postgres
```

Change to configuration locale database connection  **port**, **db name**, **user** name and **pass**word.
Do not change anything else.

## Explain application and run main class

IntelliJIdea run **ForkJoinApp** main class.

**ForkJoinApp** run step by step four business logic.
1. **aenaflight_2017_01** table add new offset_id column and indexing this is. Why did I add a new column, because this query 
   **<i>SELECT * FROM aenaflight_2017_01 ORDER BY id OFFSET 0 LIMIT 500;</i>** is slower working 
   than this **<i>SELECT * FROM aenaflight_2017_01 WHERE id>:offset ORDER BY id LIMIT 500;</i>**.
   You can tell why I did not use the **ID** itself because **ID** was not a sequential order.

2. Then I made a vacuum table, without that I could not read from more than 30,000 row more. 
   Everytime after reading 300000 row give error OutOffMemory or Stackoverflow exception. 
   For memory problem I  have did vakum table and this is action gаvе me reading speed.
  
3. I looked at the PositionConfigEntity table and see if it does not have any data. 
   If there are them given for working off and started with pasic offset = **SELECT MAX (pOffset) +500 FROM position_config;**
   This works if the read and write was successful then writes to the position_config table offset position.
   For example, if an error occurs or if you stopped the program, then my algorithm reads from the **position_config** 
   table all the successful **offsets** from [500, 1000, 1500, 2500, 30000, 50000, 60000, 65000] of my other algorithm 
   from min to max produces this figure  [500, 1000, 1500, **2000**, 2500 , 3000, **3500** , **4000**, **4500**, 5000, **5500**, 6000, 6500] 
   selects the number that does not have the first array [2000, 3500, 4000, 4500, 5500] and indexes all of these.
   