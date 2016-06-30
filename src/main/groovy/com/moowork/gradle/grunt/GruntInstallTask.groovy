package com.moowork.gradle.grunt

import com.moowork.gradle.node.task.NpmTask

class GruntInstallTask
    extends NpmTask
{
    public GruntInstallTask()
    {
        super()

        this.group = 'Grunt'
        this.description = "Runs 'npm install grunt-cli grunt' to install grunt-cli"

        // TODO Parameterize grunt version and pass in if needed.
        setArgs( ['install', 'grunt-cli', 'grunt@0.4.5'] )

        this.project.afterEvaluate {
            setWorkingDir( this.project.node.nodeModulesDir )
            getOutputs().dir( new File( this.project.node.nodeModulesDir, 'node_modules/grunt' ) )
            getOutputs().dir( new File( this.project.node.nodeModulesDir, 'node_modules/grunt-cli' ) )
        }
    }
}
