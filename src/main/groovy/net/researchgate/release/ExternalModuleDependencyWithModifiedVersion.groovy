package net.researchgate.release

import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.DependencyArtifact
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.artifacts.ExternalDependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.ModuleVersionIdentifier
import org.gradle.api.artifacts.MutableVersionConstraint
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.attributes.AttributeContainer

import javax.annotation.Nullable

class ExternalModuleDependencyWithModifiedVersion implements ExternalModuleDependency {

    private Dependency dependency;
    private String version

    ExternalModuleDependencyWithModifiedVersion(Dependency dependency, String newVersion) {
        this.dependency = dependency
        this.version = newVersion
    }

    @Override
    boolean isChanging() {
        return this.dependency.isChanging()
    }

    @Override
    ExternalModuleDependency setChanging(boolean b) {
        this.dependency.setChanging(b)
        return this
    }

    @Override
    ExternalModuleDependency copy() {
        return null
    }

    @Override
    boolean isForce() {
        return this.dependency.isForce()
    }

    @Override
    ExternalDependency setForce(boolean b) {
        this.dependency.setForce(b)
        return this
    }

    @Override
    void version(Action<? super MutableVersionConstraint> action) {
        this.dependency.version(action)
    }

    @Override
    VersionConstraint getVersionConstraint() {

        return new VersionConstraint() {
            @Override
            String getBranch() {
                return dependency.versionConstraint.branch
            }

            @Override
            String getPreferredVersion() {
                return version
            }

            @Override
            List<String> getRejectedVersions() {
                return dependency.versionConstraint.rejectedVersions
            }
        }
    }

    @Override
    ModuleDependency exclude(Map<String, String> map) {
        return dependency.exclude(map)
    }

    @Override
    Set<ExcludeRule> getExcludeRules() {
        return dependency.excludeRules
    }

    @Override
    Set<DependencyArtifact> getArtifacts() {
        return dependency.artifacts
    }

    @Override
    ModuleDependency addArtifact(DependencyArtifact dependencyArtifact) {
        return dependency.addArtifact(dependencyArtifact)
    }

    @Override
    DependencyArtifact artifact(@DelegatesTo(value = DependencyArtifact.class, strategy = 1) Closure closure) {
        return dependency.artifact(closure)
    }

    @Override
    DependencyArtifact artifact(Action<? super DependencyArtifact> action) {
        return dependency.artifact(action)
    }

    @Override
    boolean isTransitive() {
        return dependency.transitive
    }

    @Override
    ModuleDependency setTransitive(boolean b) {
        return dependency.setTransitive(b)
    }

    @Override
    String getTargetConfiguration() {
        return dependency.targetConfiguration
    }

    @Override
    void setTargetConfiguration(@Nullable String s) {
        dependency.setTargetConfiguration(s)
    }

    @Override
    AttributeContainer getAttributes() {
        return dependency.attributes
    }

    @Override
    ModuleDependency attributes(Action<? super AttributeContainer> action) {
        return dependency.attributes(action)
    }

    @Override
    String getGroup() {
        return dependency.group
    }

    @Override
    String getName() {
        return dependency.name
    }

    @Override
    String getVersion() {
        return version
    }

    @Override
    boolean contentEquals(Dependency dependency) {
        return dependency.group == this.dependency.group &&
                dependency.name == this.dependency.name &&
                dependency.version == version
    }

    @Override
    String getReason() {
        return this.dependency.reason
    }

    @Override
    void because(@Nullable String s) {
        this.dependency.because(s)
    }

    @Override
    boolean matchesStrictly(ModuleVersionIdentifier moduleVersionIdentifier) {
        return this.dependency.matchesStrictly(moduleVersionIdentifier)
    }
}
