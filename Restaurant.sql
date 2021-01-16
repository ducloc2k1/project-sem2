CREATE DATABASE Restaurant

GO

USE Restaurant

GO

CREATE TABLE tblPaymentMethod
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(50),
	status BIT
)

GO

CREATE TABLE tblMenu
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255) UNIQUE,
	status BIT
)

GO

CREATE TABLE tblDrink
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255) UNIQUE,
	price FLOAT,
	status BIT,
	menuId INT FOREIGN KEY REFERENCES tblMenu(id)
)

GO

CREATE TABLE tblDish
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255) UNIQUE,
	price FLOAT,
	status BIT,
	menuId INT FOREIGN KEY REFERENCES tblMenu(id)
)

GO

CREATE TABLE tblPosition (
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(50)
)

GO

INSERT INTO tblPosition VALUES 
(N'Tầng 1'),
(N'Tầng 2')

CREATE TABLE tblTable
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255),
	idPosition INT FOREIGN KEY REFERENCES tblPosition(id),
	status BIT
)
GO 

INSERT INTO tblTable VALUES 
(N'bàn 1',1,0),
(N'bàn 2',1,0),
(N'bàn 3',1,0),
(N'bàn 4',1,0),
(N'bàn 5',1,0),
(N'bàn 6',1,0),
(N'bàn 7',1,0),
(N'bàn 8',1,0),
(N'bàn 9',1,0),
(N'bàn 10',1,0),
(N'bàn 1',2,0),
(N'bàn 2',2,0),
(N'bàn 3',2,0),
(N'bàn 4',2,0),
(N'bàn 5',2,0),
(N'bàn 6',2,0),
(N'bàn 7',2,0),
(N'bàn 8',2,0),
(N'bàn 9',2,0),
(N'bàn 10',2,0)

GO

CREATE TABLE tblOrder
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255),
	email varchar(255) UNIQUE,
	phone varchar(255) UNIQUE,
	quantity INT,
	date date,
	time varchar(30),
	gENDer BIT,
	type BIT,
	requirement NVARCHAR(255),
	tableId INT FOREIGN KEY REFERENCES tblTable(id)
)

GO

CREATE TABLE tblResource
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255) UNIQUE,
	quantity INT,
	supplier NVARCHAR(255),
	date date,
	status BIT
)
GO

CREATE TABLE tblResourceDetail
(
	id INT PRIMARY KEY IDENTITY(1,1),
	dishId INT FOREIGN KEY REFERENCES tblDish(id),
	resourceId INT FOREIGN KEY REFERENCES tblResource(id)
)
GO

CREATE TABLE tblRole
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255)
)

GO

CREATE TABLE tblEmployee
(
	id INT PRIMARY KEY IDENTITY(1,1),
	name NVARCHAR(255),
	age INT,
	email varchar(255) UNIQUE,
	phone varchar(20) UNIQUE,
	address NVARCHAR(255),
	roleId INT FOREIGN KEY REFERENCES tblRole(id),
	startDate date,
	workTime FLOAT,
	rate FLOAT,
	salary FLOAT,
	status BIT,
	pASsword varchar(255)
)

GO

CREATE TABLE tblBill
(
	id INT PRIMARY KEY IDENTITY(1,1),
	status BIT,
	totalCost FLOAT,
	startDate datetime,
	endDate datetime,
	numberCustomer INT,
	nameCustomer NVARCHAR(50),
	idEmployee INT FOREIGN KEY REFERENCES tblEmployee(id),
	idTable INT FOREIGN KEY REFERENCES tblTable(id),
	paymentMethodId INT FOREIGN KEY REFERENCES tblPaymentMethod(id),
	note NVARCHAR(255),
)

GO

CREATE TABLE tblBillDetail
(
	id INT PRIMARY KEY IDENTITY(1,1),
	billId INT FOREIGN KEY REFERENCES tblBill(id),
	drinkId INT NULL FOREIGN KEY REFERENCES tblDrink(id),
	dishId INT NULL FOREIGN KEY REFERENCES tblDish(id),
	drinkQuantity INT,
	dishQuantity INT,
	totalPrice FLOAT,
	discount FLOAT,
	status BIT
)

