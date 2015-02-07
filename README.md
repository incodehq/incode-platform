# isis-module-command #

[![Build Status](https://travis-ci.org/isisaddons/isis-module-command.png?branch=master)](https://travis-ci.org/isisaddons/isis-module-command)

This module, intended for use with [Apache Isis](http://isis.apache.org), provides an implementation of Isis'
`CommandService` API that enables action invocations (`Command`s) to be persisted using Isis' own (JDO) objectstore.  
This supports two main use cases:

* profiling: determine which actions are invoked most frequently, what is the elapsed time for each command)

* enhanced auditing: the command represents the "cause" of a change to the system, while the related 
  [Audit module](http://isisaddons.org) captures the "effect" of the change.  The two are correlated together using a
  unique transaction Id (a GUID).
   
In addition, this module also provides an implementation of the `BackgroundCommandService` API.  This enables 
commands to be persisted but the action not invoked.  A scheduler can then be used to pick up the scheduled background
commands and invoke them at some later time.  The module provides a subclass of the `BackgroundCommandExecution` class
(in Isis core) to make it easy to write such scheduler jobs.

## Screenshots ##

The following screenshots show an example app's usage of the module.

#### Installing the Fixture Data

Install some sample fixture data ...

![](https://raw.github.com/isisaddons/isis-module-command/master/images/01-install-fixtures.png)

#### Commands ####

Commands can be associated with any object (as a polymorphic association utilizing the `BookmarkService`), and so the
demo app lists the commands associated with the example entity:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/02-example-object.png)

#### Commands created for action invocations ####

In the example entity the `changeName` action is annotated with `@Action(command=CommandReification.ENABLED)`:

<pre>
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            command = CommandReification.ENABLED
    )
public SomeCommandAnnotatedObject changeName(final String newName) {
    setName(newName);
    return this;
}
</pre>

which means that when the `changeName` action is invoked with some argument ...

![](https://raw.github.com/isisaddons/isis-module-command/master/images/04-change-name-args.png)

... then a command object is created:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/05-change-name-results.png)

... identifying the action, captures the target and action arguments, also timings and user:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/06-change-name-command-persisted.png)

#### Background Commands using the Background Service ####

Commands are also the basis for Isis' support of background commands.  The usual way to accomplish this is to call Isis'
`BackgroundService`:

<pre>
@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        command = CommandReification.ENABLED
)
@ActionLayout(
        named = "Schedule"
)
public void changeNameExplicitlyInBackground(
        @ParameterLayout(named = "New name")
        final String newName) {
    backgroundService.execute(this).changeName(newName);
}
</pre>

In the screenshots below the action (labelled "Schedule" in the UI) is called with arguments:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/08-schedule-args.png)

This results in _two_ persisted commands, a foreground command and a background command:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/11-schedule-commands.png)

The foreground command has been executed:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/13-schedule-foreground-command-with-background-command.png)

The background command has not (yet):

![](https://raw.github.com/isisaddons/isis-module-command/master/images/14-schedule-background-command-not-yet-run.png)

The background command can then be invoked through a separate process, for example using a Quartz Scheduler.  The module
provides the `BackgroundCommandExecutionFromBackgroundCommandServiceJdo` class which can be executed periodically to
process any queued background commands; more information below.

#### Background Commands scheduled implicitly ####

The other way to create background commands is implicitly, using `@Action(commandExecuteIn=CommandExecuteIn.BACKGROUND)`:

<pre>
@Action(
        semantics = SemanticsOf.IDEMPOTENT,
        command = CommandReification.ENABLED,
        commandExecuteIn = CommandExecuteIn.BACKGROUND
)
@ActionLayout(
        named = "Schedule implicitly"
)
public SomeCommandAnnotatedObject changeNameImplicitlyInBackground(
        @ParameterLayout(named = "New name")
        final String newName) {
    setName(newName);
    return this;
}
</pre>

If invoked Isis will gather the arguments as usual:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/16-schedule-implicitly-args.png)

... but then does _not_ invoke the action, but instead creates the and returns the persisted background command:

