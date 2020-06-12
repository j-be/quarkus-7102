package org.owly.duckdns.bugs.quarkus.repo;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.owly.duckdns.bugs.quarkus.entity.TestEntity;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TestEntityRepo implements PanacheRepository<TestEntity> {
}