GO

CREATE PROC getAllRole
AS
BEGIN
	SELECT * FROM tblRole
END

GO

CREATE PROC getSingleEmployee
	@gmail VARCHAR(255),
	@pASs VARCHAR(255)
AS
BEGIN
	SELECT * FROM tblEmployee WHERE email = @gmail AND pASsword = @pASs
END

GO

CREATE PROC getAllTable
AS
BEGIN
	SELECT*FROM tblTable
END	

GO

CREATE PROC getAllRole
AS
BEGIN
	SELECT*FROM tblRole
END	

GO

CREATE PROC getAllResourceDetail
AS
BEGIN
	SELECT*FROM tblResourceDetail
END	

GO

CREATE PROC getAllResource
AS
BEGIN
	SELECT*FROM tblResource
END	

GO

CREATE PROC getAllPaymentMethod
AS
BEGIN
	SELECT*FROM tblPaymentMethod
END	

GO

CREATE PROC getAllOrder
AS
BEGIN
	SELECT*FROM tblOrder
END

GO

CREATE PROC getAllMenu
AS
BEGIN
	SELECT*FROM tblMenu
END	

GO

CREATE PROC getAllEmployee
AS
BEGIN
	SELECT*FROM tblEmployee
END	

GO

CREATE PROC getAllDrink
AS
BEGIN
	SELECT 
		   tblDrink.id,
		   tblDrink.name,
		   tblDrink.price,
		   tblDrink.status,
		   tblMenu.name as 'menuName',
		   tblMenu.id as 'menuId'
	FROM tblDrink JOIN tblMenu ON tblDrink.menuId = tblMenu.id
END	

GO

CREATE PROC getAllDish
AS
BEGIN
	SELECT*FROM tblDish
END	

GO

CREATE PROC getAllBillDetail
AS
BEGIN
	SELECT*FROM tblBillDetail
END	

GO

CREATE PROC getAllBill
AS
BEGIN
	SELECT*FROM tblBill
END	

GO
------------------------------------------


GO

CREATE PROC addRole
@name NVARCHAR(255)
AS
BEGIN
	INSERT INTO tblRole(name) values(@name)
END

GO

CREATE PROC addResourceDetail
@dishId INT,
@resourceId INT
AS
BEGIN
	INSERT INTO tblResourceDetail(dishId,resourceId)values(@dishId,@resourceId)
END

GO

CREATE PROC addResource
@name NVARCHAR(255),
@quantity INT,
@supplier NVARCHAR(255),
@date date,
@status BIT
AS
BEGIN
	INSERT INTO tblResource(name,quantity,supplier,date,status)values(@name,@quantity,@supplier,@date,@status)
END

GO

CREATE PROC addPaymentMethod
@name NVARCHAR(50),
@status BIT
AS
BEGIN
	INSERT INTO tblPaymentMethod(name,status)values(@name,@status)
END

GO

CREATE PROC addOrder
@name NVARCHAR(255),
@email varchar(255),
@phone varchar(255),
@quantity INT,
@date date,
@time varchar(30),
@gENDer BIT,
@type BIT,
@requirement NVARCHAR(255),
@tableId INT
AS
BEGIN
	INSERT INTO tblOrder(name,email,phone,quantity,date,time,gENDer,type,requirement,tableId) values(@name,@email,@phone,@quantity,@date,@time,@gENDer,@type,@requirement,@tableId)
END

GO

CREATE PROC addMenu
@name NVARCHAR(255),
@status BIT
AS
BEGIN
	INSERT INTO tblMenu(name,status)values(@name,@status)
END	

GO

