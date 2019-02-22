package com.nilangpatel.kotlin.dataclasses;

import java.util.Objects;

public class StudentVOJava {

    private String name;
    private int age;
    private int standard;
    private String gender;

    public StudentVOJava(String name, int age, int standard, String gender) {
        this.name = name;
        this.age = age;
        this.standard = standard;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentVOKotlin studentVO = (StudentVOKotlin) o;
        return age == studentVO.getAge() &&
                standard == studentVO.getStandard() &&
                Objects.equals(name, studentVO.getName()) &&
                Objects.equals(gender, studentVO.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, standard, gender);
    }

    @Override
    public String toString() {
        return "StudentVOKotlin{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", standard=" + standard +
                ", gender='" + gender + '\'' +
                '}';
    }
}
