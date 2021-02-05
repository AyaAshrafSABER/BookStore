use sample73;

SELECT table_name,table_rows,Avg_row_length,
Data_length,Index_length,Data_free FROM information_schema.tables
WHERE table_schema = 'sample73';

SELECT table_name AS "Table",
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = 'sample73'
ORDER BY (data_length + index_length) DESC;

SELECT * FROM INFORMATION_SCHEMA.TABLESPACES
  WHERE TABLESPACE_NAME LIKE 'sample73.%';

load data infile '/home/youmnadwidar/data/PUBLISHER.csv' into table publishername FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\';;
load data infile '/home/youmnadwidar/data/BOOK.csv' into table book FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\';;
load data infile '/home/youmnadwidar/data/BOOK_AUTHORS.csv' into table book_authors FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\'; ;
load data infile '/home/youmnadwidar/data/LIBRARY_BRANCH.csv' into table library_branch FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\';;
load data infile '/home/youmnadwidar/data/BOOK_COPIES.csv' into table book_copies FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\';;
load data infile '/home/youmnadwidar/data/BORROWER.csv' into table borrower FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\'; ;
load data infile '/home/youmnadwidar/data/BOOK_LOANS.csv' into table book_loans FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\'; ;


SELECT table_name,table_rows,Avg_row_length,
Data_length,Index_length,Data_free FROM information_schema.tables
WHERE table_schema = 'sample73';

SELECT table_name AS "Table",
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = 'sample73'
ORDER BY (data_length + index_length) DESC;

SELECT * FROM INFORMATION_SCHEMA.TABLESPACES
  WHERE TABLESPACE_NAME like 'sample73%';

/* Compact the database tables storage  */
OPTIMIZE TABLE sample73.book;
OPTIMIZE TABLE sample73.book_authors;
OPTIMIZE TABLE sample73.book_copies;
OPTIMIZE TABLE sample73.book_loans;
OPTIMIZE TABLE sample73.borrower;
OPTIMIZE TABLE sample73.library_branch;
OPTIMIZE TABLE sample73.publishername;

SELECT table_name,table_rows,Avg_row_length,
Data_length,Index_length,Data_free FROM information_schema.tables
WHERE table_schema = 'sample73';

SELECT table_name AS "Table",
ROUND(((data_length + index_length) / 1024 / 1024), 2) AS "Size (MB)"
FROM information_schema.TABLES
WHERE table_schema = 'sample73'
ORDER BY (data_length + index_length) DESC;

SELECT * FROM INFORMATION_SCHEMA.TABLESPACES
  WHERE TABLESPACE_NAME LIKE 'sample73';