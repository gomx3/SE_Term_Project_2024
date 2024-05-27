package SE_team.IssueManager.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import SE_team.IssueManager.domain.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long>, JpaSpecificationExecutor<Issue> {

    @Query(value = "select * from issue e where DATE(e.created_at)=:date order by created_at DESC", nativeQuery = true)
    List<Issue> findByCreatedDateOrderByCreatedAtDesc(@Param("date") LocalDate date);

    List<Issue> findAll(Specification<Issue> spec, Sort sorting);
}
