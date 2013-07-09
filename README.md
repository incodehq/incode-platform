# isis-domainservice-docx #

This is a domain service to generate Word `.docx` from an initial `.docx` template and input data; in other words, a mail merge.

Several data types are supported:

  - plain text
  - rich text
  - date
  - bulleted list
  - tables

The implementation uses [docx4j](http://www.docx4java.org), [guava](https://code.google.com/p/guava-libraries/) and [jdom2](http://www.jdom.org).  Databinding to custom XML parts (the `.docx` file format's in-built support) is *not* used because this feature did not support repeating datasets prior to 2013.

This domain service was originally written to integrate with [Apache Isis](http://isis.apache.org).  However it has no dependencies on Isis and can be used as a standalone utility. 

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

The template .docx is marked up using smart tags, as specified on the [DEVELOPER](http://msdn.microsoft.com/en-us/library/bb608625.aspx "How to show the DEVELOPER tab in Word") tab.  

A sample .docx can be found [here](https://github.com/danhaywood/isis-domainservice-docx/blob/master/src/test/resources/com/danhaywood/isis/domainservice/docx/TypicalDocument.docx?raw=true).


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


## Legal Stuff ##
 
### License ###

    Copyright 2013 Dan Haywood

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



