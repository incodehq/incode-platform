-- disable all constraints
EXEC sp_MSForEachTable "ALTER TABLE ? NOCHECK CONSTRAINT all"

-- delete data in all tables (excluding flyway's dbo.schema_version)
-- (can't use truncate since constraints are present, just disabled)
-- (took 2m41sec as of 2016-12-02)
EXEC sp_MSForEachTable 'IF OBJECT_ID(''?'') <> ISNULL(OBJECT_ID(''[dbo].[schema_version]''),0)
                            DELETE FROM ?'

-- enable all constraints
EXEC sp_msforeachtable "ALTER TABLE ? WITH CHECK CHECK CONSTRAINT all"

--some of the tables have identity columns we may want to reseed them
--(ignore any errors thrown)
EXEC sp_MSforeachtable 'if exists (SELECT 1 FROM sys.identity_columns WHERE object_id = object_id(''?''))
                            DBCC CHECKIDENT ( ''?'', RESEED, 0)'


