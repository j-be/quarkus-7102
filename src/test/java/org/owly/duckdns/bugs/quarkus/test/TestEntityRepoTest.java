package org.owly.duckdns.bugs.quarkus.test;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.owly.duckdns.bugs.quarkus.entity.TestEntity;
import org.owly.duckdns.bugs.quarkus.repo.TestEntityRepo;

import javax.inject.Inject;
import javax.transaction.Transactional;

@QuarkusTest
public class TestEntityRepoTest {
    @Inject
    TestEntityRepo testEntityRepo;

    @ParameterizedTest(name = "#{index} - Executing findAll() in between: {0}")
    @ValueSource(booleans = {true, false})
    public void testUpdate(boolean executeFindAll) {
        // Create
        TestEntity testEntity = new TestEntity();
        testEntity.setName("Start");
        create(testEntity);

        // Call getAll
        if (executeFindAll)
            testEntityRepo.findAll().list();

        // Update
        update(testEntity.id);

        // Compare
        Assertions.assertEquals("Start Updated", testEntityRepo.findById(testEntity.id).getName());
    }

    @Transactional
    void create(TestEntity testEntity) {
        testEntityRepo.persist(testEntity);
    }

    @Transactional
    void update(long id) {
        TestEntity testEntity = testEntityRepo.findById(id);
        testEntity.setName(testEntity.getName() + " Updated");
        testEntityRepo.persist(testEntity);
    }
}
