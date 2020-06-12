package ie.amach.testContainersExample.DB;

import ie.amach.testContainersExample.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

