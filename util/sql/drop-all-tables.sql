DECLARE @table_schema varchar(100)
       ,@table_name varchar(100)
       ,@constraint_schema varchar(100)
       ,@constraint_name varchar(100)
       ,@cmd nvarchar(200)


--
-- drop all the constraints
--
DECLARE constraint_cursor CURSOR FOR
  select CONSTRAINT_SCHEMA, CONSTRAINT_NAME, TABLE_SCHEMA, TABLE_NAME
    from INFORMATION_SCHEMA.TABLE_CONSTRAINTS
   where TABLE_NAME != 'sysdiagrams'
    order by CONSTRAINT_TYPE asc -- FOREIGN KEY, then PRIMARY KEY
     

OPEN constraint_cursor
FETCH NEXT FROM constraint_cursor INTO @constraint_schema, @constraint_name, @table_schema, @table_name

WHILE @@FETCH_STATUS = 0 
BEGIN 
     SELECT @cmd = 'ALTER TABLE [' + @table_schema + '].[' + @table_name + '] DROP CONSTRAINT ' + @constraint_name
     EXEC sp_executesql @cmd
     --select @cmd

     FETCH NEXT FROM constraint_cursor INTO @constraint_schema, @constraint_name, @table_schema, @table_name
END

CLOSE constraint_cursor
DEALLOCATE constraint_cursor



--
-- drop all the views
--
DECLARE view_cursor CURSOR FOR
  select TABLE_SCHEMA, TABLE_NAME
    from INFORMATION_SCHEMA.TABLES
   where TABLE_NAME != 'sysdiagrams'
     and TABLE_TYPE = 'VIEW'

OPEN view_cursor
FETCH NEXT FROM view_cursor INTO @table_schema, @table_name

WHILE @@FETCH_STATUS = 0 
BEGIN 
     SELECT @cmd = 'DROP VIEW [' + @table_schema + '].[' + @table_name + ']'
     EXEC sp_executesql @cmd
     --select @cmd


     FETCH NEXT FROM view_cursor INTO @table_schema, @table_name
END

CLOSE view_cursor 
DEALLOCATE view_cursor



--
-- drop all the tables
--
DECLARE table_cursor CURSOR FOR
  select TABLE_SCHEMA, TABLE_NAME
    from INFORMATION_SCHEMA.TABLES
   where TABLE_NAME NOT IN ('sysdiagrams', 'schema_version')
     and TABLE_TYPE != 'VIEW'

OPEN table_cursor
FETCH NEXT FROM table_cursor INTO @table_schema, @table_name

WHILE @@FETCH_STATUS = 0 
BEGIN 
     SELECT @cmd = 'DROP TABLE [' + @table_schema + '].[' + @table_name + ']'
     EXEC sp_executesql @cmd
     --select @cmd


     FETCH NEXT FROM table_cursor INTO @table_schema, @table_name
END

CLOSE table_cursor 
DEALLOCATE table_cursor

