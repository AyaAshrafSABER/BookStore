-- -----------------------------------------------------
-- procedure addToCart
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `addToCart`(in orderId int,
                                                       in bookId int,
                                                       in quantity int,
                                                       in price int)
BEGIN
    insert into shoppingCart (orderId, ISBN, quantity, price, date)
    values (orderId, bookId, quantity, price, CURDATE());
END$$

DELIMITER ;
-- --------------------------------------
-- procedure authenticateUser
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `authenticateUser`(in userName varchar(50),
                                                              in password varchar(225))
BEGIN
    select userId, privilege from users where users.userName = userName and users.password = password;
END$$

DELIMITER ;

-- --------------------------------------
-- procedure logIn
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `logIn`(
    in userId int)
BEGIN
    select * from usersInfo where usersInfo.userId = userId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure addUser
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `addUser`(in P_userName varchar(50),
                                                     in P_password varchar(255),
                                                     in P_privilege boolean)
BEGIN
    insert into users (userName, password, privilege) values (P_userName, P_password, P_privilege);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure confirmOrder
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `confirmOrder`(in P_orderId int(11))
BEGIN
    SELECT ISBN into @P_bookID from book_orders WHERE orderId = P_orderId;
    SELECT numberOfBooks into @P_quantity from book_orders WHERE orderId = P_orderId;

    DELETE
    FROM book_orders
    WHERE orderId = P_orderId;

    CALL `BookStore`.`updateBookQuantity`('increase', @P_bookID, @P_quantity);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertToBook
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertToBook`(
                                                          in title varchar(1000),
                                                          in publisherID int,
                                                          in publicationYear year(4),
                                                          in price int(11),
                                                          in category varchar(10),
                                                          in threshold int,
                                                          in nOfcopies int)
BEGIN
    insert into book (title, publisherId, publicationYear, price, category)
    values (title, publisherID, publicationYear,
            price, category);
    insert into book_quantity values (LAST_INSERT_ID(), threshold, nOfcopies);
    select LAST_INSERT_ID();

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure managerPromote
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `managerPromote`(in P_userName varchar(50))
BEGIN
    UPDATE users
    SET privilege = true
    WHERE users.userName = P_userName;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure orderBook
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `orderBook`(in bookId int,
                                                       in numberOfBooks int(11))
BEGIN

    insert into book_orders (ISBN, numberOfBooks) values (bookId, numberOfBooks);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure removeFromCart
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `removeFromCart`(in P_orderId int)
BEGIN
    DELETE
    FROM shoppingCart
    WHERE orderId = P_orderId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure searchForBook
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `searchForBook`(in P_ISBN varchar(45),
                                                           in P_title varchar(1000),
                                                           in P_publisherName varchar(100),
                                                           in P_publicationYear year,
                                                           in P_Minprice int,
                                                           in P_Maxprice int,
                                                           in P_category varchar(10),
                                                           in P_author varchar(100), in startIndex int)
BEGIN
    SELECT *
    FROM book c
             left join book_authors b
                       on c.ISBN = b.ISBN
             left join publisher p on c.publisherId = p.publisherId
    where (P_ISBN is null or c.ISBN = P_ISBN)
      and (P_title is null or c.Title = P_title)
      and (P_publisherName is null or p.publisherName = P_publisherName)
      and (P_category is null or c.category = P_category)
      and (P_publicationYear is null or c.publicationYear = P_publicationYear)
      and (P_author is null or b.authorID in (select authorID from authors where P_author = authors.authorName))
      and (P_Maxprice is null or P_Minprice is null or c.price BETWEEN P_Minprice AND P_Maxprice)
    limit startIndex , 10;

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updateBookQuantity
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `updateBookQuantity`(in QuantityType nvarchar(20),
                                                                in P_ISBN int,
                                                                in quantity int(11))
BEGIN
    IF QuantityType = 'decrease'
    then
        UPDATE book_quantity
        SET nOfCopies = nOfCopies - quantity
        WHERE ISBN = P_ISBN;
    end if;
    IF QuantityType = 'increase'
    then
        UPDATE book_quantity
        SET nOfCopies = nOfCopies + quantity
        WHERE ISBN = P_ISBN;
    end if;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure updateInfo
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `updateInfo`(in CurrentUserId int,
                                                        in P_userName varchar(50),
                                                        in P_password varchar(255),
                                                        in P_firstName varchar(20),
                                                        in P_lastName varchar(20),
                                                        in P_email varchar(255),
                                                        in P_phoneNum varchar(20))
BEGIN
    UPDATE usersInfo
    SET userName=IFNULL(P_userName, userName),
        password=IFNULL(P_password, password),
        firstName=IFNULL(P_firstName, firstName),
        lastName=IFNULL(P_lastName, lastName),
        email=IFNULL(P_email, email),
        phoneNum=IFNULL(P_phoneNum, phoneNum)
    WHERE userId = CurrentUserId;
    UPDATE users
    SET password=IFNULL(P_password, password)
    WHERE userId = CurrentUserId;

END$$

DELIMITER ;


-- -----------------------------------------------------
-- procedure viewShoppingCart
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `viewShoppingCart`(in orderId int)
BEGIN
    SELECT * from shoppingCart where orderId = shoppingCart.orderId;
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertThreshold
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertThreshold`(in book int,
                                                             in nOfCopies int,
                                                             in threshold int)
