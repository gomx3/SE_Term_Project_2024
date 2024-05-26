package SE_team.IssueManager.repository;

import SE_team.IssueManager.domain.Issue;
import SE_team.IssueManager.domain.Member;
import SE_team.IssueManager.domain.enums.Category;
import SE_team.IssueManager.domain.enums.Priority;
import SE_team.IssueManager.domain.enums.Status;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> , JpaSpecificationExecutor<Issue> {


    @Query(value = "select * from issue e where DATE(e.created_at)=:date order by created_at DESC",nativeQuery = true)
    List<Issue> findByCreatedDateOrderByCreatedAtDesc(@Param("date") LocalDate date);

    List<Issue> findAll(Specification<Issue> spec, Sort sorting);
}
