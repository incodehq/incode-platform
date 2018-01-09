SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [isispublishmq].[StatusMessage](
	[timestamp] [datetime2](7) NOT NULL,
	[transactionId] [varchar](36) NOT NULL,
	[detail] [text] NULL,
	[message] [varchar](255) NOT NULL,
	[oid] [varchar](2000) NULL,
	[status] [int] NULL,
	[uri] [varchar](2000) NULL,
 CONSTRAINT [StatusMessage_PK] PRIMARY KEY CLUSTERED
(
	[timestamp] ASC,
	[transactionId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


CREATE TABLE [isispublishmq].[PublishedEvent](
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
 CONSTRAINT [PublishedEvent_PK] PRIMARY KEY CLUSTERED
(
	[sequence] ASC,
	[transactionId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
