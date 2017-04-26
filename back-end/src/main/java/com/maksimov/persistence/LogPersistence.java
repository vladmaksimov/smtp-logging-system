package com.maksimov.persistence;

import com.maksimov.models.entity.LogKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created on 01.03.17.
 */
@Repository
public interface LogPersistence extends JpaRepository<LogKey, Long>, JpaSpecificationExecutor<LogKey> {

    @Query("select l from LogKey l where l.emailFrom like ?1 or l.emailTo like ?1")
    Page<LogKey> searchLogKey(String search, Pageable pageable);

    @Query("select l from LogKey l where l.status = ?2 and (l.emailFrom like ?1 or l.emailTo like ?1)")
    Page<LogKey> getLogBySearchAndStatus(String search, String status, Pageable pageable);

    Page<LogKey> getLogByStatus(String status, Pageable pageable);

    LogKey getLogByLogKey(String logKey);

}
