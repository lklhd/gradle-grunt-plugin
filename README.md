Gradle plugin for Grunt
=======================

This is a very simple Gradle plugin for running Grunt tasks part of the build.
It merely wraps calls to "grunt xyz" as "gradle grunt_xyz" tasks. Grunt is installed locally using npm.

Status
------

* Build: [![Build Status](https://travis-ci.org/srs/gradle-grunt-plugin.png?branch=master)](https://travis-ci.org/srs/gradle-grunt-plugin)
* Download: [![Download](https://api.bintray.com/packages/srs/maven/gradle-grunt-plugin/images/download.png)](https://bintray.com/srs/maven/gradle-grunt-plugin)
* License: [![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Installing the plugin
---------------------

Releases of this plugin are hosted at BinTray (http://bintray.com) and is part of jcenter repository.
Setup the plugin like this:

	buildscript {
		repositories {
			jcenter()
		}
		dependencies {
			classpath 'com.moowork.gradle:gradle-grunt-plugin:0.6'
		}
	}

Include the plugin in your build.gradle file like this:

    apply plugin: 'com.moowork.grunt'

The plugin will also apply gradle-node-plugin for Node and NPM related tasks. (see http://github/srs/grunt-node-plugin for details).

Using the plugin
----------------

You can run grunt tasks using this syntax:

    $ gradle grunt_build    # this runs grunt build
    $ gradle grunt_compile  # this runs grunt compile

... and so on.

These tasks do not appear explicitly in `gradle tasks`, they only appear as task rule.
Your Gruntfile.js defines what grunt_* tasks exist (see `grunt --help`, or `gradle grunt_--help`).

Also (more importantly), you can depend on those tasks, e.g.

    // runs "grunt build" as part of your gradle build
    build.dependsOn grunt_build

This is the main advantage of this plugin, to allow build
scripts (and grunt agnostics) to run grunt tasks via gradle.

It is also possible to run a grunt task only if one of its input files have changed:

    def srcDir = new File(projectDir, "src/main/web")
    def targetDir = new File(project.buildDir, "web")
    grunt_dist.inputs.dir srcDir
    grunt_dist.outputs.dir targetDir

Extended Usage
--------------

If you need to supply grunt with options, you have to create GruntTasks:

    task gruntBuildWithOpts(type: GruntTask) {
       args = ["build", "arg1", "arg2"]
    }


NPM helpers
-----------

The plugin also provides two fixed helper tasks to run once per project, which
however require npm (https://npmjs.org/) to be installed:

 - installGrunt: This installs grunt and grunt-cli to the project folder, using "npm install grunt grunt-cli"
 - npmInstall: This just runs "npm install" (possibly useful for scripting)

Since grunt will only be installed in your project folder, it will not
interact with the rest of your system, and can easily be removed later by
deleting the node_modules folders.

So as an example, you can make sure a local version of grunt exists using this:

    // makes sure on each build that grunt is installed
    grunt_build.dependsOn 'installGrunt'

    // processes your package.json before running grunt build
    grunt_build.dependsOn 'npmInstall'

    // runs "grunt build" as part of your gradle build
    build.dependsOn grunt_build


Automatically downloading Node
------------------------------

To simplify the build, you can say that the plugin should download Node and NPM automatically. The dependent
gradle-node-plugin does the magic (http://github.com/srs/gradle-node-plugin). Set this configuration to enable:

    node {
        // Version of node to use.
        version = '0.10.22'

        // Enabled the automatic download. False is the default (for now).
        download = true
    }

Node will be installed in the .gradle folder for current user under nodejs subdirectory.


Getting latest npm on Ubuntu
----------------------------

See https://github.com/creationix/nvm on how to install npm via nvm.

Building the Plugin
-------------------

To build the plugin, just type the following command:

    ./gradlew clean build

