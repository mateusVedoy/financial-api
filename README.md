# FINANCIAL API
API to feed web interface with financial data from user.

### Configurations

<h6>Environment variables</h6>


```
FINANCIAL_DB_USER
FINANCIAL_DB_PWD
FINANCIAL_DB_DATABASE
```
Obs:
* If you're going to run docker-compose.yml, set values to variables as bellow:
  * FINANCIAL_DB_USER=financial 
  * FINANCIAL_DB_PWD=f1n@nc1Al 
  * FINANCIAL_DB_DATABASE=financial_db


<h6>Running docker-compose file</h6>

```
docker-compose up
```
Obs:
* Run command above inside root folder dir.
* Inside docker-compose.yml has database configuration to run locally.

<h6>Initial database feed</h6>
Use data from **init.sql** file available into root folder.

### Project information
... soon