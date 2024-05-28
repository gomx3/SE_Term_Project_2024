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
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> , JpaSpecificationExecutor<Issue> {


    @Query(value = "select * from issue e where DATE(e.created_at)=:date order by created_at DESC",nativeQuery = true)
    List<Issue> findByCreatedDateOrderByCreatedAtDesc(@Param("date") LocalDate date);

    List<Issue> findAll(Specification<Issue> spec, Sort sorting);

    @Query(value="select * from issue e where project_id=:projectId and YEAR(e.created_at)=:year and MONTH(e.created_at)=:month",nativeQuery = true)
    List<Issue> findByProjectIdAndYearAndMonth(@Param("projectId") Long projectId, @Param("year") int year,@Param("month") int month);

    @Query(
            value = "select fixer_id "
            +"from issue "
            +"where category=:category and project_id=:projectId and fixer_id is not null "
            +"group by fixer_id "
            +"order by count(*) desc"
            ,nativeQuery = true
    )
    long[] findDevByCategory(@Param("projectId")long projectId,@Param("category")String category);
}
