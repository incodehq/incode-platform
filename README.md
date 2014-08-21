# isis-module-command #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-command.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-command)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides an implementation of Isis'
`CommandService` API that enables action invocations (`Command`s) to be persisted using Isis' own (JDO) objectstore.  
This supports two main use cases:

* profiling: determine which actions are invoked most frequently, what is the elapsed time for each command)

* enhanced auditing: the command represents the "cause" of a change to the system, while the related 
  [Audit module](http://isisaddons.org) captures the "effect" of the change.  The two are correlated together using a
  unique transaction Id (GUI).
   
In addition, this module also provides an implementation of the `BackgroundCommandService` API.  This enables 
commands to be persisted but the action not invoked.  A scheduler can then be used to pick up the scheduled background
commands and invoke them at some later time.  The module provides a subclass of the `BackgroundCommandExecution` class
(in Isis core) to make it easy to write such scheduler jobs.

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data

![](https://raw.github.com/isisaddons/isis-module-command/master/images/01-install-fixtures.png)

#### Example object with list of commands (contributed collection)

![](https://raw.github.com/isisaddons/isis-module-command/master/images/02-example-object.png)

#### Change name ...

<pre>
@ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
@Command
public SomeCommandAnnotatedObject changeName(final String newName) {
    setName(newName);
    return this;
}
</pre>

![](https://raw.github.com/isisaddons/isis-module-command/master/images/03-change-name.png)
![](https://raw.github.com/isisaddons/isis-module-command/master/images/04-change-name-args.png)

#### ... results in persisted command

![](https://raw.github.com/isisaddons/isis-module-command/master/images/05-change-name-results.png)

#### which identifies the action, captures the target and action arguments, also timings and user

![](https://raw.github.com/isisaddons/isis-module-command/master/images/06-change-name-command-persisted.png)

#### Schedule action...

<pre>
@Named("Schedule")
@ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
@Command
public void changeNameExplicitlyInBackground(final String newName) {
    backgroundService.execute(this).changeName(newName);
}
</pre>

![](https://raw.github.com/isisaddons/isis-module-command/master/images/07-schedule.png)
![](https://raw.github.com/isisaddons/isis-module-command/master/images/08-schedule-args.png)

#### ... results in <i>two</i> persisted commands, a foreground and background

![](https://raw.github.com/isisaddons/isis-module-command/master/images/11-schedule-commands.png)

#### the foreground command has been executed

![](https://raw.github.com/isisaddons/isis-module-command/master/images/13-schedule-foreground-command-with-background-command.png)

#### the background command has not yet

![](https://raw.github.com/isisaddons/isis-module-command/master/images/14-schedule-background-command-not-yet-run.png)

#### Schedule implicitly action...

<pre>
    @Named("Schedule implicitly")
    @ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
    @Command(executeIn = Command.ExecuteIn.BACKGROUND)
    public SomeCommandAnnotatedObject changeNameImplicitlyInBackground(final String newName) {
        setName(newName);
        return this;
    }
</pre>

![](https://raw.github.com/isisaddons/isis-module-command/master/images/15-schedule-implicitly.png)
![](https://raw.github.com/isisaddons/isis-module-command/master/images/16-schedule-implicitly-args.png)

#### ... when invoked returns the persisted background command
![](https://raw.github.com/isisaddons/isis-module-command/master/images/17-schedule-implicitly-direct-to-results.png)

#### only a single background command is created (no foreground command at all)

![](https://raw.github.com/isisaddons/isis-module-command/master/images/18-schedule-implicitly-only-one-command.png)

#### Actions can be excluded...

<pre>
    @Named("Change (not persisted)")
    @ActionSemantics(ActionSemantics.Of.IDEMPOTENT)
    @Command(persistence = Command.Persistence.NOT_PERSISTED)
    public SomeCommandAnnotatedObject changeNameCommandNotPersisted(final String newName) {
        setName(newName);
        return this;
    }
</pre>

![](https://raw.github.com/isisaddons/isis-module-command/master/images/19-change-not-persisted.png)
![](https://raw.github.com/isisaddons/isis-module-command/master/images/20-change-not-persisted-args.png)

#### ... has no commands persisted

![](https://raw.github.com/isisaddons/isis-module-command/master/images/21-change-not-persisted-results.png)


## Relationship to Apache Isis Core ##

Isis Core 1.6.0 included the `org.apache.isis.core:isis-module-command-jdo:1.6.0` Maven artifact.  This module is a
direct copy of that code, with the following changes:

* package names have been altered from `org.apache.isis` to `org.isisaddons.module.command`
* the `persistent-unit` (in the JDO manifest) has changed from `isis-module-command-jdo` to 
  `org-isisaddons-module-command-dom`
* a copy-n-paste error in some of the JDO queries for `CommandJdo` have been fixed


Otherwise the functionality is identical; warts and all!

At the time of writing the plan is to remove this module from Isis Core (so it won't be in Isis 1.7.0), and instead 
continue to develop it solely as one of the [Isis Addons](http://www.isisaddons.org) modules.

## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.command&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-command-dom&lt;/artifactId&gt;
        &lt;version&gt;1.6.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                ...,\
                org.isisaddons.module.command.dom,\
                ...

    isis.services = ...,\
                org.isisaddons.module.command.dom.CommandServiceContributions,\
                org.isisaddons.module.command.dom.BackgroundCommandServiceContributions,\
                ...
</pre>

The `CommandServiceContributions` and `BackgroundCommandServiceContributions` services are optional but recommended; 
see below for more information.

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml   ` - parent pom
* `dom       ` - the module implementation, depends on Isis applib
* `fixture   ` - fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` - integration tests for the module; depends on `fixture`
* `webapp    ` - demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to     Check for versions available in the 
[Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-audit-dom)).  The versions of the other modules 
are purposely left at `0.0.1-SNAPSHOT` because they are not intended to be released.

## API ##

This module implements two service APIs, `CommandService` and `BackgroundCommandService`.  It also provides the
`BackgroundCommandExecutionFromBackgroundCommandServiceJdo` to retrieve background commands for a scheduler to execute.

### `CommandService`

The `CommandService` defines the following API:

<pre>
public interface CommandService {
    Command create();
    
    void startTransaction(
        final Command command, 
        final UUID transactionId);
        
    void complete(
        final Command command);
        
    boolean persistIfPossible(
        final Command command);        
}
</pre>

Isis will call this service (if available) to create an instance of (the module's implementation of) `Command`
and to indicate when the transaction wrapping the action is starting and completing.

### `BackgroundCommandService`

The `BackgroundCommandService` defines the following API:

<pre>
public interface BackgroundCommandService {
    void schedule(
        final ActionInvocationMemento aim, 
        final Command command, 
        final String targetClassName, 
        final String targetActionName, 
        final String targetArgs);
}
</pre>

The implementation is responsible for persisting the command such that it can be executed asynchronously.

### BackgroundCommandExecutionFromBackgroundCommandServiceJdo

The `BackgroundCommandExecutionFromBackgroundCommandServiceJdo` utility class ultimately extends from Isis Core's 
`AbstractIsisSessionTemplate`, responsible for setting up an Isis session and obtaining commands.

For example, a Quartz scheduler can be configured to run a job that uses this utility class:

<pre>
public class BackgroundCommandExecutionQuartzJob extends AbstractIsisQuartzJob {
    public BackgroundCommandExecutionQuartzJob() {
        super(new BackgroundCommandExecutionFromBackgroundCommandServiceJdo());   
    }
}
</pre>

where `AbstractIsisQuartzJob` is the following boilerplate:

<pre>
public class AbstractIsisQuartzJob implements Job {

    public static enum ConcurrentInstancesPolicy {
        SINGLE_INSTANCE_ONLY,
        MULTIPLE_INSTANCES
    }
    
    private final AbstractIsisSessionTemplate isisRunnable;

    private final ConcurrentInstancesPolicy concurrentInstancesPolicy;
    private boolean executing;

    public AbstractIsisQuartzJob(AbstractIsisSessionTemplate isisRunnable) {
        this(isisRunnable, ConcurrentInstancesPolicy.SINGLE_INSTANCE_ONLY);
    }
    public AbstractIsisQuartzJob(
            AbstractIsisSessionTemplate isisRunnable, 
            ConcurrentInstancesPolicy concurrentInstancesPolicy) {
        this.isisRunnable = isisRunnable;
        this.concurrentInstancesPolicy = concurrentInstancesPolicy;
    }

    /**
     * Sets up an {@link IsisSession} then delegates to the 
     * {@link #doExecute(JobExecutionContext) hook}. 
     */
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        final AuthenticationSession authSession = newAuthSession(context);
        try {
            if(concurrentInstancesPolicy == 
                   ConcurrentInstancesPolicy.SINGLE_INSTANCE_ONLY && 
               executing) {
                return;
            }
            executing = true;

            isisRunnable.execute(authSession, context);
        } finally {
            executing = false;
        }
    }

    AuthenticationSession newAuthSession(JobExecutionContext context) {
        String user = getKey(context, SchedulerConstants.USER_KEY);
        String rolesStr = getKey(context, SchedulerConstants.ROLES_KEY);
        String[] roles = Iterables.toArray(
                Splitter.on(",").split(rolesStr), String.class);
        return new SimpleSession(user, roles);
    }

    String getKey(JobExecutionContext context, String key) {
        return context.getMergedJobDataMap().getString(key);
    }
}
</pre>


## Supporting Services ##

As well as the `CommandService` and `BackgroundCommandService` implementations, the module also a number of other
domain services:

* `CommandServiceJdoRepository` provides the ability to search for persisted (foreground) `Command`s.  None 
   of its actions are visible in the user interface (they are all `@Programmatic`) and so this service is automatically 
   registered.

* `BackgroundCommandServiceJdoRepository` provides the ability to search for persisted (background) `Command`s.  None 
   of its actions are visible in the user interface (they are all `@Programmatic`) and so this service is automatically 
   registered.

* `CommandServiceJdoContributions` provides the `commands` contributed collection to the `HasTransactionId` interface.
   This will therefore display all commands that occurred in a given transaction, in other words whenever a command,
   or also (if configured) a published event or an audit entry is displayed.

* `BackgroundCommandServiceJdoContributions` provides the `childCommands` and `siblingCommands` contributed collections
   to the `HasTransactionId` interface.  These collections will therefore display for any command, published event
   or audit entry.

## Related Modules/Services ##

As well as defining the `CommandService` and `BackgroundCommandService` APIs, Isis' applib defines several other
closely related services.  Implementations of these services are referenced by the 
[Isis Add-ons](http://www.isisaddons.org) website.

The `AuditingService3` service enables audit entries to be persisted for any change to any object.  The command can
be thought of as the "cause" of a change, the audit entries as the "effect".  

The `PublishingService` is another optional service that allows an event to be published when either an object has 
changed or an actions has been invoked.   There are some similarities between publishing to auditing, but the 
publishing service's primary use case is to enable inter-system co-ordination (in DDD terminology, between bounded 
contexts).

If the all these services are configured - such that  commands, audit entries and published events are all persisted, then 
the `transactionId` that is common to all enables seamless navigation between each.  (This is implemented through 
contributed actions/properties/collections; `Command` implements the `HasTransactionId` interface in Isis' applib, 
and it is this interface that each module has services that contribute to).

## Legal Stuff ##
 
#### License ####

    Copyright 2014 Dan Haywood

    Licensed under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.


#### Dependencies ####

There are no third-party dependencies.

##  Maven deploy notes

Only the `dom` module is deployed, and is done so using Sonatype's OSS support (see [user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo

To deploy a snapshot, use:

    cd dom
    mvn clean deploy

The artifacts should be available in [Sonatype's Snapshot Repo](https://oss.sonatype.org/content/repositories/snapshots).

#### Release to Maven Central

First manually update the release and tag, eg:

    mvn versions:set -DnewVersion=1.6.0
    mvn tag 1.6.0
    mvn commit -am "bumping to 1.6.0 for release"
    
Then release:

    mvn clean deploy -P release \
        -Dpgp.secretkey=keyring:id=dan@haywood-associates.co.uk \
        -Dpgp.passphrase="literal:this is not really my passphrase"

where (for example) "dan@haywood-associates.co.uk" is the email of the secret key (`~/.gnupg/secring.gpg`) to use
for signing, and the pass phrase is as specified as a literal.  (Other ways of specifying the key and passphrase are 
available, see the `pgp-maven-plugin`'s [documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If `autoReleaseAfterClose` is set to true for the `nexus-staging-maven-plugin`, then the above command will 
automatically stage, close and the release the repo.  Sync'ing to Maven Central should happen automatically.

If `autoReleaseAfterClose` is set to false, then the repo will require manually closing and releasing either by logging
onto the [Sonatype's OSS staging repo](https://oss.sonatype.org) or alternatively by releasing from the command line:

    mvn nexus-staging:release

Finally, don't forget to update the release to next snapshot, eg:

    mvn versions:set -DnewVersion=1.6.1-SNAPSHOT
    mvn commit -am "bumping to 1.6.1-SNAPSHOT for development"

and commit and push changes.