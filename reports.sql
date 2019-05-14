declare @start date = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE())-3, 0)
declare @end date = DATEADD(MONTH, DATEDIFF(MONTH, -1, GETDATE())-1, -1)

select ISBN , SUM(shoppingCart.quantity) from table shoppingCart
where dateField between @start and @end
group by shoppingCart.ISBN
order by  SUM(shoppingCart.quantity) ASC  LIMIT 10 ;


SELECT customarName ,  SUM(shoppingCart.quantity) from table customarOrders , shoppingCart
where dataField between @start and @end and customarOrders.orderId = shoppingCart.orderId
group by customarName
order by SUM(shoppingCart.quantity) LIMIT 5 ;


select SUM(quantity) from table shoppingCart
where dataField > @end ;