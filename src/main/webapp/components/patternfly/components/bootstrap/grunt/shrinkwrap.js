/*!
 * Bootstrap Grunt task for generating npm-shrinkwrap.canonical.json
 * http://getbootstrap.com
 * Copyright 2014 Twitter, Inc.
 * Licensed under MIT (https://github.com/twbs/bootstrap/blob/master/LICENSE)
 */
/*
This Grunt task updates the npm-shrinkwrap.canonical.json file that's used as the key for Bootstrap's npm packages cache.
This task should be run and the updated file should be committed whenever Bootstrap's dependencies change.
*/
//'use strict';
//var canonicallyJsonStringify = require('canonical-json');
//var NON_CANONICAL_FILE = 'npm-shrinkwrap.json';
//var DEST_FILE = 'test-infra/npm-shrinkwrap.canonical.json';
//
//
//function updateShrinkwrap(grunt) {
//  // Assumption: Non-canonical shrinkwrap already generated by prerequisite Grunt task
//  var shrinkwrapData = grunt.file.readJSON(NON_CANONICAL_FILE);
//  grunt.log.writeln('Deleting ' + NON_CANONICAL_FILE.cyan + '...');
//  grunt.file.delete(NON_CANONICAL_FILE);
//  // Output as Canonical JSON in correct location
//  grunt.file.write(DEST_FILE, canonicallyJsonStringify(shrinkwrapData));
//  grunt.log.writeln('File ' + DEST_FILE.cyan + ' updated.');
//}
//
//
//module.exports = updateShrinkwrap;
