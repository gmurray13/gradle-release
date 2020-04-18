package net.researchgate.release

import org.gradle.api.GradleException
import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

class ReleasePluginUpdateSnapshotDependenciesTests extends Specification {

    def project

    def setup() {
        project = ProjectBuilder.builder().withName("ReleasePluginTest").build()
        project.apply plugin: 'groovy'
        project.apply plugin: ReleasePlugin
        project.release.scmAdapters = [TestAdapter]
    }

    def 'when SNAPSHOT in custom deps then remove SNAPSHOT'() {
        given:
        project.configurations { implementation }
        project.dependencies { implementation 'my:my:1.1.1-SNAPSHOT' }
        project.release.updateSnapshotDependencies = ['my:my', 'my2:my2']
        when:
        project.updateSnapshotDependencies.execute()
        then:
        notThrown GradleException
        project.configurations.implementation.dependencies[0].version == '1.1.1'
    }


    def 'when no SNAPSHOTS in custom deps then nothing gets updated'() {
        given:
        project.configurations { implementation }
        project.dependencies { implementation 'my:my:1.1.1-SNAPSHOT' }
        project.release.updateSnapshotDependencies = []
        when:
        project.updateSnapshotDependencies.execute()
        then:
        notThrown GradleException
        project.configurations.implementation.dependencies[0].version == '1.1.1-SNAPSHOT'
    }
}