![](https://raw.github.com/isisaddons/isis-module-command/master/images/17-schedule-implicitly-direct-to-results.png)

As the screenshot below shows, with this approach only a single background command is created (no foreground command 
at all):

![](https://raw.github.com/isisaddons/isis-module-command/master/images/18-schedule-implicitly-only-one-command.png)


## How to run the Demo App ##

The prerequisite software is:

* Java JDK 7 (nb: Isis currently does not support JDK 8)
* [maven 3](http://maven.apache.org) (3.2.x is recommended).

To build the demo app:

    git clone https://github.com/isisaddons/isis-module-command.git
    mvn clean install

To run the demo app:

    mvn antrun:run -P self-host
    
Then log on using user: `sven`, password: `pass`


## Relationship to Apache Isis Core ##

Isis Core 1.6.0 included the `org.apache.isis.module:isis-module-command-jdo:1.6.0` Maven artifact.  This module is a
direct copy of that code, with the following changes:

* package names have been altered from `org.apache.isis` to `org.isisaddons.module.command`
* the `persistent-unit` (in the JDO manifest) has changed from `isis-module-command-jdo` to 
  `org-isisaddons-module-command-dom`
* a copy-n-paste error in some of the JDO queries for `CommandJdo` have been fixed

Otherwise the functionality is identical; warts and all!

Isis 1.7.0 (and later) no longer ships with `org.apache.isis.module:isis-module-command-jdo`; use this addon module instead.


## How to configure/use ##

You can either use this module "out-of-the-box", or you can fork this repo and extend to your own requirements. 

#### "Out-of-the-box" ####

To use "out-of-the-box":

* update your classpath by adding this dependency in your dom project's `pom.xml`:

<pre>
    &lt;dependency&gt;
        &lt;groupId&gt;org.isisaddons.module.command&lt;/groupId&gt;
        &lt;artifactId&gt;isis-module-command-dom&lt;/artifactId&gt;
        &lt;version&gt;1.7.0&lt;/version&gt;
    &lt;/dependency&gt;
</pre>

* update your `WEB-INF/isis.properties`:

<pre>
    isis.services-installer=configuration-and-annotation
    isis.services.ServicesInstallerFromAnnotation.packagePrefix=
                ...,\
                org.isisaddons.module.command.dom,\
                ...
</pre>

Notes:
* Check for later releases by searching [Maven Central Repo](http://search.maven.org/#search|ga|1|isis-module-command-dom).

For commands to be created when actions are invoked, some configuration is required.  This can be either on a case-by-case basis, or globally:

* by default no action is treated as being a command unless it has explicitly annotated using `@Action(command=CommandReification.ENABLED)`.  This is the option used in the example app described above.

* alternatively, commands can be globally enabled by adding a key to `isis.properties`:

<pre>
    isis.services.command.actions=all
</pre>

This will create commands even for query-only (`@ActionSemantics(Of.SAFE)`) actions.  If these are to be excluded, then use:

<pre>
    isis.services.command.actions=ignoreQueryOnly
</pre>

An individual action can then be explicitly excluded from having a persisted command using `@Action(command=CommandReification.DISABLED)`.


#### "Out-of-the-box" (-SNAPSHOT) ####

If you want to use the current `-SNAPSHOT`, then the steps are the same as above, except:

* when updating the classpath, specify the appropriate -SNAPSHOT version:

<pre>
    &lt;version&gt;1.8.0-SNAPSHOT&lt;/version&gt;
</pre>

* add the repository definition to pick up the most recent snapshot (we use the Cloudbees continuous integration service).  We suggest defining the repository in a `<profile>`:

<pre>
    &lt;profile&gt;
        &lt;id&gt;cloudbees-snapshots&lt;/id&gt;
        &lt;activation&gt;
            &lt;activeByDefault&gt;true&lt;/activeByDefault&gt;
        &lt;/activation&gt;
        &lt;repositories&gt;
            &lt;repository&gt;
                &lt;id&gt;snapshots-repo&lt;/id&gt;
                &lt;url&gt;http://repository-estatio.forge.cloudbees.com/snapshot/&lt;/url&gt;
                &lt;releases&gt;
                    &lt;enabled&gt;false&lt;/enabled&gt;
                &lt;/releases&gt;
                &lt;snapshots&gt;
                    &lt;enabled&gt;true&lt;/enabled&gt;
                &lt;/snapshots&gt;
            &lt;/repository&gt;
        &lt;/repositories&gt;
    &lt;/profile&gt;
</pre>


#### Forking the repo ####

If instead you want to extend this module's functionality, then we recommend that you fork this repo.  The repo is 
structured as follows:

* `pom.xml   ` - parent pom
* `dom       ` - the module implementation, depends on Isis applib
* `fixture   ` - fixtures, holding a sample domain objects and fixture scripts; depends on `dom`
* `integtests` - integration tests for the module; depends on `fixture`
* `webapp    ` - demo webapp (see above screenshots); depends on `dom` and `fixture`

Only the `dom` project is released to Maven Central Repo.  The versions of the other modules are purposely left at 
`0.0.1-SNAPSHOT` because they are not intended to be released.

## API ##

This module implements two service APIs, `CommandService` and `BackgroundCommandService`.  It also provides the
`BackgroundCommandExecutionFromBackgroundCommandServiceJdo` to retrieve background commands for a scheduler to execute.

### `CommandService` ###

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

### `BackgroundCommandService` ###

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

In 1.7.0, it is necessary to explicitly register `CommandServiceJdoContributions` and
`BackgroundCommandServiceJdoContributions` in `isis.properties` (the rationale being that this service contributes
functionality that appears in the user interface).  In 1.8.0-SNAPSHOT this policy is reversed, and the services are
 automatically registered using `@DomainService`.  Use security to suppress its contributions if required.

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

If the all these services are configured - such that  commands, audit entries and published events are all persisted,
then the `transactionId` that is common to all enables seamless navigation between each.  (This is implemented through 
contributed actions/properties/collections; `Command` implements the `HasTransactionId` interface in Isis' applib, 
and it is this interface that each module has services that contribute to).


## Change Log ##

* `1.8.0-SNAPSHOT` - in development against Isis 1.8.0-SNAPSHOT.
* `1.7.0` - released against Isis 1.7.0.
* `1.6.1` - [#1](https://github.com/isisaddons/isis-module-command/issues/1) (don't store bookmarks beyond 2000 characters)
* `1.6.0` - re-released as part of isisaddons, with classes under package `org.isisaddons.module.command`


## Legal Stuff ##
 
#### License ####

    Copyright 2014-2015 Dan Haywood

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

Only the `dom` module is deployed, and is done so using Sonatype's OSS support (see 
[user guide](http://central.sonatype.org/pages/apache-maven.html)).

#### Release to Sonatype's Snapshot Repo ####

To deploy a snapshot, use:

    pushd dom
    mvn clean deploy
    popd

The artifacts should be available in Sonatype's 
[Snapshot Repo](https://oss.sonatype.org/content/repositories/snapshots).

#### Release to Maven Central ####

The `release.sh` script automates the release process.  It performs the following:

* performs a sanity check (`mvn clean install -o`) that everything builds ok
* bumps the `pom.xml` to a specified release version, and tag
* performs a double check (`mvn clean install -o`) that everything still builds ok
* releases the code using `mvn clean deploy`
* bumps the `pom.xml` to a specified release version

For example:

    sh release.sh 1.8.0 \
                  1.9.0-SNAPSHOT \
                  dan@haywood-associates.co.uk \
                  "this is not really my passphrase"
    
where
* `$1` is the release version
* `$2` is the snapshot version
* `$3` is the email of the secret key (`~/.gnupg/secring.gpg`) to use for signing
* `$4` is the corresponding passphrase for that secret key.

Other ways of specifying the key and passphrase are available, see the `pgp-maven-plugin`'s 
[documentation](http://kohsuke.org/pgp-maven-plugin/secretkey.html)).

If the script completes successfully, then push changes:

    git push

If the script fails to complete, then identify the cause, perform a `git reset --hard` to start over and fix the issue
before trying again.  Note that in the `dom`'s `pom.xml` the `nexus-staging-maven-plugin` has the 
`autoReleaseAfterClose` setting set to `true` (to automatically stage, close and the release the repo).  You may want
to set this to `false` if debugging an issue.
 
According to Sonatype's guide, it takes about 10 minutes to sync, but up to 2 hours to update [search](http://search.maven.org).
