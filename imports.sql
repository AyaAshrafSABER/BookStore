set foreign_key_checks=0;
load data infile '/home/youmnadwidar/Downloads/books.csv' into table book FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\';
load data infile '/home/youmnadwidar/Downloads/publisher.csv' into table authors FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\' ignore 1 lines ;
load data infile '/home/youmnadwidar/Downloads/sh(1).csv' into table shoppingCart FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '\\' ignore  1 lines ;
insert into book_quantity values (1,5,5);