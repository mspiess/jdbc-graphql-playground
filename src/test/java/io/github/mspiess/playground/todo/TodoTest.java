package io.github.mspiess.playground.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureGraphQlTester
@Transactional
public class TodoTest {

    @Autowired
    GraphQlTester graphQlTester;

    @Autowired
    JdbcClient jdbcClient;

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
                .path("getTodos.edges")
                .entityList(Object.class)
                .hasSize(0);
    }

    @Test
    void queryTodosReturnsTodos() {
        int affectedCount = jdbcClient.sql("INSERT INTO todo (title) VALUES (?)").param("Some Title").update();
        assertThat(affectedCount).isEqualTo(1);

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
                .path("getTodos.edges[*].node")
                .entityList(Todo.class)
                .hasSize(1)
                .satisfies(list -> assertThat(list.getFirst().title()).isEqualTo("Some Title"));
    }
}
