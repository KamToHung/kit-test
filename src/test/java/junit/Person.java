package junit;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Person {

    private Long id;

    private String name;

    private Integer age;

    private String sayAction;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private LocalDateTime timeoutTime;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public String getSayAction() {
        return sayAction;
    }

    public void setSayAction(final String sayAction) {
        this.sayAction = sayAction;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(final LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getTimeoutTime() {
        return timeoutTime;
    }

    public void setTimeoutTime(final LocalDateTime timeoutTime) {
        this.timeoutTime = timeoutTime;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sayAction='" + sayAction + '\'' +
                ", createTime=" + createTime +
                ", timeoutTime=" + timeoutTime +
                '}';
    }

}
