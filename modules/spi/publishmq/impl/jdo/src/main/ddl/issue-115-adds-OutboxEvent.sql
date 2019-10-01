--
-- https://github.com/incodehq/incode-platform/issues/115
--

CREATE TABLE [isispublishmq].[OutboxEvent](
	[sequence] [int] NOT NULL,
	[transactionId] [varchar](36) NOT NULL,
	[eventType] [varchar](20) NOT NULL,
	[memberIdentifier] [varchar](255) NULL,
	[serializedForm] [text] NULL,
	[targetAction] [varchar](50) NULL,
	[targetClass] [varchar](50) NULL,
	[target] [varchar](2000) NULL,
	[timestamp] [datetime2](7) NOT NULL,
	[title] [varchar](255) NOT NULL,
	[user] [varchar](50) NOT NULL,
 CONSTRAINT [OutboxEvent_PK] PRIMARY KEY CLUSTERED 
(

	[transactionId] ASC,
	[sequence] ASC
) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, FILLFACTOR = 12) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

go
