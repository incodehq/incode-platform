--
-- https://github.com/incodehq/incode-platform/issues/88
--

ALTER TABLE [isispublishmq].[StatusMessage] DROP CONSTRAINT [StatusMessage_PK]
GO

ALTER TABLE [isispublishmq].[StatusMessage] ADD [sequence] INT
go
UPDATE [isispublishmq].[StatusMessage] SET [sequence] = 0 where [sequence] IS NULL
go
ALTER TABLE [isispublishmq].[StatusMessage] ALTER COLUMN [sequence] INT NOT NULL
go

ALTER TABLE [isispublishmq].[StatusMessage] ADD  CONSTRAINT [StatusMessage_PK] PRIMARY KEY CLUSTERED
(
	[timestamp] ASC,
	[transactionId] ASC,
	[sequence] ASC
)
GO


--
--to revert:
--
--ALTER TABLE [isispublishmq].[StatusMessage] DROP CONSTRAINT [StatusMessage_PK]
--GO
--
--ALTER TABLE [isispublishmq].[StatusMessage] DROP COLUMN [sequence]
--GO
--
--ALTER TABLE [isispublishmq].[StatusMessage] ADD  CONSTRAINT [StatusMessage_PK] PRIMARY KEY CLUSTERED
--(
--	[timestamp] ASC,
--	[transactionId] ASC
--)
--GO
