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
* [Click this link and download](https://drive.google.com/open?id=1yaFiD9RdBk5oFXg8UKczsVVAtkIxfwp9)- Download test data  
* [Configuration](#configuration) - Database connection configuration on the application
* [Explain application and run main class](#run-main-class)

## Click this link and download

1. Download test data  [click this link](https://drive.google.com/open?id=1yaFiD9RdBk5oFXg8UKczsVVAtkIxfwp9)
2. Open Postgresql 10 and create new database with name **gmbh**
3. Extract downloaded **aena_test_data.tar.gz** file and restore recently created **gmbh** database   
```
psql -h localhost -p 5434 -U postgres gmbh < /you_local_where_lacated_extractfile_path/aenaflight.sql
``` 
After restore data to **gmbh** database you can see one table with name **aenaflight_2017_01**. This contains nearly 10 000 000 rows.

**aenaflight_2017_01** table.

| column | type | description |
| --------------------------|------------------|----------------------------|
| id | bigint | primary key, unique identifier |
| act_arr_date_time_lt | character varying(64) | actual arrival timestamp |
| aircraft_name_scheduled | text | scheduled aircraft name |
| arr_apt_name_es | character varying(128) | Arrival airport name in Spanish |
| arr_apt_code_iata | character varying(8)| IATA/ICAO code of arrival airport |
| baggage_info | character varying(128) | Baggage information of flight |
| carrier_airline_name_en | character varying(128) | Carrier airline name English |
| carrier_icao_code | character varying(8) | Carrier IATA/ICAO code |
| carrier_number | character varying(8) | Carrier number |
| counter character | varying(64) | Registration counter |
| dep_apt_name_es | character varying(128) | Departure airport name Spanish |
| dep_apt_code_iata | character varying(8) | IATA/ICAO code of departure airport |
| est_arr_date_time_lt | character varying(64) | estimated arrival timestamp |
| est_dep_date_time_lt | character varying(64) | estimated departure timestamp |
| flight_airline_name_en | character varying(128) | Flight airline name English |
| flight_airline_name | character varying(128) | Flight Airline name |
| flight_icao_code | character varying(8) | IATA/ICAO flight airline code |
| flight_number | character varying(8) | flight number |
| flt_leg_seq_no | character varying(8) | flight leg sequence id |
| gate_info | character varying(128) | gate information |
| lounge_info | character varying(128) | lounge information |
| schd_arr_only_date_lt | character varying(32) | scheduled arrival date |
| schd_arr_only_time_lt | character varying(32) | scheduled arrival time |
| source_data | text | source of data |
| status_info | character varying(128) | flight status |
| terminal_info | character varying(128) | terminal information |
| arr_terminal_info | character varying(128) | arrival terminal information |
| act_dep_date_time_lt | character varying(64) | actual departure timestamp |
| schd_dep_only_date_lt | character varying(32) | scheduled departure date |
| schd_dep_only_time_lt | character varying(32) | scheduled departure time |
| created_at | bigint unix timestamp | when record was created |
