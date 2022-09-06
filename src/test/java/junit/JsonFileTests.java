package junit;

import com.kit.junit.annotation.JsonFileSource;
import com.kit.junit.enums.JsonMode;
import com.kit.junit.enums.Type;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.List;
import java.util.logging.Logger;

/**
 * 最好使用resources，因为IDEA支持跳转
 */
class JsonFileTests {

   private Logger log = Logger.getLogger(getClass().getName());

    @Nested
    class ResourceTests {

        @ParameterizedTest
        @JsonFileSource(resources = {"/person/01.json", "/person/02.json"})
        @DisplayName("测试Json转Object入参")
        void jsonTest(Person person) {
            log.info("Object:" + person.toString());
        }

        @ParameterizedTest
        @JsonFileSource(resources = {"/persons/list01.json", "/persons/list02.json"})
        @DisplayName("测试Json转List入参")
        void jsonListTest(List<Person> persons) {
            log.info("List:" + persons.toString());
        }

        @ParameterizedTest
        @JsonFileSource(resources = {"/person/01.json", "/person/02.json"})
        @DisplayName("测试Json转String入参")
        void jsonStringTest(String person) {
            log.info("String:" + person);
        }

    }


    @Nested
    class FileTests {

        @ParameterizedTest
        @JsonFileSource(files = {"src/test/resources/person/01.json", "src/test/resources/person/02.json"})
        @DisplayName("测试Json转Object入参")
        void jsonTest(Person person) {
            log.info("Object:" + person.toString());
        }

        @ParameterizedTest
        @JsonFileSource(files = {"src/test/resources/persons/list01.json", "src/test/resources/persons/list02.json"})
        @DisplayName("测试Json转List入参")
        void jsonListTest(List<Person> persons) {
            log.info("List:" + persons.toString());
        }

        @ParameterizedTest
        @JsonFileSource(files = {"src/test/resources/person/01.json", "src/test/resources/person/02.json"})
        @DisplayName("测试Json转String入参")
        void jsonStringTest(String person) {
            log.info("String:" + person);
        }

    }

    @Nested
    class DirectoryResourceTests {

        @ParameterizedTest
        @JsonFileSource(resourceDirs = {"/person"})
        @DisplayName("测试同一目录下Json转Object入参")
        void jsonTest(Person person) {
            log.info("Object:" + person.toString());
        }

        @ParameterizedTest
        @JsonFileSource(resourceDirs = {"/persons"})
        @DisplayName("测试同一目录下Json转List入参")
        void jsonListTest(List<Person> persons) {
            log.info("List:" + persons.toString());
        }

        @ParameterizedTest
        @JsonFileSource(resourceDirs = {"/person"})
        @DisplayName("测试同一目录下Json转String入参")
        void jsonStringTest(String person) {
            log.info("String:" + person);
        }

    }

    @Nested
    class DirectoryFileTests {

        @ParameterizedTest
        @JsonFileSource(fileDirs = {"src/test/resources/person"})
        @DisplayName("同一目录下测试Json转Object入参")
        void jsonTest(Person person) {
            log.info("Object:" + person.toString());
        }

        @ParameterizedTest
        @JsonFileSource(fileDirs = {"src/test/resources/persons"})
        @DisplayName("同一目录下测试Json转List入参")
        void jsonListTest(List<Person> persons) {
            log.info("List:" + persons.toString());
        }

        @ParameterizedTest
        @JsonFileSource(fileDirs = {"src/test/resources/person"})
        @DisplayName("同一目录下测试Json转String入参")
        void jsonStringTest(String person) {
            log.info("String:" + person);
        }

    }


    @Nested
    class UnderLineTests {

