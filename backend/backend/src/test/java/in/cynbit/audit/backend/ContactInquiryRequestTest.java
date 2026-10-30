package in.cynbit.audit.backend;

import in.cynbit.audit.backend.dto.ContactInquiryRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactInquiryRequestTest {

    private final ValidatorFactory factory =
            Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    @Test
    void shouldRejectInvalidInquiry() {
        ContactInquiryRequest request = new ContactInquiryRequest();
        request.setFullName("");
        request.setEmail("not-an-email");
        request.setMessage("");

        var violations = validator.validate(request);

        assertEquals(3, violations.size());
    }

    @Test
    void shouldAcceptValidInquiry() {
        ContactInquiryRequest request = new ContactInquiryRequest();
        request.setFullName("Test User");
        request.setEmail("test@example.com");
        request.setCompany("Example Ltd");
        request.setIndustry("Technology");
        request.setMessage("This is a valid test inquiry.");

        var violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }
}
