package com.rfsystems.estudos.spring.batch.repository;

import com.rfsystems.estudos.spring.batch.model.GraphicIpDTO;
import com.rfsystems.estudos.spring.batch.model.GraphicUserAgentDTO;
import com.rfsystems.estudos.spring.batch.model.LogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface LogRepo extends PagingAndSortingRepository<LogEntity, Long> {

    public Page<LogEntity> findAll(Pageable pageable);

    @Transactional(readOnly=true)
    @Query("SELECT obj FROM LogEntity obj WHERE  (:ip is null or obj.ip = :ip) and obj.data BETWEEN :inicio and :fim")
    public List<LogEntity> findLogs(@Param("inicio") LocalDateTime inicio, @Param("fim")LocalDateTime fim, @Param("ip")String ip);

    public List<LogEntity> findByDataBetweenAndIp(@Param("inicio")LocalDateTime inicio,@Param("fim")LocalDateTime fim,@Param("ip")String ip);
    public List<LogEntity> findByDataBetween(@Param("inicio")LocalDateTime inicio,@Param("fim")LocalDateTime fim);
    public List<LogEntity> findByIp(@Param("ip")String ip);

    @Query(value = "select count(request) as requests, ip from log group by ip order by requests DESC limit 10", nativeQuery = true)
    public List<GraphicIpDTO> findIpByRequests();

    @Query(value = "select count(request) as requests, user_agent as userAgent from log group by user_agent order by requests DESC limit 10", nativeQuery = true)
    public List<GraphicUserAgentDTO> findUserByRequests();



}
