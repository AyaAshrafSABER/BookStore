create Schema BookStore;
use BookStore;
create table categories
(
  name varchar(10) primary key
);

insert into categories
values ('Science'),
       ('Art'),
       ('Religion'),
       ('History'),
       ('Geography');

create table book
(
  ISBN            int auto_increment primary key,
  title           varchar(1000),
  publisherId     int,
  publicationYear year,
  price           int NOT NULL CHECK (price >= 0),
  category        varchar(10),
  foreign key (category) references categories (name)
);


create table authors
(
  authorId      int auto_increment PRIMARY KEY,
  authorName    varchar(100),
  authorAddress varchar(1000)

);


create table book_authors
(
  ISBN     int,
  authorID int,
  foreign key (ISBN) references book (ISBN) on delete cascade,
  primary key (ISBN, authorID)
);

create table publisher
(
  publisherId   int auto_increment primary key,
  publisherName varchar(100),
  Paddress      varchar(200),
  phone         varchar(20)
);

create table book_quantity
(
  ISBN      int primary key,
  nOfCopies int,
  threshold int,
  foreign key (ISBN) references book (ISBN)
);


create table book_orders
(
  orderId       int primary key AUTO_INCREMENT,
  ISBN          int,
  numberOfBooks int,
  date          date,
  foreign key (ISBN) references book (ISBN)
);

create table users
(
  userId    int primary key auto_increment,
  userName  varchar(50) unique ,
  password  varchar(255),
  privilege boolean
);
create table usersInfo
(
  userId    int primary key auto_increment,
  userName  varchar(50) ,
  password  varchar(255),
  firstName varchar(20),
  lastName  varchar(20),
  email     varchar(255) UNIQUE,
  phoneNum  varchar(20),
  foreign key (userId) references users (userId)
);

create table shippingAddress
(
  userId          int,
  Shippingaddress varchar(200),
  primary key (userId, Shippingaddress),
  foreign key (userId) references users (userId)
);

create table customarOrders
(
  orderId    int primary key auto_increment,
  customarId int,
  totalPrice INT,
  foreign key (customarId) references usersInfo (userId)
);

create table shoppingCart
(
  orderId  int,
  ISBN     int,
  quantity int,
  price    int,
  date     date,
  primary key (orderId, ISBN),
  foreign key (orderId) references customarOrders (orderId),
  foreign key (ISBN) references book (ISBN)
);
create table creditCards
(

  userId     int,
  creditCard bigint,
  cvvNumber  int,
  primary key (userId, creditCard),
  foreign key (userId) references usersInfo (userId)

);

alter table book
  add foreign key (publisherId) references publisher (publisherId);

DELIMITER $$
CREATE TRIGGER afterUpdateQuantity
  AFTER UPDATE
  ON book_quantity
  FOR EACH ROW
begin
  DECLARE sum int;
  SELECT SUM(numberOfBooks) into sum from book_orders where book_orders.ISBN = NEW.ISBN;
  IF NEW.threshold > NEW.nOfCopies + sum THEN
    insert into book_orders (ISBN, numberOfBooks) values (NEW.ISBN, NEW.threshold - NEW.nOfCopies - sum);
  end if;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER afterInsertBook
  AFTER INSERT
  ON book_quantity
  FOR EACH ROW
begin
  DECLARE sum int;
  SELECT SUM(numberOfBooks) into sum from book_orders where book_orders.ISBN = NEW.ISBN;
  IF NEW.threshold > NEW.nOfCopies + sum THEN
    insert into book_orders (ISBN, numberOfBooks, date)
    values (NEW.ISBN, NEW.threshold - NEW.nOfCopies - sum, CURDATE());
  end if;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER ConfirmingOrders
  before delete
  ON book_orders
  FOR EACH ROW
begin
  UPDATE book_quantity
  SET book_quantity.nOfCopies = book_quantity.nOfCopies + OLD.numberOfBooks
  where book_quantity.ISBN = OLD.ISBN;

END $$


DELIMITER ;

DELIMITER $$
CREATE TRIGGER beforeQuantityUpdate
  BEFORE UPDATE
  ON book_quantity
  FOR EACH ROW
begin
  DECLARE msg varchar(255);
  if NEW.nOfCopies < 0 then
    SET msg = 'Negative Quantity Value';
    SIGNAL SQLSTATE '45000' set message_text = msg;
  end if;
END $$
DELIMITER ;


DELIMITER $$


CREATE TRIGGER insertIntoCart
  BEFORE insert
  ON shoppingCart
  FOR EACH ROW
begin
  DECLARE msg varchar(255);
  update book_quantity
  set book_quantity.nOfCopies = book_quantity.nOfCopies - NEW.quantity
  where book_quantity.ISBN = NEW.ISBN
    and book_quantity.nOfCopies >= NEW.quantity;
  if (row_count() = 0) then
    SET msg = 'NOT Enough Books '  + ISBN;
    SIGNAL SQLSTATE '45000' set message_text = msg;
  end if;
END $$
DELIMITER ;


DELIMITER $$
CREATE TRIGGER afterRemove
    after delete
    on shoppingCart
    for each row
begin
  update book_quantity
  set book_quantity.nOfCopies = book_quantity.nOfCopies + OLD.quantity
  where book_quantity.ISBN = OLD.ISBN;
END;
DELIMITER ;
