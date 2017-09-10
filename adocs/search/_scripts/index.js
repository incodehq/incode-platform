#!/usr/bin/env node

'use strict';

// Script includes
const fs = require('fs');
const el = require('elasticlunr');
const recursive = require('recursive-readdir');
const crypto = require('crypto');
const optionParser = require('command-line-args');
const path = require('path');
const cheerio = require('cheerio');

const VERSION = '0.1';
const DESCRIPTION = 'Script to build the "elasticlunr" search index.'
const SCRIPT_NAME = path.basename(process.argv[1]);

/**
 * Calculates the md5 hash of a string and returns the
 * result as an integer.
 */
function hashString(str) {
    var md5 = crypto.createHash('md5');
    var hex_digest = md5.update(str).digest('hex');
    // Convert to integer
    return parseInt(hex_digest.slice(0, 8), 16);
}

function mkdirp(directory) {
    fs.existsSync(directory) || fs.mkdirSync(directory);
}

/**
 * Truncates a string to a maximum number of words.
 */
function truncateWords(str, words) {
    return str.split(/\b/).slice(0, words * 2).join('');
}

const optionParams = [
    {name: 'index-dir', alias: 'd', type: String, defaultOption: true},
    {name: 'output', alias: 'o', type: String, defaultValue: './index.json'},
    {name: 'version', alias: 'v', type: Boolean},
    {name: 'verbose', alias: 'V', type: Boolean},
    {name: 'help', alias: 'h', type: Boolean}
];

const options = optionParser(optionParams);

if (options.help) {
    console.log(SCRIPT_NAME + ' - ' + DESCRIPTION + '\n');
    console.log(
        'Usage: ' + SCRIPT_NAME + ' INDEX-DIR [ --output OUT-FILE --verbose]'
    );
    process.exit();
}

if (options.version) {
    console.log(process.argv[0] + ' - v' + VERSION);
    process.exit();
}

function padLeft(str, char, length) { 
    if(typeof str !== "undefined") 
        return str + char.repeat(Math.max(0, length - str.length));
    else
        return '';
}

// find all unique file extensions in bash using:
// for a in `find content -type f -print `; do filename=`basename $a`; fileext=${filename##*.}; echo "$fileext"; done  | sort -u

// TODO: should build this up dynamically instead
var ignore = [
        '*.PNG',
        '*.cache',
        '*.css',
        '*.docx',
        '*.eot',
        '*.gif',
        '*.jar',
        '*.jpg',
        '*.js',
        '*.less',
        '*.odp',
        '*.otf',
        '*.pdf',
        '*.pdn',
        '*.png',
        '*.ppt',
        '*.pptx',
        '*.rb',
        '*.rdf',
        '*.scss',
        '*.svg',
        '*.ttf',
        '*.woff',
        '*.woff2',
        '*.xml',
        '*.xsd'
        ];

// Create the index schema.
var index = el(function () {
    this.addField('title'),
    this.addField('body'),
    this.addField('description'),
    this.addField('url'),
    this.setRef('id')
});


var indexDir = options['index-dir'];

recursive(indexDir, ignore, function (err, files) {
    if (err) {
        console.log(err);
        return;
    }

    for (var file of files) {
        var html = fs.readFileSync(file, 'utf-8');
        
        file = file.replace(/\\/g, '/');
        file = file.replace(new RegExp('^' + options['index-dir']), '');

        if (options.verbose) {
            console.log("")
            console.log("Parsing: " + file);
        }
            
        // Parse the DOM.
        var $ = cheerio.load(html, {
            normalizeWhitespace: true,
            xmlMode: false
        });
        
        var withIds = $( '[id]' );
        
        $(withIds).each( function(i, withId) {
            //var sectionHeading = $(withId).prevAll('[id]')

            var sectionId = $(withId).attr('id');
            var childIds = $(withId).children('[id]');

            var isHeading = (withId.name === "h1" || withId.name === "h2" || withId.name === "h3" || withId.name === "h4" || withId.name === "h5" || withId.name === "h6")

            // has no child sections                
            if(isHeading && childIds.length == 0) {

                var headingWithId = withId;

                var sectionTitle = $(headingWithId).text();
                sectionTitle = sectionTitle.replace(new RegExp('^[0-9\.]*\. '), '');

                var sectionDiv = $(headingWithId).parent('div')[0]

                var sectionParagraphs = $(sectionDiv).children('div.paragraph')
                var firstPara = $(sectionParagraphs).length > 0 ? $(sectionParagraphs[0]).text() : ""
                var sectionDescription = truncateWords(firstPara, 50);

                var sectionBodyText =sectionTitle + " " + sectionParagraphs.text()

                var sectionUrl = file + "#" + sectionId

                var docId = hashString(sectionUrl)
                
                var doc = {
                    title: sectionTitle,
                    url: sectionUrl,
                    body: sectionBodyText,
                    description: sectionDescription,
                    id: docId
                };

                if (options.verbose) {
                    console.log("  " + padLeft(sectionId, ' ', 40) + ": " + sectionTitle);
                    /*
                    console.log("  sectionTitle      : " + sectionTitle);
                    console.log("  sectionUrl        : " +sectionUrl);
                    console.log("  id                : " + docId);
                    console.log("  sectionDescription: " + sectionDescription);
                    console.log("  sectionBodyText   : " + sectionBodyText);
                    console.log("")
                    */
                }

                index.addDoc(doc);

            }
        });

    }

    // Serialise and write the index.
    var out = index.toJSON();

    /*
    // Remove the body field from the documentStore to decrease the size of the index.
    for (var id in out.documentStore.docs) {
        delete out.documentStore.docs[id].body;
    }
    */


    if (options.verbose) {
        console.log("Serialising to: " + options.output)
    }

    var outputFile = options.output;
    var outputFileParse = path.parse(outputFile);

    mkdirp(outputFileParse.dir)
    fs.writeFileSync(outputFile, JSON.stringify(out), 'utf-8');


    if (options.verbose) {
        console.log('done');
    }

    process.exit();

});

