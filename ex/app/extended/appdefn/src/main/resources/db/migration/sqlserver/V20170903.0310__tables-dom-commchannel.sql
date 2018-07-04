SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [incodeCommChannel].[CommunicationChannel](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[notes] [text] NULL,
	[purpose] [varchar](30) NOT NULL,
	[type] [varchar](30) NOT NULL,
	[version] [bigint] NOT NULL,
	[discriminator] [varchar](255) NOT NULL,
	[phoneNumber] [varchar](20) NULL,
	[emailAddress] [varchar](254) NULL,
	[addressComponents] [text] NULL,
	[addressLine1] [varchar](50) NULL,
	[addressLine2] [varchar](50) NULL,
	[addressLine3] [varchar](50) NULL,
	[addressLine4] [varchar](50) NULL,
	[country] [varchar](50) NULL,
	[formattedAddress] [varchar](254) NULL,
	[geocodeApiResponseAsJson] [text] NULL,
	[latLng] [varchar](255) NULL,
	[placeId] [varchar](255) NULL,
	[postalCode] [varchar](12) NULL,
 CONSTRAINT [CommunicationChannel_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [EmailAddress_emailAddress_IDX] ON [incodeCommChannel].[CommunicationChannel]
(
	[emailAddress] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PhoneNumber_phoneNumber_IDX] ON [incodeCommChannel].[CommunicationChannel]
(
	[phoneNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PostalAddress_formattedAddress_idx] ON [incodeCommChannel].[CommunicationChannel]
(
	[formattedAddress] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PostalAddress_unq_idx] ON [incodeCommChannel].[CommunicationChannel]
(
	[placeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





CREATE TABLE [incodeCommChannel].[CommunicationChannelOwnerLink](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[communicationChannelId] [bigint] NOT NULL,
	[communicationChannelType] [varchar](30) NOT NULL,
	[ownerStr] [varchar](2000) NOT NULL,
 CONSTRAINT [CommunicationChannelOwnerLink_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [CommunicationChannelOwnerLink_commchannel_UNQ] UNIQUE NONCLUSTERED
(
	[communicationChannelId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [CommunicationChannelOwnerLink_main_idx] ON [incodeCommChannel].[CommunicationChannelOwnerLink]
(
	[ownerStr] ASC,
	[communicationChannelType] ASC,
	[communicationChannelId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




ALTER TABLE [incodeCommChannel].[CommunicationChannelOwnerLink]  WITH CHECK ADD  CONSTRAINT [CommunicationChannelOwnerLink_FK1] FOREIGN KEY([communicationChannelId])
REFERENCES [incodeCommChannel].[CommunicationChannel] ([id])
GO
ALTER TABLE [incodeCommChannel].[CommunicationChannelOwnerLink] CHECK CONSTRAINT [CommunicationChannelOwnerLink_FK1]
GO


