package ru.moscow.tms.tms.service;

public interface DeletableEntitiesMarker {
    /**
     * We MUST NOT delete entity in DB, just mark it as Inactive (Deleted)
     * We may return this entity back any time
     * All related TestExecutions will be valid and may be searchable
     * */
    void markAsDeleted(Long id);

    void unmarkAsDeleted(Long id);
}
