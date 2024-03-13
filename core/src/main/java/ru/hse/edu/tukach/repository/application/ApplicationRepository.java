package ru.hse.edu.tukach.repository.application;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.hse.edu.tukach.dto.application.ApplicationFilter;
import ru.hse.edu.tukach.dto.application.ApplicationLiteDto;
import ru.hse.edu.tukach.model.application.Application;

import java.util.List;
import javax.persistence.criteria.Predicate;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {

    List<ApplicationLiteDto> findAllByInitiatorTg(String initiatorTg);

    static Specification<Application> applicationSpecification(ApplicationFilter filter) {
        return (root, query, cb) -> {
            Predicate predicate = cb.and();
            if (!CollectionUtils.isEmpty(filter.getReviewerLogins())) {
                predicate = cb.and(predicate, root.get("reviewerLogin").in(filter.getReviewerLogins()));
            }
            if (!CollectionUtils.isEmpty(filter.getStatuses())) {
                predicate = cb.and(predicate, root.get("status").in(filter.getStatuses()));
            }
            if (filter.getDateFrom() != null) {
                predicate = cb.and(
                    predicate,
                    cb.greaterThanOrEqualTo(root.get("createdDateTime"), filter.getDateFrom())
                );
            }
            if (filter.getDateTo() != null) {
                predicate = cb.and(
                    predicate,
                    cb.lessThanOrEqualTo(root.get("createdDateTime"), filter.getDateTo())
                );
            }
            return predicate;
        };
    }

}
