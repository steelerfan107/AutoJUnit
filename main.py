import os
import shutil
import subprocess
import xml.etree.ElementTree as ET
from xml.dom import minidom

MVN_LOC = 'C:\\Users\\yhcro\\Downloads\\apache-maven-3.8.8-bin\\apache-maven-3.8.8\\bin\\mvn'
JAVA_HOME = "C:\\Users\\yhcro\\IdeaProjects\\FlakyTracker\\OpenJDK8U-jdk_x64_windows_hotspot_8u402b06\\jdk8u402-b06\\jre\\"
JACOCO_VERSION = "0.8.7"
GENERATED_TEST_PACKAGE = "llm"
GENERATED_TEST_NAME = "LLMGeneratedTest"
COMPILE_FAIL_LOG = "./compile.log"
TEST_FAIL_LOG = "./test.log"
COVREP_LOG = "./covrep/"

COVREP_JAR_LOC = "./covrep.jar"

debug = True


def get_target_dir(project_path, module=""):
    target_dir = project_path
    if module != "":
        for root, dirs, files in os.walk(project_path):
            if module in dirs:
                target_dir = os.path.join(root, module)
    return target_dir


def run_command(command, cwd):
    """Run a shell command and return (returncode, stdout, stderr)."""
    try:
        result = subprocess.run(command, cwd=cwd, shell=True,
                                stdout=subprocess.PIPE, stderr=subprocess.PIPE,
                                text=True, check=False)
        return result.returncode, result.stdout, result.stderr
    except Exception as e:
        return -1, '', str(e)


def build_project(target_dir):
    clean_cmd = MVN_LOC + " clean"
    run_command(clean_cmd, target_dir)
    cmd = MVN_LOC + ' install -DskipTests -Ddependency-check.skip=true -Denforcer.skip=true -Drat.skip=true -Dmdep.analyze.skip=true -Dmaven.javadoc.skip=true -Dgpg.skip=true -Dlicense.skip=true -am  '
    print(target_dir, cmd)
    returncode, stdout, stderr = run_command(cmd, target_dir)
    build_log = target_dir + '/build.log'

    if returncode != 0:
        print("Build failed. See build.log for details.")

    with open(build_log, 'w', encoding='utf-8') as f:
        f.write(stdout)
        f.write(stderr)


def prettify(elem):
    """return formatted string"""
    rough_string = ET.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="    ")


def add_jacoco_plugin(pom_path):
    tree = ET.parse(pom_path)
    root = tree.getroot()

    # namespace process
    namespace = {'maven': 'http://maven.apache.org/POM/4.0.0'}
    ET.register_namespace('', 'http://maven.apache.org/POM/4.0.0')

    build = root.find('maven:build', namespace)
    if build is None:
        build = ET.SubElement(root, 'build')

    plugins = build.find('maven:plugins', namespace)
    if plugins is None:
        plugins = ET.SubElement(build, 'plugins')

    # check if jacoco already listed in plugins
    for plugin in plugins.findall('maven:plugin', namespace):
        artifactId = plugin.find('maven:artifactId', namespace)
        if artifactId is not None and artifactId.text == 'jacoco-maven-plugin':
            print("JaCoCo already in pom.xml")
            return

    # create jacoco element
    jacoco_plugin = ET.SubElement(plugins, 'plugin')
    ET.SubElement(jacoco_plugin, 'groupId').text = 'org.jacoco'
    ET.SubElement(jacoco_plugin, 'artifactId').text = 'jacoco-maven-plugin'
    ET.SubElement(jacoco_plugin, 'version').text = JACOCO_VERSION

    executions = ET.SubElement(jacoco_plugin, 'executions')
    #
    # # prepare-agent goal
    # execution1 = ET.SubElement(executions, 'execution')
    # goals1 = ET.SubElement(execution1, 'goals')
    # ET.SubElement(goals1, 'goal').text = 'prepare-agent'

    # report goal
    execution2 = ET.SubElement(executions, 'execution')
    ET.SubElement(execution2, 'id').text = 'report'
    ET.SubElement(execution2, 'phase').text = 'test'
    goals2 = ET.SubElement(execution2, 'goals')
    ET.SubElement(goals2, 'goal').text = 'report'

    # 保存修改后的pom.xml
    with open(pom_path, 'w', encoding='utf-8') as f:
        f.write(prettify(root))
    print("JaCoCo added successfully。")


