package org.chance.gradle

import org.gradle.testfixtures.ProjectBuilder
import spock.lang.Specification

/**
 * @author Kimi Chen
 * @since 2020/3/19, Thu
 * */
public class MybatisGeneratorPluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("com.thinkimi.gradle.MybatisGenerator")

        then:
        project.tasks.findByName("mbGenerator") != null
    }
}