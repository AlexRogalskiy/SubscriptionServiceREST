package com.wildbeeslabs.rest.repositories;

import com.wildbeeslabs.rest.model.BaseEntity;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

/**
 *
 * Base REST Application storage repository
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long> {

    /**
     * Get list of entities created before (including) particular date
     *
     * @param date - request date
     * @return list of entities
     */
    @Query("select e from #{#entityName} e where e.createdAt <= ?1")
    List<T> findAllByCreatedAtLessThanEqual(final Date date);

    /**
     * Get list of entities created after (excluding) particular date
     *
     * @param date - request date
     * @return list of entities
     */
    @Query("select e from #{#entityName} e where e.createdAt > ?1")
    List<T> findAllByCreatedAtGreaterThan(final Date date);

    /**
     * Get list of entities created between (lower bound excluding, upper bound
     * including) particular date
     *
     * @param dateFrom - start date
     * @param dateTo - end date
     * @return list of entities
     */
    @Query("select e from #{#entityName} e where e.createdAt > ?1 and e.createdAt <= ?2")
    List<T> findAllByCreatedAtBetween(final Date dateFrom, final Date dateTo);
}
