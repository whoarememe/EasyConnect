/*
Navicat SQL Server Data Transfer

Source Server         : easyConnect
Source Server Version : 110000
Source Host           : 118.89.239.67:1433
Source Database       : EasyConnect
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 110000
File Encoding         : 65001

Date: 2017-07-16 23:10:45
*/


-- ----------------------------
-- Table structure for AppUser
-- ----------------------------
DROP TABLE [dbo].[AppUser]
GO
CREATE TABLE [dbo].[AppUser] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(255) NOT NULL ,
[password] varchar(255) NOT NULL ,
[phone] varchar(255) NOT NULL ,
[mail] varchar(255) NOT NULL ,
[ip] varchar(255) NULL ,
[port] int NULL ,
[state] int NOT NULL ,
[heartTime] bigint NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[AppUser]', RESEED, 23)
GO

-- ----------------------------
-- Table structure for Developer
-- ----------------------------
DROP TABLE [dbo].[Developer]
GO
CREATE TABLE [dbo].[Developer] (
[id] int NOT NULL IDENTITY(1,1) ,
[manufacturer_id] int NOT NULL ,
[password] varchar(255) NOT NULL ,
[phone] varchar(255) NOT NULL ,
[name] varchar(255) NOT NULL ,
[state] int NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[Developer]', RESEED, 16)
GO

-- ----------------------------
-- Table structure for DeveloperDevice
-- ----------------------------
DROP TABLE [dbo].[DeveloperDevice]
GO
CREATE TABLE [dbo].[DeveloperDevice] (
[id] int NOT NULL IDENTITY(1,1) ,
[developer_id] int NOT NULL ,
[type_id] int NOT NULL ,
[model] varchar(255) NOT NULL ,
[state] int NOT NULL ,
[key_word] varchar(255) NULL ,
[fucntion_id] int NOT NULL ,
[description] varchar(255) NOT NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[DeveloperDevice]', RESEED, 25)
GO

-- ----------------------------
-- Table structure for DeviceInUsing
-- ----------------------------
DROP TABLE [dbo].[DeviceInUsing]
GO
CREATE TABLE [dbo].[DeviceInUsing] (
[device_id] int NOT NULL ,
[developer_device_id] int NOT NULL ,
[password] varchar(255) NOT NULL ,
[ip] varchar(255) NULL ,
[port] int NULL ,
[state] int NOT NULL ,
[heartTime] bigint NULL 
)


GO

-- ----------------------------
-- Table structure for DeviceType
-- ----------------------------
DROP TABLE [dbo].[DeviceType]
GO
CREATE TABLE [dbo].[DeviceType] (
[id] int NOT NULL IDENTITY(1,1) ,
[type_name] varchar(255) NOT NULL ,
[pic] varchar(255) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[DeviceType]', RESEED, 3)
GO

-- ----------------------------
-- Table structure for Fucntion
-- ----------------------------
DROP TABLE [dbo].[Fucntion]
GO
CREATE TABLE [dbo].[Fucntion] (
[id] int NOT NULL IDENTITY(1,1) ,
[functions] varchar(255) NOT NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[Fucntion]', RESEED, 28)
GO

-- ----------------------------
-- Table structure for Manufacturer
-- ----------------------------
DROP TABLE [dbo].[Manufacturer]
GO
CREATE TABLE [dbo].[Manufacturer] (
[id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(255) NOT NULL ,
[addr] varchar(255) NOT NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[Manufacturer]', RESEED, 2)
GO

-- ----------------------------
-- Table structure for Message
-- ----------------------------
DROP TABLE [dbo].[Message]
GO
CREATE TABLE [dbo].[Message] (
[id] int NOT NULL IDENTITY(1,1) ,
[direction] int NOT NULL ,
[user_id] int NOT NULL ,
[device_id] int NOT NULL ,
[msg_type] int NOT NULL ,
[msg] varchar(255) NULL ,
[time] bigint NULL ,
[hasread] int NOT NULL ,
[level] int NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[Message]', RESEED, 930)
GO

-- ----------------------------
-- Table structure for Test
-- ----------------------------
DROP TABLE [dbo].[Test]
GO
CREATE TABLE [dbo].[Test] (
[test_id] int NOT NULL IDENTITY(1,1) ,
[name] varchar(20) NULL 
)


GO

-- ----------------------------
-- Table structure for UserDevice
-- ----------------------------
DROP TABLE [dbo].[UserDevice]
GO
CREATE TABLE [dbo].[UserDevice] (
[id] int NOT NULL IDENTITY(1,1) ,
[user_id] int NOT NULL ,
[device_id] int NOT NULL ,
[authority] int NOT NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[UserDevice]', RESEED, 35)
GO

-- ----------------------------
-- Indexes structure for table AppUser
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table AppUser
-- ----------------------------
ALTER TABLE [dbo].[AppUser] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Developer
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table Developer
-- ----------------------------
ALTER TABLE [dbo].[Developer] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table DeveloperDevice
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table DeveloperDevice
-- ----------------------------
ALTER TABLE [dbo].[DeveloperDevice] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table DeviceInUsing
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table DeviceInUsing
-- ----------------------------
ALTER TABLE [dbo].[DeviceInUsing] ADD PRIMARY KEY ([device_id])
GO

-- ----------------------------
-- Indexes structure for table DeviceType
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table DeviceType
-- ----------------------------
ALTER TABLE [dbo].[DeviceType] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Fucntion
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table Fucntion
-- ----------------------------
ALTER TABLE [dbo].[Fucntion] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Manufacturer
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table Manufacturer
-- ----------------------------
ALTER TABLE [dbo].[Manufacturer] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Message
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table Message
-- ----------------------------
ALTER TABLE [dbo].[Message] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Indexes structure for table Test
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table Test
-- ----------------------------
ALTER TABLE [dbo].[Test] ADD PRIMARY KEY ([test_id])
GO

-- ----------------------------
-- Indexes structure for table UserDevice
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table UserDevice
-- ----------------------------
ALTER TABLE [dbo].[UserDevice] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[DeveloperDevice]
-- ----------------------------
ALTER TABLE [dbo].[DeveloperDevice] ADD FOREIGN KEY ([developer_id]) REFERENCES [dbo].[Developer] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[DeveloperDevice] ADD FOREIGN KEY ([type_id]) REFERENCES [dbo].[DeviceType] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[DeveloperDevice] ADD FOREIGN KEY ([fucntion_id]) REFERENCES [dbo].[Fucntion] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[DeviceInUsing]
-- ----------------------------
ALTER TABLE [dbo].[DeviceInUsing] ADD FOREIGN KEY ([developer_device_id]) REFERENCES [dbo].[DeveloperDevice] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[Message]
-- ----------------------------
ALTER TABLE [dbo].[Message] ADD FOREIGN KEY ([device_id]) REFERENCES [dbo].[DeviceInUsing] ([device_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[Message] ADD FOREIGN KEY ([user_id]) REFERENCES [dbo].[AppUser] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[UserDevice]
-- ----------------------------
ALTER TABLE [dbo].[UserDevice] ADD FOREIGN KEY ([device_id]) REFERENCES [dbo].[DeviceInUsing] ([device_id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
ALTER TABLE [dbo].[UserDevice] ADD FOREIGN KEY ([user_id]) REFERENCES [dbo].[AppUser] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
