# isis-domainservice-docx #

[![Build Status](https://travis-ci.org/danhaywood/isis-domainservice-docx.png?branch=master)](https://travis-ci.org/danhaywood/isis-domainservice-docx)

The docx domain service, for use in [Apache Isis](http://isis.apache.org), generates Word `.docx` from an initial `.docx` template, providing a mail merge/reporting capability.  A simplified HTML is used as the input to the service.

The service supports several data types:

- plain text
- rich text
- date
- bulleted list
- tables

The implementation uses [docx4j](http://www.docx4java.org), [guava](https://code.google.com/p/guava-libraries/) and [jdom2](http://www.jdom.org).  Databinding to custom XML parts (the `.docx` file format's in-built support) is *not* used because this feature did not support repeating datasets prior to 2013.

## Screenshots ##

The following screenshots are taken from the `zzzdemo` app (adapted from Isis' quickstart archetype).

#### Object

The screenshot below shows the object acting as the source of the data.  The "download as doc" action calls the service.

![](https://raw.github.com/danhaywood/isis-domainservice-docx/master/images/contributed-action.png)

#### Template

The template docx uses MS Word smart tags feature to identify the fields:

![](https://raw.github.com/danhaywood/isis-domainservice-docx/master/images/template.png)

Any styling in the template document is preserved on generation.

#### Generated

The generated docx merges in the data from the object into the template.  

![](https://raw.github.com/danhaywood/isis-domainservice-docx/master/images/generated-docx.png)

Note how the bulleted list is repeated for each dependency of the `ToDoItem`.  Tables work similarly.


## API ##

The main API is:
 
    public void merge(
             String html, 
             InputStream docxTemplate, 
             OutputStream docxTarget, 
             MatchingPolicy matchingPolicy) 
         throws LoadInputException, 
                LoadTemplateException, 
                MergeException

The `MatchingPolicy` specifies whether unmatched input values or unmatched placeholders in the template are allowed or should be considered as a failure.

Overloaded versions of the `merge(...)` method exist:

  - the `html` may instead be provided as a `org.w3c.dom.Document`
  - the `docxTemplate` may instead be provided as a doc4j `WordprocessingMLPackage` (an in-memory object structure that could be considered as analogous to an w3c `Document`, but representing a `.docx`).

The `WordprocessingMLPackage` can be obtained from a supplementary API method:

    public WordprocessingMLPackage loadPackage(
            InputStream docxTemplate) 
        throws LoadTemplateException

This exists because the parsing of the input stream into a `WordprocessingMLPackage` is not particularly quick.  Therefore clients may wish to cache this in-memory object structure.  If calling the overloaded version of `merge(...)` that accepts the `WordprocessingMLPackage` then the service performs a defensive copy of the template.


## .docx template ##

The template `.docx` is marked up using smart tags, as specified on the [DEVELOPER](http://msdn.microsoft.com/en-us/library/bb608625.aspx "How to show the DEVELOPER tab in Word") tab.  

A sample `.docx` can be found [here](https://github.com/danhaywood/isis-domainservice-docx/blob/master/src/test/resources/com/danhaywood/isis/domainservice/docx/TypicalDocument.docx?raw=true).


## input HTML ##

The input data is provided as an XHTML form, and the service merges using the `@id` attribute of the XHTML against the tag of the smart tag field in the .docx.

To specify a **plain** field, use:

    <p id="CustomerId" class="plain">12345</p>

To specify a **date** field, use:

    <p id="RenewalDate" class="date">20-Jan-2013</p>

To specify a **rich** field, use:

    <p id="PromoText" class="rich">
        Roll up, roll up, step right this way!
    </p>

To specify a **list** field, use:

    <ul id="Albums">
        <li>
            <p>Please Please Me</p>
            <p>1963</p>
        </li>
        <li>
            <p>Help</p>
        </li>
        <li>
            <p>Sgt Peppers Lonely Hearts Club Band</p>
            <p>1965</p>
            <p>Better than Revolver, or not?</p>
        </li>
    </ul>

To specify a **table** field, use:

    <table id="BandMembers">
        <tr>
            <td>John Lennon</td>
            <td>Rhythm guitar</td>
        </tr>
        <tr>
            <td>Paul McCartney</td>
            <td>Bass guitar</td>
        </tr>
        <tr>
            <td>George Harrison</td>
            <td>Lead guitar</td>
        </tr>
        <tr>
            <td>Ringo Starr</td>
            <td>Drums</td>
        </tr>
    </table>

A sample HTML can be found [here](https://github.com/danhaywood/isis-domainservice-docx/blob/master/src/test/resources/com/danhaywood/isis/domainservice/docx/input.html?raw=true)


## Generated output ##

For simple data types such as plain text, rich text and date, the service simply substitutes the input data into the placeholder fields in the `.docx`.

For lists, the service expects the contents of the placeholder to be a bulleted list, with an optional second paragraph of a different style.  The service clones the paragraphs for each item in the input list.  If the input specifies more than one paragraph in the list item, then the second paragraph from the template is used for those additional paragraphs.

For tables, the service expects the placeholder to be a table, with a header and either one or two body rows.  The header is left untouched, the body rows are used as the template for the input data.  Any surplus cells in the input data are ignored.

A sample generated document can be found [here](https://github.com/danhaywood/isis-domainservice-docx/blob/master/src/test/resources/com/danhaywood/isis/domainservice/docx/ExampleGenerated.docx?raw=true)


## Demo App ##

The demo app (in `zzzdemo` directory) has a `ToDoItemReportingService` class that contributes an action to allow a report to be generated for each `ToDoItem`.  

For simplicity, the service caches the bytes of the .docx acting as the template.  (A more sophisticated app would probably define some sort of entity to represent the report template):

    private final byte[] toDoItemTemplates;

    public ToDoItemReportingService() throws IOException {
        final URL templateUrl = Resources.getResource(ToDoItemReportingService.class, "ToDoItem.docx");
        toDoItemTemplates = Resources.toByteArray(templateUrl);
    }
    
The `downloadAsDoc()` action is contributed to each `ToDoItem`:

    @NotContributed(As.ASSOCIATION) // ie contributed as action
    @NotInServiceMenu
    public Blob downloadAsDoc(ToDoItem toDoItem) throws LoadInputException, LoadTemplateException, MergeException {

        final String html = asInputHtml(toDoItem);
        final byte[] byteArray = mergeToDocx(html);
        
        final String outputFileName = "ToDoItem-" + bookmarkService.bookmarkFor(toDoItem).getIdentifier() + ".docx";
        return new Blob(outputFileName, MIME_TYPE_DOCX, byteArray);
    }

This in turn calls the `asInputHtml()` helper method; [JDOM2](http://jdom.org) is used as the XML library to build up required input:

    private static String asInputHtml(ToDoItem toDoItem) {
        final Element htmlEl = new Element("html");
        Document doc = new Document();
        doc.setRootElement(htmlEl);

        final Element bodyEl = new Element("body");
        htmlEl.addContent(bodyEl);
        
        bodyEl.addContent(newP("Description", "plain", toDoItem.getDescription()));
        bodyEl.addContent(newP("Category", "plain", toDoItem.getCategory().name()));
        bodyEl.addContent(newP("Subcategory", "plain", toDoItem.getSubcategory().name()));
        bodyEl.addContent(newP("DueBy", "date", dueByOf(toDoItem)));
        
        final Element ulDependencies = new Element("ul");
        ulDependencies.setAttribute("id", "Dependencies");
        
        final SortedSet<ToDoItem> dependencies = toDoItem.getDependencies();
        for (final ToDoItem dependency : dependencies) {
            final Element liDependency = new Element("li");
            ulDependencies.addContent(liDependency);
            final Element pDependency = new Element("p");
            pDependency.setContent(new Text(dependency.getDescription()));
            liDependency.addContent(pDependency);
        }
        bodyEl.addContent(ulDependencies);
        
        final String html = new XMLOutputter().outputString(doc);
        return html;
    }

    private static Element newP(String id, String cls, String text) {
        final Element pDescription = new Element("p");
        pDescription.setAttribute("id", id);
        pDescription.setAttribute("class", cls);
        pDescription.setContent(new Text(text));
        return pDescription;
    }

    private static String dueByOf(ToDoItem toDoItem) {
        LocalDate dueBy = toDoItem.getDueBy();
        return dueBy != null? dueBy.toString("dd/MM/yyyy"): "";
    }

Finally, the `DocxService` is used to merge the input HTML against the .docx template:
    
    private byte[] mergeToDocx(final String html) throws LoadInputException, LoadTemplateException, MergeException {
        final ByteArrayInputStream docxTemplateIs = new ByteArrayInputStream(toDoItemTemplates);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        docxService.merge(html, docxTemplateIs, baos, MatchingPolicy.LAX);
        byte[] byteArray = baos.toByteArray();
        return byteArray;
    }



## Legal Stuff ##
 
### License ###

    Copyright 2013~2014 Dan Haywood

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


### Dependencies

    <dependencies>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>org.docx4j</groupId>
            <artifactId>docx4j</artifactId>
            <version>2.8.1</version>
        </dependency>
        <dependency>
            <!-- ASL v2.0 -->
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>13.0.1</version>
        </dependency>
        <dependency>
            <!-- https://raw.github.com/hunterhacker/jdom/master/LICENSE.txt -->
            <!-- Similar to Apache License but with the acknowledgment clause removed -->
            <groupId>org.jdom</groupId>
            <artifactId>jdom2</artifactId>
            <version>2.0.3</version>
        </dependency>
    </dependencies>