CREATE PROC addEmployee
@name NVARCHAR(255),
@age INT,
@email varchar(255),
@phone varchar(20),
@address varchar(255),
@roleId INT,
@startDate date,
@workTime FLOAT,
@rate FLOAT,
@salary FLOAT,
@status BIT,
@pASsword varchar(255)
AS
BEGIN
	INSERT INTO tblEmployee(name,age,email,phone,address,roleId,startDate,workTime,rate,salary,status,pASsword)values(@name,@age,@email,@phone,@address,@roleId,@startDate,@workTime,@rate,@salary,@status,@pASsword)
END

GO

CREATE PROC addDrink
@name NVARCHAR(255),
@price FLOAT,
@status BIT,
@menuId INT
AS
BEGIN
	INSERT INTO tblDrink(name,price,status,menuId)values(@name,@price,@status,@menuId)
END

GO

CREATE PROC addDish
@name NVARCHAR(255),
@price FLOAT,
@status BIT,
@menuId INT
AS
BEGIN
	INSERT INTO tblDish(name,price,status,menuId)values(@name,@price,@status,@menuId)
END

GO

CREATE PROC addBillDetail
@billId INT,
@drinkId INT,
@dishId INT,
@drinkQuantity INT,
@dishQuantity INT,
@totalPrice FLOAT,
@discount FLOAT,
@status BIT
AS
BEGIN
	INSERT INTO tblBillDetail(billId,drinkId,dishId,drinkQuantity,dishQuantity,totalPrice,discount,status)values(@billId,@drinkId,@dishId,@drinkQuantity,@dishQuantity,@totalPrice,@discount,@status)
END

GO

ALTER PROC addBill
@status BIT,
@totalCost FLOAT,
@startDate datetime,
@endDate datetime,
@numberCustomer INT,
@nameCustomer NVARCHAR(50),
@idEmployee INT,
@idTable INT,
@paymentMethodId INT,
@note NVARCHAR(255)
AS
BEGIN
	INSERT INTO tblBill(status,totalCost,startDate,
	endDate,numberCustomer,nameCustomer,
	idEmployee,idTable,paymentMethodId,note) values
	(@status,@totalCost,
	@startDate,@endDate,
	@numberCustomer,@nameCustomer,
	@idEmployee,@idTable,
	@paymentMethodId,@note)
END

GO

CREATE PROC findBill
	@id INT
AS
BEGIN
	SELECT * FROM tblBill WHERE id = @id
END

GO

CREATE PROC findBillByTable
	@idTable INT
AS
BEGIN
	SELECT * FROM tblBill WHERE idTable = @idTable AND status = 0;
END

GO
------------------------------------------
CREATE PROC editTable
@id INT,
@name NVARCHAR(255),
@idPosition NVARCHAR(255),
@status BIT
AS
BEGIN
	UPDATE tblTable Set name=@name,idPosition=@idPosition,status=@status WHERE id=@id
END

GO

CREATE PROC addTable
@name NVARCHAR(255),
@idPosition NVARCHAR(255),
@status BIT
AS
BEGIN
	INSERT INTO tblTable(name,idPosition,status)values(@name,@idPosition,@status)
END

GO

CREATE PROC findTable
@id INT
AS
BEGIN
	SELECT * FROM tblTable WHERE id=@id
END

GO

CREATE PROC editRole
@id INT,
@name NVARCHAR(255)
AS
BEGIN
	UPDATE tblRole set name=@name WHERE id=@id
END

GO

CREATE PROC editResourceDetail
@id INT,
@dishId INT,
@resourceId INT
AS
BEGIN
	UPDATE tblResourceDetail set dishId=@dishId,resourceId=@resourceId WHERE id=@id
END

GO

CREATE PROC editResource
@id INT,
@name NVARCHAR(255),
@quantity INT,
@supplier NVARCHAR(255),
@date date,
@status BIT
AS
BEGIN
	UPDATE tblResource set name=@name,quantity=@quantity,supplier=@supplier,date=@date,status=@status WHERE id=@id
END

GO

CREATE PROC editPaymentMethod
@id INT,
@name NVARCHAR(50),
@status BIT
AS
BEGIN
	UPDATE tblPaymentMethod set name=@name,status=@status WHERE id=@id
