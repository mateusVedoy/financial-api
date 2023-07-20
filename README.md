# FINANCIAL API
API to feed web interface with financial data from user.

### Configurations

<h6>Environment variables</h6>


```
FINANCIAL_DB_USER
FINANCIAL_DB_PWD
FINANCIAL_DB_DATABASE
FINANCIAL_DB_PORT
```
Obs:
* If you're going to run docker-compose.yml, set values to variables as bellow:
  * FINANCIAL_DB_USER=financial 
  * FINANCIAL_DB_PWD=f1n@nc1Al 
  * FINANCIAL_DB_DATABASE=financial_db
  * FINANCIAL_DB_PORT=1234


<h6>Running docker-compose file</h6>

```
docker-compose up
```
Obs:
* Run command above inside root folder dir.
* Inside docker-compose.yml has database configuration to run locally.

<h6>Initial database feed</h6>
Use data from **init.sql** file available into root folder.

### Use cases

#### Create financial operation
Endpoint:
```
POST /api/finances/operation/save
```
Header: 
```
Content-Type: application/json
```
Body:
```json
{
	"type": "input",
	"amount": 10,
	"date": "2023-06-19",
	"hour": "17:45"
}
```
* type: Can be input or output
* amount: Dot-separated decimal value
* date: Format YYYY-MM-DD
* hour: Format HH:mm

Success Response
```json
{
	"status": 201,
	"message": "Financial operation created successfully"
}
```
OBS: Errors response are variable, but an example
```json
{
	"status": 400,
	"message": "Something went wrong. Consult errors.",
	"errors": [
		{
			"message": "Operation date and hour cannot be empty.",
			"stacktrace": "domain.FinancialOperation.executedAt"
		}
	]
}
```

#### Find all financial operation
Endpoint:
```
GET /api/finances/operation/find/all
```
Success Response:
```json
{
	"status": 200,
	"message": "Financial operation fetched bellow",
	"data": [
		{
			"id": 1,
			"type": "input",
			"amount": 1200.0,
			"date": "2023-04-09",
			"hour": "12:55:00"
		},
		{
			"id": 2,
			"type": "input",
			"amount": 1200.0,
			"date": "2023-04-09",
			"hour": "12:55:00"
		}
	]
}
```

#### Find financial operation by id
Endpoint: 
```
GET /api/finances/operation/find/id/{identifier}
```
Success with data:
```json
{
	"status": 200,
	"message": "Financial operation fetched bellow",
	"data": [
		{
			"id": 2,
			"type": "input",
			"amount": 1200.0,
			"date": "2023-04-09",
			"hour": "12:55:00"
		}
	]
}
```
Success without data
```json
{
  "status": 200,
  "message": "There's no financial Operation to be recovered.",
  "data": []
}
```

#### Get financial statement by period
Endpoint:
```
GET /api/finances/statement/balance?initialDate={YYYY-MM-DD}&finalDate={YYYY-MM-DD}
```
Success with data:
```json
{
	"status": 200,
	"message": "Financial operation fetched bellow",
	"data": [
		{
			"state": "profit",
			"balance": 230.0,
			"period": {
				"initialDate": "2023-04-09",
				"finalDate": "2023-04-10"
			}
		}
	]
}
```
* state: Can be profit or loss, based on balance amount

Success without data:
```json
{
	"status": 200,
	"message": "There's no financial Operation to be recovered."
}
```
OBS: Errors response are variable, but an example
```
{
	"status": 400,
	"message": "Something went wrong. Consult errors.",
	"errors": [
		{
			"message": "Final date cannot be less than initial date",
			"stacktrace": "financialStatement.dates"
		}
	]
}
```