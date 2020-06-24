package ua.com.paradine;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ua.com.paradine");

        noClasses()
            .that()
                .resideInAnyPackage("ua.com.paradine.service..")
            .or()
                .resideInAnyPackage("ua.com.paradine.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ua.com.paradine.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
