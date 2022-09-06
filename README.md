# kit-test
kit-test是一个用于拓展Junit测试和mock dubbo相关接口的框架。 


## @JsonFileSource
当前Junit 5`@ParameterizedTest`注解只支持`@CsvSource`,`CsvFileSource`,`@EnumSource`,`@ValueSource`等传递参数的方式进行测试。
但是在开发中，我们往往没有用到他们提供的这些注解参数传递参数，我们用json格式较多，但Junit 5没有提供相关方法。 
`@JsonFileSource`注解可以使得我们可以从resources下读取相关json文件作为参数流入进行测试流程。

### 简单使用

#### 支持从resources中读取
```java
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
```

#### 支持从指定file路径读取
```java
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
```

#### 支持从指定文件下读取批量数据
```java
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
```

#### 支持下划线/驼峰，支持选择序列化方式
```java
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
```
> 具体测试例子可以参考`test`

## @MockDubboSpringBootTest
`MockDubboSpringBootTest`注解方便测试的时候对所有dubbo进行mock，从而达到不要让外界接口 影响我们自测（单元测试本质就是对自己负责的单元测试）。

#### 使用方式
```java
/**
 * 此注解会把所有dubbo接口生成Bean并且mock掉
 */
@MockDubboSpringBootTest(classes = ApiApplication.class)
class TradingDomainServiceTests {

    /**
     * 此对象会被mock
     */
    @Autowired
    private ItemFacade.ItemWatcherService itemWatcherService;

    /**
     * 或者不使用@MockDubboSpringBootTest,使用@MockBean单独对门面进行mock
     */
//    @MockBean
//    private ItemFacade itemFacade;

    @Test
    void billing() {
        when(itemWatcherService.getItemBySkuId(anyLong())).then(o -> {
            // 打桩的方法所有入参
            System.out.println(Arrays.toString(o.getArguments()));
            // 返回自定义对象
            return new ItemFacade.OutItem();
        });
    }

}

@Component
public class ItemFacade {

    /**
     * 假设此接口是Dubbo接口
     */
    @DubboReference
    public ItemWatcherService itemWatcherService;

    public OutItem getItemBySkuId(long skuId) {
        return itemWatcherService.getItemBySkuId(skuId);
    }

    public interface ItemWatcherService {

        OutItem getItemBySkuId(long skuId);

    }

    @Data
    public static class OutItem {

        public Long skuId;

        public Long itemId;

        public String name;

        private Long stockNum;

    }

}
```

## 一些可优化点
1. 通过`@JsonFileSource`的测试方法只支持一个入参，以后可以拓展支持多个入参，多个入参的json可通过类似application.yml文件的`---`划分
2. 通过`@MockDubboSpringBootTest`的测试其实还是要把整个SpringBoot都起起来，每次启动很耗时。可以尝试通过某些手段排除掉zookeeper/dubbo等，本地执行不依赖外部环境。