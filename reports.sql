use BookStore;
select  b.title,shoppingCart.ISBN , title , SUM(shoppingCart.quantity) from shoppingCart join book b on shoppingCart.ISBN = b.ISBN
where DATEDIFF(date,CURRENT_DATE()) > 0
group by shoppingCart.ISBN
order by  SUM(shoppingCart.quantity) ASC  LIMIT 10 ;


SELECT customarId ,  SUM(shoppingCart.quantity) from customarOrders , shoppingCart
where DATEDIFF(date,current_date()) > 0 and customarOrders.orderId = shoppingCart.orderId
group by customarId
order by SUM(shoppingCart.quantity) LIMIT 5 ;


select SUM(quantity) from shoppingCart
where DATEDIFF(date,CURDATE()) > 0;