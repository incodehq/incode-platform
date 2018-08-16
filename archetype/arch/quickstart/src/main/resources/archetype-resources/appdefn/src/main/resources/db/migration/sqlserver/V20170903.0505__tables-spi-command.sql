SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [isiscommand].[Command](
	[transactionId] [varchar](36) NOT NULL,
	[arguments] [text] NULL,
	[completedAt] [datetime2](7) NULL,
	[exception] [text] NULL,
	[executeIn] [varchar](10) NOT NULL,
	[memberIdentifier] [varchar](255) NOT NULL,
	[memento] [text] NULL,
	[parentTransactionId] [varchar](36) NULL,
	[result] [varchar](2000) NULL,
	[startedAt] [datetime2](7) NULL,
	[targetAction] [varchar](50) NOT NULL,
	[targetClass] [varchar](50) NOT NULL,
	[target] [varchar](2000) NULL,
	[timestamp] [datetime2](7) NOT NULL,
	[user] [varchar](50) NOT NULL,
 CONSTRAINT [Command_PK] PRIMARY KEY CLUSTERED
(
	[transactionId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Command_N49] ON [isiscommand].[Command]
(
	[parentTransactionId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




ALTER TABLE [isiscommand].[Command]  WITH CHECK ADD  CONSTRAINT [Command_FK1] FOREIGN KEY([parentTransactionId])
REFERENCES [isiscommand].[Command] ([transactionId])
GO
ALTER TABLE [isiscommand].[Command] CHECK CONSTRAINT [Command_FK1]
GO
