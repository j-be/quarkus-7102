package org.owly.duckdns.bugs.quarkus.test;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.owly.duckdns.bugs.quarkus.entity.TestEntity;
import org.owly.duckdns.bugs.quarkus.repo.TestEntityRepo;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestEntityRepoTest {
    @Inject
    TestEntityRepo testEntityRepo;

    private static final List<Long> testEntityIds = new ArrayList<>();

    @ParameterizedTest(name = "#{index} - Executing findAll() in between: {0}")
    @ValueSource(booleans = {false, true})
    @Order(0)
    public void testUpdate(boolean executeFindAll) {
        // Create
        TestEntity testEntity = new TestEntity();
        testEntity.setName("Start");
        create(testEntity);
        testEntityIds.add(testEntity.id);

        // Call getAll
        if (executeFindAll)
            testEntityRepo.findAll().list();

        // Update
        update(testEntity.id);

        // Compare
        Assertions.assertEquals("Start " + testEntity.id + " Updated", testEntityRepo.findById(testEntity.id).getName());
    }

    @Test
    @Order(1)
    public void testAllWereUpdated() {
        for (long testEntityId : testEntityIds)
            Assertions.assertEquals("Start " + testEntityId + " Updated", testEntityRepo.findById(testEntityId).getName());
    }

    @Transactional
    void create(TestEntity testEntity) {
        testEntityRepo.persist(testEntity);
    }

    @Transactional
    void update(long id) {
        TestEntity testEntity = testEntityRepo.findById(id);
        testEntity.setName(testEntity.getName() + " " + id + " Updated");
        testEntityRepo.persist(testEntity);
    }
}
