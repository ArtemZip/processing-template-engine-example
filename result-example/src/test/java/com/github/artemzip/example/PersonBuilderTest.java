package com.github.artemzip.example;

import com.gitgub.artemzip.example.Person;
import com.gitgub.artemzip.example.PersonBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PersonBuilderTest {

    @Test
    void test() {
        Person tester = new PersonBuilder().setAge(21).setName("Tester").build();
        assertEquals(21, tester.getAge());
        assertEquals("Tester", tester.getName());
    }
}
