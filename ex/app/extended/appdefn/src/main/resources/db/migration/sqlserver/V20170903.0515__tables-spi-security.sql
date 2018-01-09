SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO


CREATE TABLE [isissecurity].[ApplicationUser](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[accountType] [varchar](255) NOT NULL,
	[atPath] [varchar](255) NULL,
	[emailAddress] [varchar](50) NULL,
	[encryptedPassword] [varchar](255) NULL,
	[familyName] [varchar](50) NULL,
	[faxNumber] [varchar](25) NULL,
	[givenName] [varchar](50) NULL,
	[knownAs] [varchar](20) NULL,
	[phoneNumber] [varchar](25) NULL,
	[status] [varchar](255) NOT NULL,
	[username] [varchar](30) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [ApplicationUser_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [ApplicationUser_username_UNQ] UNIQUE NONCLUSTERED
(
	[username] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO



CREATE TABLE [isissecurity].[ApplicationTenancy](
	[path] [varchar](255) NOT NULL,
	[name] [varchar](40) NOT NULL,
	[parentPath] [varchar](255) NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [ApplicationTenancy_PK] PRIMARY KEY CLUSTERED
(
	[path] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [ApplicationTenancy_name_UNQ] UNIQUE NONCLUSTERED
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [ApplicationTenancy_N49] ON [isissecurity].[ApplicationTenancy]
(
	[parentPath] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO



CREATE TABLE [isissecurity].[ApplicationRole](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[description] [varchar](254) NULL,
	[name] [varchar](50) NOT NULL,
 CONSTRAINT [ApplicationRole_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [ApplicationRole_name_UNQ] UNIQUE NONCLUSTERED
(
	[name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO




CREATE TABLE [isissecurity].[ApplicationUserRoles](
	[userId] [bigint] NOT NULL,
	[roleId] [bigint] NOT NULL,
 CONSTRAINT [ApplicationUserRoles_PK] PRIMARY KEY CLUSTERED
(
	[userId] ASC,
	[roleId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [ApplicationUserRoles_N49] ON [isissecurity].[ApplicationUserRoles]
(
	[userId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO
CREATE NONCLUSTERED INDEX [ApplicationUserRoles_N50] ON [isissecurity].[ApplicationUserRoles]
(
	[roleId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO




CREATE TABLE [isissecurity].[ApplicationPermission](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[featureFqn] [varchar](255) NOT NULL,
	[featureType] [varchar](255) NOT NULL,
	[mode] [varchar](255) NOT NULL,
	[roleId] [bigint] NOT NULL,
	[rule] [varchar](255) NOT NULL,
	[version] [bigint] NOT NULL,
 CONSTRAINT [ApplicationPermission_PK] PRIMARY KEY CLUSTERED
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [ApplicationPermission_role_feature_rule_UNQ] UNIQUE NONCLUSTERED
(
	[roleId] ASC,
	[featureType] ASC,
	[featureFqn] ASC,
	[rule] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO


CREATE NONCLUSTERED INDEX [ApplicationPermission_N49] ON [isissecurity].[ApplicationPermission]
(
	[roleId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
GO





ALTER TABLE [isissecurity].[ApplicationTenancy]  WITH CHECK ADD  CONSTRAINT [ApplicationTenancy_FK1] FOREIGN KEY([parentPath])
REFERENCES [isissecurity].[ApplicationTenancy] ([path])
GO
ALTER TABLE [isissecurity].[ApplicationTenancy] CHECK CONSTRAINT [ApplicationTenancy_FK1]
GO


ALTER TABLE [isissecurity].[ApplicationUserRoles]  WITH CHECK ADD  CONSTRAINT [ApplicationUserRoles_FK1] FOREIGN KEY([userId])
REFERENCES [isissecurity].[ApplicationUser] ([id])
GO
ALTER TABLE [isissecurity].[ApplicationUserRoles] CHECK CONSTRAINT [ApplicationUserRoles_FK1]
GO


ALTER TABLE [isissecurity].[ApplicationUserRoles]  WITH CHECK ADD  CONSTRAINT [ApplicationUserRoles_FK2] FOREIGN KEY([roleId])
REFERENCES [isissecurity].[ApplicationRole] ([id])
GO
ALTER TABLE [isissecurity].[ApplicationUserRoles] CHECK CONSTRAINT [ApplicationUserRoles_FK2]
GO


ALTER TABLE [isissecurity].[ApplicationPermission]  WITH CHECK ADD  CONSTRAINT [ApplicationPermission_FK1] FOREIGN KEY([roleId])
REFERENCES [isissecurity].[ApplicationRole] ([id])
GO
ALTER TABLE [isissecurity].[ApplicationPermission] CHECK CONSTRAINT [ApplicationPermission_FK1]
GO

