package io.github.mspiess.playground.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class TodoTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Test
    void queryTodosReturnsEmptyList() {
        graphQlTester.document(
                        """
                                {
                                    getTodos {
                                        edges {
                                            node {
                                                title
                                            }
                                        }
                                    }
                                }
                                """)
                .execute()
                .errors()
                .verify()
                .path("getTodos.edges", path -> path.entityList(Object.class).satisfies(list -> assertThat(list).isEmpty()));
    }
}
