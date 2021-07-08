book_storage application

Endpoints can be checked by Postman or Swagger:
 http://localhost:8080/swagger-ui.html


H2 in-memory DB is empty. First need to post first entries:

for an endpoint /abook JSON example (antique book):

{
"bookName": "Shining",
"author": "Stephen King",
"barcode": "233567185874",
"publishingYear": "1855-01-01",
"price": 100.00,
"quantity": 1
}

for an endpoint /journal JSON example (journal): 

{
"bookName": "Shining",
"author": "Stephen King",
"barcode": "233567185874",
"scienceIndex": 2,
"price": 1.00,
"quantity": 3
}

for an endpoint /book JSON example (regular book): 

{
"bookName": "Shining",
"author": "Stephen King",
"barcode": "233567185874",
"price": 1.00,
"quantity": 3
}

P.S. If provided publishing year is later than 1900, book will be saved as regular book, not antique.

