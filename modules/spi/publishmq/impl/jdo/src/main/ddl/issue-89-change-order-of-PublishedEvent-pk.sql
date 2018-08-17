--
-- https://github.com/incodehq/incode-platform/issues/88
--


--
-- was defined as (sequence, transactionId), should be the other way around to avoid unnecessary table scans
--

ALTER TABLE [isispublishmq].[PublishedEvent] DROP CONSTRAINT [PublishedEvent_PK]
GO

ALTER TABLE [isispublishmq].[PublishedEvent] ADD  CONSTRAINT [PublishedEvent_PK] PRIMARY KEY CLUSTERED
(
	[transactionId] ASC,
	[sequence] ASC
)
GO



