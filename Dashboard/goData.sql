/*
	Filename:			goData
	Author:				Aysham Ali Hameed
	Created:			13th October 2018
	Operating System:	Windows 10
	Version:			MSSMS v17.8.1
	Description:		Creating the database and tables.
	
*/

	--CREATING DATABASE================================================
	USE MASTER
	GO

	PRINT 'Using master database.'
	GO

	IF EXISTS(SELECT name FROM master.dbo.sysdatabases where name='pDatabase')
	BEGIN
		DROP DATABASE pDatabase
		PRINT 'Existing database dropped.'
	END
	GO

	CREATE DATABASE pDatabase							
			ON PRIMARY(								
				NAME='pDatabase_data', 
				FILENAME='C:\DB\pDatabase_data.mdf',
				SIZE=5MB,							
				FILEGROWTH=10%						
			)
			LOG ON(									
				NAME='pDatabase_log',
				FILENAME='C:\DB\pDatabase_log.ldf',	
				SIZE=5MB,							
				FILEGROWTH=10%						
			)		
	GO
	



	--CREATING TABLES================================================

	USE pDatabase
	GO

	
	DROP TABLE IF EXISTS dbo.Users
	GO
	DROP TABLE IF EXISTS dbo.Animals
	GO
	DROP TABLE IF EXISTS dbo.Species
	GO


	CREATE TABLE Species(
		speciesID VARCHAR(20) NOT NULL PRIMARY KEY,
		speciesName VARCHAR(30) NOT NULL
	)
	GO

	CREATE TABLE Animals(
		animalID VARCHAR(20) NOT NULL PRIMARY KEY,
		animalName VARCHAR(30) NOT NULL,
		animalDesc VARCHAR(100) NOT NULL,
		speciesID VARCHAR(20) NOT NULL REFERENCES Species(speciesID) ON DELETE CASCADE 
	)
	GO

	CREATE TABLE Users(
		userID VARCHAR(20) NOT NULL PRIMARY KEY,
		userName VARCHAR(30) NOT NULL UNIQUE,
		userPassword VARCHAR(30) NOT NULL
	)
	GO

	--INSERTING DATA INTO Users TABLE

	INSERT INTO Users
	VALUES	('1','BruceW7','bruceisbatman'),
			('2','PeterP8','peterisspiderman#123')
	GO