        @Nested
        class ObjectTests {
            @ParameterizedTest
            @JsonFileSource(jsonMode = JsonMode.UNDER_LINE, resources = {"/person/01.json", "/person/02.json"})
            @DisplayName("测试下划线Json转Object入参")
            void jsonUnderLineTest(Person person) {
                log.info("Default Object:" + person.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.JACKSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/person/01.json", "/person/02.json"})
            @DisplayName("测试Jackson下划线转Object入参")
            void jsonJacksonUnderLineTest(Person person) {
                log.info("Jackson Object:" + person.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.GSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/person/01.json", "/person/02.json"})
            @DisplayName("测试Gson下划线转Object入参")
            void jsonGsonUnderLineTest(Person person) {
                log.info("Gson Object:" + person.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.FASTJSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/person/01.json", "/person/02.json"})
            @DisplayName("测试Fastjson下划线转Object入参")
            void jsonFastjsonUnderLineTest(Person person) {
                log.info("Fastjson Object:" + person.toString());
            }
        }

        @Nested
        class ListTests {
            @ParameterizedTest
            @JsonFileSource(jsonMode = JsonMode.UNDER_LINE, resources = {"/persons/list01.json", "/persons/list02.json"})
            @DisplayName("测试下划线Json转List入参")
            void jsonListUnderLineTest(List<Person> persons) {
                log.info("Default List:" + persons.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.JACKSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/persons/list01.json", "/persons/list02.json"})
            @DisplayName("测试Jackson下划线转List入参")
            void jsonListJacksonUnderLineTest(List<Person> persons) {
                log.info("Jackson List:" + persons.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.GSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/persons/list01.json", "/persons/list02.json"})
            @DisplayName("测试Gson下划线转List入参")
            void jsonListGsonUnderLineTest(List<Person> persons) {
                log.info("Gson List:" + persons.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.FASTJSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/persons/list01.json", "/persons/list02.json"})
            @DisplayName("测试Fastjson下划线转List入参")
            void jsonListFastjsonUnderLineTest(List<Person> persons) {
                log.info("Fastjson List:" + persons.toString());
            }
        }

    }

    @Nested
    class WrapperTests {

        @Nested
        class ObjectTests {
            @ParameterizedTest
            @JsonFileSource(jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response.json"})
            @DisplayName("测试下划线Json转Wrapper Object入参")
            void jsonUnderLineTest(Response<Person> person) {
                log.info("Default Object:" + person.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.JACKSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response.json"})
            @DisplayName("测试Jackson下划线转Wrapper Object入参")
            void jsonJacksonUnderLineTest(Response<Person> person) {
                log.info("Jackson Object:" + person.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.GSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response.json"})
            @DisplayName("测试Gson下划线转Wrapper Object入参")
            void jsonGsonUnderLineTest(Response<Person> person) {
                log.info("Gson Object:" + person.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.FASTJSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response.json"})
            @DisplayName("测试Fastjson下划线转Wrapper Object入参")
            void jsonFastjsonUnderLineTest(Response<Person> person) {
                log.info("Fastjson Object:" + person.toString());
            }
        }

        @Nested
        class ListTests {
            @ParameterizedTest
            @JsonFileSource(jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response_list.json"})
            @DisplayName("测试下划线Json转Wrapper List入参")
            void jsonListUnderLineTest(Response<List<Person>> persons) {
                log.info("Default List:" + persons.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.JACKSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response_list.json"})
            @DisplayName("测试Jackson下划线转Wrapper List入参")
            void jsonListJacksonUnderLineTest(Response<List<Person>> persons) {
                log.info("Jackson List:" + persons.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.GSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response_list.json"})
            @DisplayName("测试Gson下划线转Wrapper List入参")
            void jsonListGsonUnderLineTest(Response<List<Person>> persons) {
                log.info("Gson List:" + persons.toString());
            }

            @ParameterizedTest
            @JsonFileSource(type = Type.FASTJSON, jsonMode = JsonMode.UNDER_LINE, resources = {"/response/person_response_list.json"})
            @DisplayName("测试Fastjson下划线转Wrapper List入参")
            void jsonListFastjsonUnderLineTest(Response<List<Person>> persons) {
                log.info("Fastjson List:" + persons.toString());
            }
        }

    }

    @Nested
    @Disabled
    class ErrorTests {

        @ParameterizedTest
        @JsonFileSource(resources = {"/person/01.json", "/person/02.json"})
        @DisplayName("测试多参数异常")
        void jsonTest(Person person, int num) {
            log.info("Object:" + person.toString());
        }


        @ParameterizedTest
        @JsonFileSource(type = Type.JACKSON, resources = {"/error/list01.json", "/error/list02.json"})
        @DisplayName("测试错误Json Object数据异常使用Jackson反序列化异常")
        void jsonObjectJacksonTest(Person person) {
            log.info("Object:" + person.toString());
        }

        @ParameterizedTest
        @JsonFileSource(type = Type.FASTJSON, resources = {"/error/list01.json", "/error/list02.json"})
        @DisplayName("测试错误Json Object数据异常使用Fastjson反序列化异常")
        void jsonObjectFastjsonTest(Person person) {
            log.info("Object:" + person.toString());
        }

        @ParameterizedTest
        @JsonFileSource(type = Type.JACKSON, resources = {"/error/list01.json", "/error/list02.json"})
        @DisplayName("测试错误Json List数据异常使用Jackson反序列化异常")
        void jsonListJacksonTest(List<Person> persons) {
            log.info("List:" + persons.toString());
        }

        @ParameterizedTest
        @JsonFileSource(type = Type.FASTJSON, resources = {"/error/list01.json", "/error/list02.json"})
        @DisplayName("测试错误Json List数据异常使用Fastjson反序列化异常")
        void jsonListFastjsonTest(List<Person> persons) {
            log.info("List:" + persons.toString());
        }

    }


}