END

GO

CREATE PROC editOrder
@id INT,
@name NVARCHAR(255),
@email varchar(255),
@phone varchar(255),
@quantity INT,
@date date,
@time varchar(30),
@gENDer BIT,
@type BIT,
@requirement NVARCHAR(255),
@tableId INT
AS
BEGIN
	UPDATE tblOrder set name=@name,email=@email,phone=@phone,quantity=@quantity,date=@date,time=@time,gENDer=@gENDer,type=@type,requirement=@requirement,tableId=@tableId WHERE id=@id
END

GO

CREATE PROC editMenu
@id INT,
@name NVARCHAR(255),
@status BIT
AS
BEGIN
	UPDATE tblMenu set name=@name,status=@status WHERE id=@id
END	

GO

CREATE PROC editEmployee
@id INT,
@name NVARCHAR(255),
@age INT,
@email varchar(255),
@phone varchar(20),
@address varchar(255),
@roleId INT,
@startDate date,
@workTime FLOAT,
@rate FLOAT,
@salary FLOAT,
@status BIT,
@pASsword varchar(255)
AS
BEGIN
	UPDATE tblEmployee set name=@name,age=@age,email=@email,phone=@phone,address=@address,roleId=@roleId,startDate=@startDate,workTime=@workTime,rate=@rate,salary=@salary,status=@status,pASsword=@pASsword WHERE id=@id
END

GO

CREATE PROC editDrink
@id INT,
@name NVARCHAR(255),
@price FLOAT,
@status BIT,
@menuId INT
AS
BEGIN
	UPDATE tblDrink set name=@name,price=@price,status=@status,menuId=@menuId WHERE id = @id
END

GO

CREATE PROC editDish
@id INT,
@name NVARCHAR(255),
@price FLOAT,
@status BIT,
@menuId INT
AS
BEGIN
	UPDATE tblDish set name=@name,price=@price,status=@status,menuId=@menuId WHERE id=@id
END

GO

CREATE PROC editBillDetail
@id INT,
@billId INT,
@drinkId INT,
@dishId INT,
@drinkQuantity INT,
@dishQuantity INT,
@totalPrice FLOAT,
@discount FLOAT,
@status BIT
AS
BEGIN
	UPDATE tblBillDetail set billId=@billId,@drinkId=@drinkId,dishId=@dishId,drinkQuantity=@drinkQuantity,dishQuantity=@dishQuantity,totalPrice=@totalPrice,discount=@discount,status=@status WHERE id=@id
END

GO

CREATE PROC editBill
	@id INT,
	@status BIT,
	@totalCost FLOAT,
	@startDate datetime,
	@endDate datetime,
	@numberCustomer INT,
	@nameCustomer NVARCHAR(50),
	@idEmployee INT,
	@idTable INT,
	@paymentMethodId INT,
	@note NVARCHAR(255)
AS
BEGIN
	UPDATE tblBill set endDate = @endDate, status = @status,
	totalCost = @totalCost, startDate = @startDate,
	numberCustomer = @numberCustomer, nameCustomer = @nameCustomer,
	idEmployee = @idEmployee, idTable = @idTable,
	paymentMethodId = @paymentMethodId WHERE id = @id
END
-------------------------------------------------------
GO

CREATE PROC findRole
@id INT
AS
BEGIN
	select * FROM tblRole WHERE id=@id
END

GO

CREATE PROC findResourceDetail
@id INT
AS
BEGIN
	select * FROM tblResourceDetail WHERE id=@id
END

GO

CREATE PROC findResource
@id INT
AS
BEGIN
	select * FROM tblResource WHERE id=@id
END

GO

CREATE PROC findPaymentMethod
@id INT
AS
BEGIN
	select * FROM tblPaymentMethod WHERE id=@id
END

GO

CREATE PROC findOrder
@id INT
AS
BEGIN
	select * FROM tblOrder WHERE id=@id
END

GO

CREATE PROC findMenu
@id INT
AS
BEGIN
	select * FROM tblMenu WHERE id=@id