BEGIN
    insert into book_quantity (ISBN, nOfCopies, threshold) values (book, nOfCopies, threshold);

END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertToAuthors
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertToAuthors`(in ISBN int,
                                                             in authorId int)
BEGIN
    insert into book_authors (ISBN, authorID) values (ISBN, authorId);
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertAuthor
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertAuthor`(in authorName varchar(100),
                                                          in authorAddress varchar(1000))
BEGIN
    insert into authors (authors.authorName, authors.authorName) values (authorName, authorAddress);
    select LAST_INSERT_ID();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure getAuthors
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `getAuthors`()
BEGIN
    SELECT authorId, authorName from authors;

END$$

DELIMITER ;


-- -----------------------------------------------------
-- procedure getPublishers
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `getPublishers`()
BEGIN
    SELECT publisherId, publisherName from publisher;

END$$

DELIMITER ;


-- -----------------------------------------------------
-- procedure insertToPublisher
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertToPublisher`(in pName varchar(100),
                                                               in Paddress varchar(200),
                                                               in phone varchar(20))
BEGIN
    insert into publisher (publisherName, Paddress, phone) values (pName, Paddress, phone);
    select last_insert_id();
END$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertToShippingAddress
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertToShippingAddress`(userId int,
                                                                     Shippingaddress varchar(200))
BEGIN
    insert into shippingAddress values (userId, Shippingaddress) ;

END
$$

DELIMITER ;


-- -----------------------------------------------------
-- procedure signup
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore`$$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `signup`(in P_userName varchar(50),
                                                    in P_password varchar(255),
                                                    in P_firstName varchar(20),
                                                    in P_lastName varchar(20),
                                                    in P_email varchar(255),
                                                    in P_phoneNum varchar(20),
                                                    in P_privilege boolean)
BEGIN
    insert into users (userName, password, privilege) values (P_userName, P_password, P_privilege);
    insert into usersInfo
    values (last_insert_id(), P_userName, P_password, P_firstName, P_lastName, P_email, P_phoneNum);
    select last_insert_id();
END $$
DELIMITER ;
-- -----------------------------------------------------
-- procedure getBookOrders
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore` $$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `getBookOrders`()
BEGIN
    SELECT * from book_orders;
END $$
DELIMITER ;


-- -----------------------------------------------------
-- procedure getShippingAddress
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore` $$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `getShippingAddress`(in P_userId int)
BEGIN
    SELECT Shippingaddress from shippingAddress where shippingAddress.userId = P_userId;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- procedure insertCustomerOrder
-- -----------------------------------------------------

DELIMITER $$
USE `BookStore` $$
CREATE
    DEFINER = `root`@`localhost` PROCEDURE `insertCustomerOrder`(in P_userId int, in totalPrice int)
BEGIN
    insert into customarOrders (customarId, totalPrice) values (P_userId, totalPrice);
    select last_insert_id();
END $$
DELIMITER ;