def add_new_tests(target_dir, test_path):
    test_dir = target_dir + "/src/test/java/" + GENERATED_TEST_PACKAGE
    if not os.path.exists(test_dir):
        os.makedirs(test_dir)
    shutil.copy(test_path, test_dir + "/" + GENERATED_TEST_NAME + ".java")


def compile_updated_code(target_dir):
    cmd = MVN_LOC + "compile"
    returncode, stdout, stderr = run_command(cmd, target_dir)
    if returncode != 0:
        print("Compilation failed. See compile.log for details.")
        with open(COMPILE_FAIL_LOG, 'w', encoding='utf-8') as f:
            f.write(stdout)
            f.write(stderr)
        # todo: analyze why compile failed


def run_junit_tests(target_dir, with_jacoco=False, test_class=GENERATED_TEST_NAME):
    if with_jacoco:
        cmd = MVN_LOC + " test jacoco:report -Dtest=" + test_class
    else:
        cmd = MVN_LOC + " test -Dtest=" + test_class
    returncode, stdout, stderr = run_command(cmd, target_dir)
    if returncode != 0:
        print("TEST failed. See test.log for details.")
    print(cmd)
    with open(TEST_FAIL_LOG, 'w', encoding='utf-8') as f:
        f.write(stdout)
        f.write(stderr)


#
def get_osc(target_dir, class_name=None, test_name=GENERATED_TEST_PACKAGE + '.' + GENERATED_TEST_NAME):
    test_dir = target_dir + "/src/test/java/"
    # -s C:\Users\yhcro\Documents\GitHub\pset4\src\main\java -c fall24ee360t.pset4.Graph -ts C:\Users\yhcro\Documents\GitHub\pset4\src\test\java -t fall24ee360t.pset4.GraphTester
    separator = ';' if os.name == 'nt' else ':'

    if not class_name:
        for root, dirs, files in os.walk(target_dir + "/target/classes"):
            for file in files:
                if file.endswith(".class"):
                    class_name = os.path.join(root, file)
                    class_name = class_name.replace(target_dir + '/target/classes' + os.sep, '').replace('/',
                                                                                                         '.').replace(
                        '\\', '.').replace('.class', '')
                    get_osc(target_dir, class_name)
    else:
        cmd = JAVA_HOME + '/bin/java -cp \"' + os.path.abspath(
            target_dir) + "/target/classes" + separator + os.path.abspath(
            COVREP_JAR_LOC) + "\" covrep.CovRep -c " + class_name + " -s " + os.path.abspath(
            target_dir) + "/src/main/java + -ts " + os.path.abspath(test_dir) + " -t " + test_name
        print(cmd)
        returncode, stdout, stderr = run_command(cmd, target_dir)

        if returncode != 0:
            print("OSC COVERAGE FAILED TO GET. See cov.log for details.")
        if debug:
            log_file = COVREP_LOG + '/' + class_name.replace('.', '/') + '.covlog'
            flaky_tracker_dir = '/'.join(log_file.split('/')[0:len(log_file.split('/')) - 1])
            if not os.path.exists(flaky_tracker_dir):
                os.makedirs(flaky_tracker_dir)
            with open(log_file, 'w',
                      encoding='utf-8') as f:  # Todo: io here for debug, Considering efficiency we can analyze result only in memory.
                f.write(stdout)
                f.write(stderr)


def test_loop(project_path, module=""):
    target_dir = get_target_dir(project_path, module)
    build_project(target_dir)
    add_jacoco_plugin(target_dir + "./pom.xml")

    # get new tests?
    add_new_tests(target_dir, './test.java')
    run_junit_tests(target_dir)
    # get_osc(target_dir, "fall24ee360t.pset4.Graph")  # the second argument is the cut we care, by default it will calculate all classes.
    get_osc(target_dir)


def test_loop2(project_path, module=""):
    target_dir = get_target_dir(project_path, module)
    build_project(target_dir)
    add_jacoco_plugin(target_dir + "./pom.xml")
    run_junit_tests(target_dir, with_jacoco=True, test_class="binarytree.BinaryTreeTests")
    get_osc(target_dir, class_name="binarytree.BinaryTree", test_name="binarytree.BinaryTreeTests")


test_project_path = "./pset4"
test_project_path2 = "./examples"

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    os.environ['JAVA_HOME'] = JAVA_HOME
    os.environ['MAVEN_HOME'] = MVN_LOC
    test_loop2(test_project_path2)

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
