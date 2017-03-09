package com.maksimov.persistence;

import com.maksimov.models.entity.LogDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created on 01.03.17.
 */
public interface LogDetailsPersistence extends JpaRepository<LogDetail, Long> {

    @Query("select l from LogDetail l where l.logKey.id = ?1")
    Page<LogDetail> getLogDetails(Long id, Pageable pageable);

    @Query("select l from LogDetail l where l.logKey.id = ?1 and l.message like ?2")
    Page<LogDetail> searchDetails(Long id, String search, Pageable pageable);

    LogDetail findFirst1ByOrderByIdDesc();


}
