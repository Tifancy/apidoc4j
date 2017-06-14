package cn.xiaocuoben.apidoc4j.plugin;

import cn.xiaocuoben.apidoc4j.constant.API4jConstant;
import cn.xiaocuoben.apidoc4j.doclet.APIDocDoclet;
import cn.xiaocuoben.apidoc4j.utils.ContextUtils;
import cn.xiaocuoben.apidoc4j.utils.FreemarkerRenderUtils;
import com.sun.tools.javadoc.Main;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.classworlds.realm.ClassRealm;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

/**
 * @author Frank
 * @date 2017-05-10
 * @goal generate
 * @phase package
// * @configurator include-project-dependencies
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class APIDocMojo extends AbstractMojo {

    /**
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     */
    private String resourceDirectory;
    /**
     * @parameter expression="${basePackage}"
     * @required
     */
    private String basePackage;
    /**
     * @parameter expression="${output}"
     * @required
     */
    private String output;
    /**
     * 项目编译输出目录
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    private String buildOutputDirectory;
    /**
     * @arameter expression="${project.build.sourceEncoding}"
     * @required
     */
    private String encoding = "UTF-8";


    public void execute() throws MojoExecutionException, MojoFailureException {
        ContextUtils.put(API4jConstant.DOC_OUTPUT_DIRECTORY, this.output);
        this.setUp();
        String docletClassName = APIDocDoclet.class.getName();

        List<String> commandArgumentList = new ArrayList<>();
        commandArgumentList.addAll(Arrays.asList("-doclet", docletClassName));
        commandArgumentList.addAll(Arrays.asList("-sourcepath", this.resourceDirectory));
        commandArgumentList.addAll(Arrays.asList("-encoding", this.encoding));

        String classpath = this.buildOutputDirectory + File.pathSeparator + this.resolveProjectDependenciesPath() + File.pathSeparator + this.resolvePluginDependenciesPath();
        commandArgumentList.addAll(Arrays.asList("-classpath", classpath));

        commandArgumentList.addAll(Arrays.asList("-subpackages",this.basePackage));

        String[] commandArray = commandArgumentList.toArray(new String[commandArgumentList.size()]);
        if(this.getLog().isDebugEnabled()){
            this.getLog().debug("javadoc " + StringUtils.join(commandArray," "));
        }

        int status = Main.execute(commandArray);
        if (status != 0) {
            this.getLog().error("执行javadoc失败");
        }
    }

    public String getResourceDirectory() {
        return resourceDirectory;
    }

    public APIDocMojo setResourceDirectory(String resourceDirectory) {
        this.resourceDirectory = resourceDirectory;
        return this;
    }

    public String getOutput() {
        return output;
    }

    public APIDocMojo setOutput(String output) {
        this.output = output;
        return this;
    }

    public Path convertToRelativePath(Path absolutePath){
        Path sourcePath = new File(this.resourceDirectory).toPath();
        return sourcePath.relativize(absolutePath);
    }

    public String resolveProjectDependenciesPath(){
        List<File> dependencyFileList = this.convertProjectDependencyToFile();

        StringBuilder classpathBuilder = new StringBuilder();
        for (File file : dependencyFileList) {
            classpathBuilder.append(file.getAbsolutePath()).append(File.pathSeparator);
        }
        classpathBuilder.deleteCharAt(classpathBuilder.length() - 1);
        return classpathBuilder.toString();
    }
    public String resolvePluginDependenciesPath(){
        List<File> dependencyFileList = this.convertPluginDependencyToFile();

        StringBuilder classpathBuilder = new StringBuilder();
        for (File file : dependencyFileList) {
            classpathBuilder.append(file.getAbsolutePath()).append(File.pathSeparator);
        }
        classpathBuilder.deleteCharAt(classpathBuilder.length() - 1);
        return classpathBuilder.toString();
    }

    public List<File> convertPluginDependencyToFile(){
        PluginDescriptor pluginDescriptor = (PluginDescriptor) this.getPluginContext().get("pluginDescriptor");

        Artifact pluginArtifact = pluginDescriptor.getPluginArtifact();
        StringBuilder filePathBuilder = new StringBuilder(pluginArtifact.getFile().toString());

        int endIdx = filePathBuilder.indexOf(pluginArtifact.getGroupId().replace(".", File.separator));
        String mavenLocalRepoPath = filePathBuilder.substring(0, endIdx);

        List<File> dependencyFileList = new ArrayList<>();
        List<Artifact> artifactList = pluginDescriptor.getArtifacts();
        for (Artifact artifact : artifactList) {
            String groupPath = artifact.getGroupId().replace(".", File.separator);
            String jarPath = mavenLocalRepoPath + groupPath + File.separator + artifact.getArtifactId() + File.separator + artifact.getVersion() + File.separator + artifact.getArtifactId() + "-" + artifact.getVersion() + ".jar";
            dependencyFileList.add(new File(jarPath));
        }
        return dependencyFileList;
    }

    public List<File> convertProjectDependencyToFile(){
        PluginDescriptor pluginDescriptor = (PluginDescriptor) this.getPluginContext().get("pluginDescriptor");
        MavenProject mavenProject = (MavenProject) this.getPluginContext().get("project");

        Artifact pluginArtifact = pluginDescriptor.getPluginArtifact();
        StringBuilder filePathBuilder = new StringBuilder(pluginArtifact.getFile().toString());

        int endIdx = filePathBuilder.indexOf(pluginArtifact.getGroupId().replace(".", File.separator));
        String mavenLocalRepoPath = filePathBuilder.substring(0, endIdx);

        List<File> dependencyFileList = new ArrayList<>();
        Set<Artifact> artifactList = mavenProject.getDependencyArtifacts();
        for (Artifact artifact : artifactList) {
            String groupPath = artifact.getGroupId().replace(".", File.separator);
            String jarPath = mavenLocalRepoPath + groupPath + File.separator + artifact.getArtifactId() + File.separator + artifact.getVersion() + File.separator + artifact.getArtifactId() + "-" + artifact.getVersion() + ".jar";
            dependencyFileList.add(new File(jarPath));
        }
        return dependencyFileList;
    }

    public void setUp(){
        //渲染配置
        try {
            File docFile = new File(ContextUtils.get(API4jConstant.DOC_OUTPUT_DIRECTORY)).toPath().toFile();
            FreemarkerRenderUtils.OUT = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //项目依赖
        PluginDescriptor pluginDescriptor = (PluginDescriptor) this.getPluginContext().get("pluginDescriptor");
        File projectClasspath = new File(this.buildOutputDirectory);
        try {
            ClassRealm mavenPluginClassLoader = pluginDescriptor.getClassRealm();
            mavenPluginClassLoader.addURL(projectClasspath.toURL());
            for (File jarFile : this.convertProjectDependencyToFile()) {
                mavenPluginClassLoader.addURL(jarFile.toURL());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
