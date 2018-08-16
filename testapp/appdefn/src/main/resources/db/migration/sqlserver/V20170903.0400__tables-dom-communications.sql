SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO

--
-- has FK to documents module.
--


CREATE TABLE [IncodeCommunications].[Communication](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[atPath] [varchar](255) NOT NULL,
	[createdAt] [datetime2](7) NOT NULL,
	[sentAt] [datetime2](7) NULL,
	[stateId] [varchar](255) NOT NULL,
	[subject] [varchar](255) NULL,
	[typeId] [varchar](255) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [Communication_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [Communication_atPath_type_IDX] ON [IncodeCommunications].[Communication]
(
	[atPath] ASC,
	[typeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Communication_type_atPath_IDX] ON [IncodeCommunications].[Communication]
(
	[typeId] ASC,
	[atPath] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





CREATE TABLE [IncodeCommunications].[CommunicationChannel](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[description] [varchar](254) NULL,
	[legal] [bit] NOT NULL,
	[purpose] [varchar](30) NULL,
	[reference] [varchar](24) NULL,
	[type] [varchar](30) NOT NULL,
	[version] [bigint] NOT NULL,
	[discriminator] [varchar](255) NOT NULL,
	[emailAddress] [varchar](254) NULL,
	[phoneNumber] [varchar](20) NULL,
	[address1] [varchar](100) NULL,
	[address2] [varchar](100) NULL,
	[address3] [varchar](100) NULL,
	[city] [varchar](50) NULL,
	[countryId] [bigint] NULL,
	[postalCode] [varchar](12) NULL,
	[stateId] [bigint] NULL,
 CONSTRAINT [CommunicationChannel_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [CommunicationChannel_N49] ON [IncodeCommunications].[CommunicationChannel]
(
	[countryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [CommunicationChannel_N50] ON [IncodeCommunications].[CommunicationChannel]
(
	[stateId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [EmailAddress_emailAddress_IDX] ON [IncodeCommunications].[CommunicationChannel]
(
	[emailAddress] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PhoneNumber_phoneNumber_IDX] ON [IncodeCommunications].[CommunicationChannel]
(
	[phoneNumber] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PostalAddress_main_idx] ON [IncodeCommunications].[CommunicationChannel]
(
	[address1] ASC,
	[postalCode] ASC,
	[city] ASC,
	[countryId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




CREATE TABLE [IncodeCommunications].[PaperclipForCommunication](
	[id] [bigint] NOT NULL,
	[communicationId] [bigint] NOT NULL,
 CONSTRAINT [PaperclipForCommunication_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [PaperclipForCommunication_N49] ON [IncodeCommunications].[PaperclipForCommunication]
(
	[communicationId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO


CREATE TABLE [IncodeCommunications].[CommunicationChannelOwnerLink](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[communicationChannelId] [bigint] NOT NULL,
	[communicationChannelType] [varchar](30) NOT NULL,
	[ownerIdentifier] [varchar](255) NOT NULL,
	[ownerObjectType] [varchar](255) NOT NULL,
 CONSTRAINT [CommunicationChannelOwnerLink_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [CommunicationChannelOwnerLink_commchannel_owner_UNQ] UNIQUE NONCLUSTERED
(
	[communicationChannelId] ASC,
	[ownerObjectType] ASC,
	[ownerIdentifier] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [CommunicationChannelOwnerLink_main_idx] ON [IncodeCommunications].[CommunicationChannelOwnerLink]
(
	[ownerObjectType] ASC,
	[ownerIdentifier] ASC,
	[communicationChannelType] ASC,
	[communicationChannelId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [CommunicationChannelOwnerLink_N49] ON [IncodeCommunications].[CommunicationChannelOwnerLink]
(
	[communicationChannelId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO



CREATE TABLE [IncodeCommunications].[CommChannelRole](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[channelId] [bigint] NULL,
	[communicationId] [bigint] NOT NULL,
	[description] [varchar](255) NULL,
	[typeId] [varchar](255) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [CommChannelRole_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [CommChannelRole_comm_channel_type_IDX] ON [IncodeCommunications].[CommChannelRole]
(
	[communicationId] ASC,
	[channelId] ASC,
	[typeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [CommChannelRole_comm_type_channel_IDX] ON [IncodeCommunications].[CommChannelRole]
(
	[communicationId] ASC,
	[typeId] ASC,
	[channelId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [CommChannelRole_N49] ON [IncodeCommunications].[CommChannelRole]
(
	[channelId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [CommChannelRole_N50] ON [IncodeCommunications].[CommChannelRole]
(
	[communicationId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Communication_channel_comm_type_IDX] ON [IncodeCommunications].[CommChannelRole]
(
	[channelId] ASC,
	[communicationId] ASC,
	[typeId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [Communication_channel_type_comm_IDX] ON [IncodeCommunications].[CommChannelRole]
(
	[channelId] ASC,
	[typeId] ASC,
	[communicationId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




ALTER TABLE [IncodeCommunications].[CommunicationChannel]  WITH CHECK ADD  CONSTRAINT [CommunicationChannel_FK1] FOREIGN KEY([countryId])
REFERENCES [incodeCountry].[Country] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommunicationChannel] CHECK CONSTRAINT [CommunicationChannel_FK1]
GO


ALTER TABLE [IncodeCommunications].[CommunicationChannel]  WITH CHECK ADD  CONSTRAINT [CommunicationChannel_FK2] FOREIGN KEY([stateId])
REFERENCES [incodeCountry].[State] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommunicationChannel] CHECK CONSTRAINT [CommunicationChannel_FK2]
GO




ALTER TABLE [IncodeCommunications].[PaperclipForCommunication]  WITH CHECK ADD  CONSTRAINT [PaperclipForCommunication_FK1] FOREIGN KEY([id])
REFERENCES [incodeDocuments].[Paperclip] ([id])
GO
ALTER TABLE [IncodeCommunications].[PaperclipForCommunication] CHECK CONSTRAINT [PaperclipForCommunication_FK1]
GO


ALTER TABLE [IncodeCommunications].[PaperclipForCommunication]  WITH CHECK ADD  CONSTRAINT [PaperclipForCommunication_FK2] FOREIGN KEY([communicationId])
REFERENCES [IncodeCommunications].[Communication] ([id])
GO
ALTER TABLE [IncodeCommunications].[PaperclipForCommunication] CHECK CONSTRAINT [PaperclipForCommunication_FK2]
GO


ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink]  WITH CHECK ADD  CONSTRAINT [CommunicationChannelOwnerLink_FK1] FOREIGN KEY([communicationChannelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink] CHECK CONSTRAINT [CommunicationChannelOwnerLink_FK1]
GO


ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink]  WITH CHECK ADD  CONSTRAINT [CommunicationChannelOwnerLink_FK2] FOREIGN KEY([communicationChannelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink] CHECK CONSTRAINT [CommunicationChannelOwnerLink_FK2]
GO


ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink]  WITH CHECK ADD  CONSTRAINT [CommunicationChannelOwnerLink_FK3] FOREIGN KEY([communicationChannelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink] CHECK CONSTRAINT [CommunicationChannelOwnerLink_FK3]
GO


ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink]  WITH CHECK ADD  CONSTRAINT [CommunicationChannelOwnerLink_FK4] FOREIGN KEY([communicationChannelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommunicationChannelOwnerLink] CHECK CONSTRAINT [CommunicationChannelOwnerLink_FK4]
GO


ALTER TABLE [IncodeCommunications].[CommChannelRole]  WITH CHECK ADD  CONSTRAINT [CommChannelRole_FK1] FOREIGN KEY([channelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommChannelRole] CHECK CONSTRAINT [CommChannelRole_FK1]
GO


ALTER TABLE [IncodeCommunications].[CommChannelRole]  WITH CHECK ADD  CONSTRAINT [CommChannelRole_FK2] FOREIGN KEY([communicationId])
REFERENCES [IncodeCommunications].[Communication] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommChannelRole] CHECK CONSTRAINT [CommChannelRole_FK2]
GO


ALTER TABLE [IncodeCommunications].[CommChannelRole]  WITH CHECK ADD  CONSTRAINT [CommChannelRole_FK3] FOREIGN KEY([channelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommChannelRole] CHECK CONSTRAINT [CommChannelRole_FK3]
GO


ALTER TABLE [IncodeCommunications].[CommChannelRole]  WITH CHECK ADD  CONSTRAINT [CommChannelRole_FK4] FOREIGN KEY([channelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommChannelRole] CHECK CONSTRAINT [CommChannelRole_FK4]
GO



ALTER TABLE [IncodeCommunications].[CommChannelRole]  WITH CHECK ADD  CONSTRAINT [CommChannelRole_FK5] FOREIGN KEY([channelId])
REFERENCES [IncodeCommunications].[CommunicationChannel] ([id])
GO
ALTER TABLE [IncodeCommunications].[CommChannelRole] CHECK CONSTRAINT [CommChannelRole_FK5]
GO
