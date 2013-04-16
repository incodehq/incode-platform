isis-wicket-excel
=================

Integrating [Apache Isis](http://isis/apache.org)' Wicket Viewer, to allow a collection of entities to be downloaded as an Excel spreadsheet (using [Apache POI](http://poi.apache.org)).

### Usage

Add this component to your classpath, eg:

    <dependency>
        <groupId>com.danhaywood.isis.wicket.ui.components</groupId>
        <artifactId>danhaywood-isis-wicket-excel</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>


You should then find that a new view is provided for all collections of entities (either as returned from an action, or as a parented collection), from which a link to download the spreadsheet can be accessed.