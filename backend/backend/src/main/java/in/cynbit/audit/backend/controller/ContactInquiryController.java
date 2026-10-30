package in.cynbit.audit.backend.controller;

import in.cynbit.audit.backend.dto.ContactInquiryRequest;
import in.cynbit.audit.backend.entity.ContactInquiry;
import in.cynbit.audit.backend.service.ContactInquiryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/inquiries")
public class ContactInquiryController {

    private final ContactInquiryService inquiryService;

    public ContactInquiryController(ContactInquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> submitInquiry(
            @Valid @RequestBody ContactInquiryRequest request) {

        ContactInquiry savedInquiry = inquiryService.saveInquiry(request);

        Map<String, Object> response = Map.of(
                "success", true,
                "message", "Inquiry received successfully",
                "inquiryId", savedInquiry.getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}