END

GO

CREATE PROC findEmployee
@id INT
AS
BEGIN
	select * FROM tblEmployee WHERE id=@id
END

GO

CREATE PROC findDrink
@id INT
AS
BEGIN
	select * FROM tblDrink WHERE id=@id
END

GO

CREATE PROC findDish
@id INT
AS
BEGIN
	select * FROM tblDish WHERE id=@id
END

GO

CREATE PROC findBillDetail
@id INT
AS
BEGIN
	select * FROM tblBillDetail WHERE id=@id
END

GO

-------------------------------------------------------------------------------------------
CREATE PROC removeTable
@id INT
AS
BEGIN
	DELETE FROM tblTable WHERE id=@id
END

GO

CREATE PROC removeRole
@id INT
AS
BEGIN
	DELETE FROM tblRole WHERE id=@id
END

GO

CREATE PROC removeResourceDetail
@id INT
AS
BEGIN
	DELETE FROM tblResourceDetail WHERE id=@id
END

GO

CREATE PROC removeResource
@id INT
AS
BEGIN
	DELETE FROM tblResource WHERE id=@id
END

GO

CREATE PROC removePaymentMethod
@id INT
AS
BEGIN
	DELETE FROM tblPaymentMethod WHERE id=@id
END

GO

CREATE PROC removeOrder
@id INT
AS
BEGIN
	DELETE FROM tblOrder WHERE id=@id
END

GO

CREATE PROC removeMenu
@id INT
AS
BEGIN
	DELETE FROM tblMenu WHERE id=@id
END

GO

CREATE PROC removeEmployee
@id INT
AS
BEGIN
	DELETE FROM tblEmployee WHERE id=@id
END

GO

CREATE PROC removeDrink
@id INT
AS
BEGIN
	DELETE FROM tblDrink WHERE id=@id
END

GO

CREATE PROC removeDish
@id INT
AS
BEGIN
	DELETE FROM tblDish WHERE id=@id
END

GO

CREATE PROC removeBillDetail
@id INT
AS
BEGIN
	DELETE FROM tblBillDetail WHERE id=@id
END

GO

CREATE PROC removeBill
@id INT
AS
BEGIN
	DELETE FROM tblBill WHERE id=@id
END

GO

SELECT * 
  FROM Restaurant.INFORMATION_SCHEMA.ROUTINES
 WHERE ROUTINE_TYPE = 'PROCEDURE'

 GO

CREATE PROC findPosition
@id INT
AS
BEGIN
	select * FROM tblPosition WHERE id = @id
END

findPosition 2

GO

INSERT INTO tblRole VALUES (N'Quản lý'),
						   (N'Nhân viên')

GO

INSERT INTO tblEmployee VALUES (N'Nguyễn Văn Tân',	25,	'tannguyenvan@gmail.com',	0349018290,	N'Hải Dương',	1,	'2019-09-07',	NULL,	1.5,	5000000, 1 ,	'21232f297a57a5a743894a0e4a801fc3')

GO

INSERT INTO tblPaymentMethod VALUES (N'tiền mặt',1),
									(N'quẹt thẻ',0)


--PROC getAllDishJoin

CREATE PROC getAllDishJoin
AS
BEGIN
	SELECT 
		   tblDish.id,
		   tblDish.name,
		   tblDish.price,
		   tblDish.status,
		   tblMenu.name as 'menuName',
		   tblMenu.id as 'menuId'
	FROM tblDish JOIN tblMenu ON tblDish.menuId = tblMenu.id
END

getAllDishJoin

SELECT 
	dish.name,
	detail.dishQuantity,
	dish.price
FROM tblDish dish JOIN tblBillDetail detail
ON dish.id = detail.dishId
WHERE detail.billId = 11

SELECT 
	drink.name,
	detail.drinkQuantity,
	drink.price
FROM tblDrink drink JOIN tblBillDetail detail
ON drink.id = detail.drinkId
WHERE detail.billId = 11