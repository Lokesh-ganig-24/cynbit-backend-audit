package in.cynbit.audit.backend.service;

import in.cynbit.audit.backend.dto.ContactInquiryRequest;
import in.cynbit.audit.backend.entity.ContactInquiry;
import in.cynbit.audit.backend.repository.ContactInquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactInquiryService {

    private final ContactInquiryRepository repository;

    public ContactInquiryService(ContactInquiryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ContactInquiry saveInquiry(ContactInquiryRequest request) {

        ContactInquiry inquiry = new ContactInquiry();

        inquiry.setFullName(request.getFullName().trim());
        inquiry.setEmail(request.getEmail().trim().toLowerCase());
        inquiry.setCompany(request.getCompany());
        inquiry.setIndustry(request.getIndustry());
        inquiry.setMessage(request.getMessage().trim());

        return repository.save(inquiry);
    }
}